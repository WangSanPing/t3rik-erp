package com.t3rik.mes.service

import com.t3rik.common.constant.Constants
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.domain.entity.SysUser
import com.t3rik.common.core.domain.model.LoginUser
import com.t3rik.common.core.redis.RedisCache
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.exception.user.UserPasswordNotMatchException
import com.t3rik.common.utils.DictUtils
import com.t3rik.common.utils.MessageUtils
import com.t3rik.common.utils.StringUtils
import com.t3rik.framework.manager.AsyncManager
import com.t3rik.framework.manager.factory.AsyncFactory
import com.t3rik.framework.web.service.PermissionService
import com.t3rik.framework.web.service.SysLoginService
import com.t3rik.framework.web.service.TokenService
import com.t3rik.system.domain.LoginParams
import com.t3rik.system.service.ISysRoleService
import com.t3rik.system.service.ISysUserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.annotation.Resource

@Service
class MobileLoginService {
    private val log: Logger = LoggerFactory.getLogger(MobileLoginService::class.java)

    @Resource
    lateinit var tokenService: TokenService

    @Resource
    lateinit var sysLoginService: SysLoginService

    @Resource
    lateinit var sysRoleService: ISysRoleService

    @Resource
    lateinit var authenticationManager: AuthenticationManager

    @Resource
    lateinit var sysUserService: ISysUserService

    @Resource
    lateinit var permissionService: PermissionService

    /**
     * 注入redis服务
     */
    @Resource
    lateinit var redisCache: RedisCache

    /**
     * 请求时间戳过期时间5分钟
     */
    private val REQUEST_TIME_OUT: Int = 1000 * 60 * 5


    /**
     * jwt密钥
     */
    @Value("\${token.secret}")
    private val jwtSecretKey: String? = null

    fun login(loginParams: LoginParams): AjaxResult {
        log.debug("login and loginParams:{}", loginParams)

        if (Objects.isNull(loginParams)) {
            return AjaxResult.error(-6, "登录参数不能为空")
        }
        val loginType = loginParams.loginType
        if (StringUtils.isBlank(loginType)) {
            return AjaxResult.error(-6, "登录方式不能为空")
        }
        // 登录方式0验证码登录，1用户名密码登录，2本机一键登录，3微信单点登录
        if (loginType == "0") {
            val phoneNo = loginParams.phoneNo
            if (StringUtils.isBlank(phoneNo)) {
                return AjaxResult.error(-6, "登录名不能为空")
            }
            val validCode = loginParams.validCode
            // 2表示登录验证码，校验验证码合法性
            // sysSmsSendService.checkValidCode(phoneNo,validCode,"2");
            loginParams.username = phoneNo
            loginParams.password = "SSO_LOGIN"
        } else if (loginType == "1") {
            val password = loginParams.password
            if (StringUtils.isBlank(password)) {
                return AjaxResult.error(-6, "密码不能为空")
            }
        }
        // 用户验证
        var authentication: Authentication? = null
        try {
            authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginParams.username,
                    loginParams.password
                )
            )
        } catch (e: Exception) {
            if (e is BadCredentialsException) {
                AsyncManager.me().execute(
                    AsyncFactory.recordLogininfor(
                        loginParams.username,
                        Constants.LOGIN_FAIL,
                        MessageUtils.message("user.password.not.match")
                    )
                )
                throw UserPasswordNotMatchException()
            } else {
                AsyncManager.me()
                    .execute(AsyncFactory.recordLogininfor(loginParams.username, Constants.LOGIN_FAIL, e.message))
                throw BusinessException(e.message)
            }
        }
        val loginUser = authentication.principal as LoginUser
        // loginUser.setSource("app");
        val user = loginUser.user
        // 生成token
        val token = tokenService!!.createToken(loginUser)
        AsyncManager.me().execute(
            AsyncFactory.recordLogininfor(
                loginParams.username,
                Constants.LOGIN_SUCCESS,
                MessageUtils.message("user.login.success")
            )
        )
        sysLoginService!!.recordLoginInfo(user.userId)
        // 判断用户是否存在管理员角色
        // 角色集合
//        Set<String> roles = permissionService.getRolePermission(user);
//        boolean roleFlag = false;
//        if(!CollectionUtils.isEmpty(roles)){
//            for (String roleKey : roles) {
//                if(roleKey.equals("agent")){
//                    roleFlag = true;
//                    break;
//                }
//            }
//        }
        val ajax = AjaxResult.success("")
        ajax["token"] = token
        // token过期时间
        ajax["expired"] = loginUser.expireTime
        ajax["user"] = loginUser.user
        ajax["isAgent"] = true.toString()
        return ajax
    }

    /**
     * 发送注册验证码
     *
     * @param loginParams
     * @return
     */
    fun sendCode(loginParams: LoginParams): AjaxResult {
        if (Objects.isNull(loginParams)) {
            return AjaxResult.error(-6, "参数为空")
        }
        // 验证验证码
        if (StringUtils.isBlank(loginParams.phoneNo)) {
            return AjaxResult.error(-6, "发送手机号不能为空")
        }
        var validCodeType: String? = "2"
        if (StringUtils.isNotBlank(loginParams.validCodeType)) {
            validCodeType = loginParams.validCodeType
        }
        try {
            // SysSmsSend sysSmsSend = sysSmsSendService.sendMessage(loginParams.getPhoneNo(),validCodeType,true);
            // String resultFlag = sysSmsSend.getResultFlag();
            val resultFlag = "n"
            if (resultFlag == "f") {
                return AjaxResult.error(-6, "对不起手机号【" + loginParams.phoneNo + "】发送短信失败：失败原因:")
            }
        } catch (e: Exception) {
            throw BusinessException(e.message)
        }
        val ajax = AjaxResult.success("验证码发送成功")
        return ajax
    }

    /**
     * 手机号验证码注册用户
     *
     * @param loginParams
     * @return
     */
    @Transactional(readOnly = false)
    fun registerUser(loginParams: LoginParams): AjaxResult {
        try {
            if (Objects.isNull(loginParams)) {
                return AjaxResult.error(-6, "参数为空")
            }
            val phoneNo = loginParams.phoneNo
            if (StringUtils.isBlank(phoneNo)) {
                return AjaxResult.error(-6, "发送手机号不能为空")
            }
            val validCode = loginParams.validCode
            if (StringUtils.isBlank(validCode)) {
                return AjaxResult.error(-6, "验证码不能为空")
            }
            loginParams.username = phoneNo
            loginParams.password = phoneNo
            loginParams.loginType = "1"
            return this.login(loginParams)
        } catch (e: Exception) {
            throw BusinessException(e.message)
        }
    }

    /**
     * 设置注册用户角色部门岗位信息
     *
     * @param registerUser
     * @return
     */
    private fun setUserDefaultInfo(registerUser: SysUser) {
        val registerRoleCode = DictUtils.getDictValue("sys_config", "register_role_code", "")
        if (StringUtils.isBlank(registerRoleCode)) {
            throw BusinessException("请前往数据字典【sys_config】中维护注册用户角色编码【register_role_code】")
        }
        val registerDeptCode = DictUtils.getDictValue("sys_config", "register_dept_code", "")
        if (StringUtils.isBlank(registerDeptCode)) {
            throw BusinessException("请前往数据字典【sys_config】中维护注册用户部门编码【register_dept_code】")
        }
        val registerPostCode = DictUtils.getDictValue("sys_config", "register_post_code", "")
        if (StringUtils.isBlank(registerPostCode)) {
            throw BusinessException("请前往数据字典【sys_config】中维护注册用户岗位编码【register_post_code】")
        }
    }
}