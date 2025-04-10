package com.t3rik.system.monitor;

import com.alibaba.fastjson2.JSON;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.Constants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.model.LoginUser;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.core.redis.RedisCache;
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.system.domain.SysUserOnline;
import com.t3rik.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * 在线用户监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/online")
public class SysUserOnlineController extends BaseController {
    @Autowired
    private ISysUserOnlineService userOnlineService;

    @Autowired
    private RedisCache redisCache;

    @PreAuthorize("@ss.hasPermi('monitor:online:list')")
    @GetMapping("/list")
    public TableDataInfo list(String ipaddr, String userName) {
        Collection<String> keys = redisCache.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<SysUserOnline>();
        for (String key : keys) {
            try {
                LoginUser user = JSON.parseObject(JSON.toJSONString(redisCache.getCacheObject(key)), LoginUser.class);
                if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                    if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                        userOnlineList.add(userOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                    }
                } else if (StringUtils.isNotEmpty(ipaddr)) {
                    if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                        userOnlineList.add(userOnlineService.selectOnlineByIpaddr(ipaddr, user));
                    }
                } else if (StringUtils.isNotEmpty(userName) && StringUtils.isNotNull(user.getUser())) {
                    if (StringUtils.equals(userName, user.getUsername())) {
                        userOnlineList.add(userOnlineService.selectOnlineByUserName(userName, user));
                    }
                } else {
                    userOnlineList.add(userOnlineService.loginUserToUserOnline(user));
                }
            } catch (Exception e) {
                logger.info("反序列化用户信息失败 ,异常信息： {}", e.getMessage());
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return getDataTable(userOnlineList);
    }

    /**
     * 强退用户
     */
    @PreAuthorize("@ss.hasPermi('monitor:online:forceLogout')")
    @Log(title = "在线用户", businessType = BusinessType.FORCE)
    @DeleteMapping("/{tokenId}")
    public AjaxResult forceLogout(@PathVariable String tokenId) {
        redisCache.deleteObject(Constants.LOGIN_TOKEN_KEY + tokenId);
        return AjaxResult.success();
    }
}
