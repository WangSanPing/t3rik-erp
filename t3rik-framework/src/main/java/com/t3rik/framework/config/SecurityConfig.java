package com.t3rik.framework.config;

import com.t3rik.common.constant.UserConstants;
import com.t3rik.framework.filter.BlackListFilter;
import com.t3rik.framework.security.filter.JwtAuthenticationTokenFilter;
import com.t3rik.framework.security.handle.AuthenticationEntryPointImpl;
import com.t3rik.framework.security.handle.LogoutSuccessHandlerImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

/**
 * spring-boot 3.0 + spring security配置
 *
 * @author t3rik
 * @date 2024/8/8 16:04
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 自定义用户认证逻辑
     */
    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 认证失败处理类
     */
    @Resource
    private AuthenticationEntryPointImpl unauthorizedHandler;

    /**
     * 退出处理类
     */
    @Resource
    private LogoutSuccessHandlerImpl logoutSuccessHandler;

    /**
     * token认证过滤器
     */
    @Resource
    private JwtAuthenticationTokenFilter authenticationTokenFilter;

    /**
     * 黑名单过滤
     */
    @Resource
    private BlackListFilter blackListFilter;

    /**
     * 跨域过滤器
     */
    @Resource
    private CorsFilter corsFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // CSRF禁用，因为不使用session
                .csrf(AbstractHttpConfigurer::disable)
                // 认证失败处理类
                .exceptionHandling(e -> e.authenticationEntryPoint(unauthorizedHandler))
                // 基于token，所以不需要session
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 过滤请求
                .authorizeHttpRequests(
                        authorizeHttpRequests ->
                                // 对于登录login 注册register 验证码captchaImage 允许匿名访问
                                authorizeHttpRequests.requestMatchers("/login", "/register", "/captchaImage", "/mobile/captchaImage"
                                                , UserConstants.MOBILE_PATH + "/login")
                                        .anonymous()
                                        .requestMatchers(
                                                HttpMethod.GET,
                                                "/",
                                                "/*.html",
                                                "/profile/**"
                                        ).permitAll()
                                        .requestMatchers("/jmreport/**", "/drag/**", "/favicon.ico").permitAll()
                                        .requestMatchers("/error").permitAll()
                                        .requestMatchers("/swagger-ui.html").anonymous()
                                        .requestMatchers("/swagger-resources/**").anonymous()
                                        .requestMatchers("/webjars/**").anonymous()
                                        .requestMatchers("/*/api-docs").anonymous()
                                        .requestMatchers("/druid/**").anonymous()
                                        .requestMatchers("/websocket/**").anonymous()
                                        .requestMatchers("/system/autocode/get/**"
                                                , UserConstants.MOBILE_PATH + "/logout").permitAll()
                                        // 除上面外的所有请求全部需要鉴权认证
                                        .anyRequest().authenticated()

                ).headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        // 身份认证
        httpSecurity.userDetailsService(userDetailsService);
        // 退出配置
        httpSecurity.logout(o -> o.logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler));
        // 黑名单过滤
        httpSecurity.addFilterBefore(blackListFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加JWT filter
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加CORS filter
        httpSecurity.addFilterBefore(corsFilter, JwtAuthenticationTokenFilter.class);
        httpSecurity.addFilterBefore(corsFilter, LogoutFilter.class);
        return httpSecurity.build();
    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //
    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

    /**
     * 调用SecurityContextHolder的setStrategyName方法,
     * 改变Threadlocal模式为InheritableThreadLocal,
     * 使其可以在子线程中传递上下文信息.
     * 主要解决在异步线程中无法获取当前登录用户信息的问题
     *
     * @date 2024-6-4
     */
    @Bean
    public MethodInvokingFactoryBean methodInvokingFactoryBean() {
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(SecurityContextHolder.class);
        methodInvokingFactoryBean.setTargetMethod("setStrategyName");
        methodInvokingFactoryBean.setArguments((Object) new String[]{SecurityContextHolder.MODE_INHERITABLETHREADLOCAL});
        return methodInvokingFactoryBean;
    }

}
