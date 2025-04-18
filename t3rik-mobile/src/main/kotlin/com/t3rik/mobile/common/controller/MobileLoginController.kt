package com.t3rik.mobile.common.controller

import com.t3rik.common.annotation.RepeatSubmit
import com.t3rik.common.constant.Constants
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.domain.model.LoginBody
import com.t3rik.common.enums.system.ModuleTypeEnum
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.framework.web.service.SysLoginService
import com.t3rik.framework.web.service.SysPermissionService
import com.t3rik.framework.web.service.TokenService
import com.t3rik.mobile.common.service.IMobileLoginService
import com.t3rik.system.service.ISysMenuService
import org.springframework.web.bind.annotation.*
import jakarta.annotation.Resource
import jakarta.servlet.http.HttpServletRequest


@RestController
@RequestMapping(UserConstants.MOBILE_PATH)
class MobileLoginController {

    @Resource
    lateinit var loginService: SysLoginService

    @Resource
    lateinit var permissionService: SysPermissionService

    @Resource
    lateinit var menuService: ISysMenuService

    @Resource
    lateinit var tokenService: TokenService

    @Resource
    lateinit var mobileLoginService: IMobileLoginService


    /**
     * 登录
     */
    @RepeatSubmit
    @PostMapping("/login")
    fun login(@RequestBody loginBody: LoginBody): AjaxResult {
        if (loginBody.username.isNullOrBlank() || loginBody.password.isNullOrBlank()) {
            return AjaxResult.error(MsgConstants.PARAM_ERROR)
        }
        // 生成令牌
        val token = this.loginService.login(loginBody.username, loginBody.password, loginBody.code, loginBody.uuid)
        return AjaxResult.success().apply {
            put(Constants.TOKEN, token)
        }
    }

    /**
     * 获取用户信息
     */
    @GetMapping("getInfo")
    fun getInfo(): AjaxResult {
        val user = SecurityUtils.getLoginUser().user
        // 角色集合
        val roles = this.permissionService.getRolePermission(user)
        // 权限
        val permission = this.permissionService.getMenuPermission(user)
        // 菜单
        val menus =
            this.menuService
                .selectMenuTreeByUserIdAndModuleType(user.userId, ModuleTypeEnum.MOBILE)
                .toMutableList()
        // 移出顶层菜单
        menus.removeIf { it.parentId == 0L }
        return AjaxResult.success().apply {
            put("user", user)
            put("roles", roles)
            put("permissions", permission)
            put("menus", menus)
        }
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): AjaxResult {
        // 获取当前用户
        this.tokenService.getLoginUser(request)?.let { loginUser ->
            // 删除当前用户信息,登出操作
            this.tokenService.delLoginUser(loginUser.token)
        }
        return AjaxResult.success(MsgConstants.SUCCESS)
    }

    @GetMapping("/getDict")
    fun getDict(request: HttpServletRequest): AjaxResult {
        return AjaxResult.success(this.mobileLoginService.getDictList())
    }
}