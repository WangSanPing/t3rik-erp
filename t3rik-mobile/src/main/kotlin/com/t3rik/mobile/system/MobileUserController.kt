package com.t3rik.mobile.system

import com.t3rik.common.annotation.Log
import com.t3rik.common.config.PlatformConfig
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.domain.entity.SysUser
import com.t3rik.common.enums.system.BusinessType
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.common.utils.StringUtils
import com.t3rik.common.utils.file.FileUploadUtils
import com.t3rik.framework.web.service.TokenService
import com.t3rik.system.service.ISysUserService
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


/**
 *
 * @author t3rik
 * @date 2025/4/11 21:36
 */
@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/system/user/profile")
class MobileUserController : BaseController() {


    @Resource
    lateinit var userService: ISysUserService

    @Resource
    lateinit var tokenService: TokenService

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    fun updatePwd(oldPassword: String?, newPassword: String?): AjaxResult {
        val loginUser = loginUser
        val userName = loginUser.username
        val password = loginUser.password
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("修改密码失败，旧密码错误")
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("新密码不能与旧密码相同")
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.user.password = SecurityUtils.encryptPassword(newPassword)
            tokenService.setLoginUser(loginUser)
            return AjaxResult.success()
        }
        return AjaxResult.error("修改密码异常，请联系管理员")
    }

    /**
     * 个人信息
     */
    @GetMapping
    fun profile(): AjaxResult {
        val loginUser = loginUser
        val user = loginUser.user
        return AjaxResult.success(user).apply {
            put("roleGroup", userService.selectUserRoleGroup(loginUser.username))
            put("postGroup", userService.selectUserPostGroup(loginUser.username))
        }
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    fun updateProfile(@RequestBody user: SysUser): AjaxResult {
        val loginUser = loginUser
        val sysUser = loginUser.user
        user.userName = sysUser.userName
        if (StringUtils.isNotEmpty(user.phonenumber)
            && UserConstants.NOT_UNIQUE == userService.checkPhoneUnique(user)
        ) {
            return AjaxResult.error("修改用户'" + user.userName + "'失败，手机号码已存在")
        }
        if (StringUtils.isNotEmpty(user.email)
            && UserConstants.NOT_UNIQUE == userService.checkEmailUnique(user)
        ) {
            return AjaxResult.error("修改用户'" + user.userName + "'失败，邮箱账号已存在")
        }
        user.userId = sysUser.userId
        user.password = null
        if (userService.updateUserProfile(user) > 0) {
            // 更新缓存用户信息
            sysUser.nickName = user.nickName
            sysUser.phonenumber = user.phonenumber
            sysUser.email = user.email
            sysUser.sex = user.sex
            tokenService.setLoginUser(loginUser)
            return AjaxResult.success()
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员")
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    @Throws(Exception::class)
    fun avatar(@RequestParam("avatarfile") file: MultipartFile, @RequestParam("suffix") suffix: String?): AjaxResult {
        if (!file.isEmpty) {
            val loginUser = loginUser
            val avatar = FileUploadUtils.upload(PlatformConfig.getAvatarPath(), file)
            if (userService.updateUserAvatar(loginUser.username, avatar)) {
                val ajax = AjaxResult.success()
                ajax["imgUrl"] = avatar
                // 更新缓存用户头像
                loginUser.user.avatar = avatar
                tokenService.setLoginUser(loginUser)
                return ajax
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员")
    }
}