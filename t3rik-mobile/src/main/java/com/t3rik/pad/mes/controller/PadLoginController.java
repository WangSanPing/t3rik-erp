package com.t3rik.pad.mes.controller;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.model.LoginUser;
import com.t3rik.framework.web.service.TokenService;
import com.t3rik.pad.mes.service.PadLoginService;
import com.t3rik.system.domain.LoginParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 登录控制器
 *
 * @author SK
 * @since 2018/6/13
 */
@RestController
@RequestMapping(UserConstants.PAD_PATH + "/login")
public class PadLoginController {

    @Autowired(required = false)
    private TokenService tokenService;

    @Autowired(required = false)
    private PadLoginService loginService;


    /**
     * 会员登录
     *
     * @return -1 用户名或密码错误  -2 账号冻结  -3 账号锁定 1 成功  -4 验证码错误
     */
    @PostMapping("/loginByPassword")
    @ResponseBody
    public AjaxResult loginByPassword(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String phoneNo = request.getParameter("phoneNo");
        String validCode = request.getParameter("validCode");
        String loginType = request.getParameter("loginType");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setUsername(username);
        loginParams.setPassword(password);
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCode(validCode);
        loginParams.setLoginType(loginType);
        return loginService.login(loginParams);
    }

    /**
     * 发送验证码
     */
    @PostMapping("/sendCode")
    @ResponseBody
    public AjaxResult sendRegisterCode(HttpServletRequest request) {
        String phoneNo = request.getParameter("phoneNo");
        String validCodeType = request.getParameter("validCodeType");
        // 登录结果
        LoginParams loginParams = new LoginParams();
        loginParams.setPhoneNo(phoneNo);
        loginParams.setValidCodeType(validCodeType);
        return loginService.sendCode(loginParams);
    }

    @GetMapping("/logout")
    @ResponseBody
    public AjaxResult logout(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (null != loginUser) {
            tokenService.delLoginUser(loginUser.getToken());
        }
        return AjaxResult.success("退出成功！");
    }

}
