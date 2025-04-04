package com.t3rik.framework.filter;

import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.ip.IpUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 黑名单拦截器
 *
 * @author t3rik
 * @date 2025/4/4 19:03
 */
@Slf4j
@Component
public class BlackListFilter extends OncePerRequestFilter {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ipAddr = IpUtils.getIpAddr(request);
        String BLACK_LIST_KEY = "ip:blacklist";
        String ipKey = "ip:count:" + ipAddr;

        // 先检查是否在黑名单中
        if (Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(BLACK_LIST_KEY, ipAddr))) {
            log.warn("当前ip已被限制黑名单访问 : {}", ipAddr);
            throw new BusinessException("短时间内访问次数过多，已被限制访问");
        }

        // 自增访问次数，如果是第一次访问，设置过期时间为1分钟
        Long count = redisTemplate.opsForValue().increment(ipKey);
        if (count != null && count == 1) {
            // 设置访问计数键 1 分钟后过期
            redisTemplate.expire(ipKey, 1, TimeUnit.MINUTES);
        }

        // 超过15次访问，则加入黑名单
        if (count != null && count > 15) {
            redisTemplate.opsForSet().add(BLACK_LIST_KEY, ipAddr);
        }

        filterChain.doFilter(request, response);
    }
}
