package com.t3rik.framework.filter;

import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.ip.IpUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

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
    private Jedis jedis;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String ipAddr = IpUtils.getIpAddr(request);
        String BLACK_LIST_KEY = "ip:blacklist";
        String ipKey = "ip:count:" + ipAddr;

        // 1. 检查是否在黑名单中
        if (jedis.sismember(BLACK_LIST_KEY, ipAddr)) {
            System.out.println("当前ip已被限制黑名单访问 : " + ipAddr);
            throw new BusinessException("短时间内访问次数过多，已被限制访问");
        }

        // 2. 自增访问次数
        Long count = jedis.incr(ipKey);  // 自增访问次数
        if (count == 1) {
            // 设置访问计数键 1 分钟后过期
            jedis.expire(ipKey, (int) TimeUnit.MINUTES.toSeconds(1));  // 转换为秒
        }

        // 3. 超过15次访问，则加入黑名单
        if (count > 15) {
            jedis.sadd(BLACK_LIST_KEY, ipAddr);  // 将 IP 地址加入黑名单集合
        }

        filterChain.doFilter(request, response);
    }
}
