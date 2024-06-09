package com.t3rik.mes.controller

import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.framework.web.service.TokenService
import com.t3rik.system.domain.LoginParams
import com.t3rik.mes.service.MobileLoginService
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest


@RestController
@RequestMapping("/mobile/login")
class LoginController {

    @Resource
    lateinit var tokenService: TokenService;

    @Resource
    lateinit var loginService: MobileLoginService


    /**
     * 会员登录
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/loginByPassword")
    @ResponseBody
    fun loginByPassword(request: HttpServletRequest): AjaxResult {
        val username = request.getParameter("username")
        val password = request.getParameter("password")
        val phoneNo = request.getParameter("phoneNo")
        val validCode = request.getParameter("validCode")
        val loginType = request.getParameter("loginType")
        // 登录结果
        val loginParams = LoginParams()
        loginParams.username = username
        loginParams.password = password
        loginParams.phoneNo = phoneNo
        loginParams.validCode = validCode
        loginParams.loginType = loginType
        return loginService.login(loginParams)
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    fun sendRegisterCode(request: HttpServletRequest): AjaxResult {
        val phoneNo = request.getParameter("phoneNo")
        val validCodeType = request.getParameter("validCodeType")
        // 登录结果
        val loginParams = LoginParams()
        loginParams.phoneNo = phoneNo
        loginParams.validCodeType = validCodeType
        return loginService.sendCode(loginParams)
    }

    @GetMapping("/logout")
    fun logout(request: HttpServletRequest?): AjaxResult {
        val loginUser = tokenService.getLoginUser(request)
        if (null != loginUser) {
            tokenService.delLoginUser(loginUser.token)
        }
        return AjaxResult.success("退出成功！")
    }
}