// package com.t3rik.mes.aspect;
//
// import org.aspectj.lang.ProceedingJoinPoint;
// import org.aspectj.lang.annotation.Around;
// import org.aspectj.lang.annotation.Aspect;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
// import org.springframework.web.context.request.RequestContextHolder;
// import org.springframework.web.context.request.ServletRequestAttributes;
//
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.HashMap;
// import java.util.Map;
//
// /**
//  * @author 施子安
//  * @version 1.0.0
//  * @date 2024/7/8 16:22
//  * @deprecated 切面类，处理@EnableOnOff注解。
//  */
// @Aspect
// @Component
// @Order(10)
// public class EnableOnOffAspect {
//
//     /**
//      * 在带@EnableOnOff注解的方法执行前拦截。
//      * 处理添加'onOff'参数到POST请求和重定向带'onOff'参数的GET请求。
//      *
//      * @param joinPoint 切点对象，用于访问方法参数
//      * @throws IOException 如果重定向过程中遇到IO错误
//      */
//     @Around("@annotation(enableOnOffAnnotation)")
//     public Object around(ProceedingJoinPoint joinPoint, EnableOnOff enableOnOffAnnotation) throws Throwable {
//         ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//         HttpServletResponse response = attributes.getResponse();
//         HttpServletRequest request = attributes.getRequest();
//         String token = request.getHeader("Authorization");
//
//         Object result = null;
//         String requestMethod = request.getMethod();
//         if ("POST".equalsIgnoreCase(requestMethod)) {
//             result = handlePostRequest(joinPoint, enableOnOffAnnotation, response);
//         } else if ("GET".equalsIgnoreCase(requestMethod)) {
//             result = handleGetRequest(request, response, enableOnOffAnnotation, token, joinPoint);
//         }
//         return result;
//     }
//
//
//     /**
//      * 处理在POST请求中添加'onOff'参数，如果不存在的话。
//      *
//      * @param joinPoint             切点对象，用于访问方法参数
//      * @param enableOnOffAnnotation EnableOnOff注解对象，用于获取注解参数
//      * @param response
//      */
//     private Object handlePostRequest(ProceedingJoinPoint joinPoint, EnableOnOff enableOnOffAnnotation, HttpServletResponse response) throws Throwable {
//         Map<String, Object> originalParams = (Map<String, Object>) joinPoint.getArgs()[0];
//         Map<String, Object> newParams = new HashMap<>(originalParams);
//         if (!newParams.containsKey("onOff")) {
//             newParams.put("onOff", enableOnOffAnnotation.onOff());
//         }
//
//         Object[] newArgs = joinPoint.getArgs();
//         newArgs[0] = newParams;
//
//         return joinPoint.proceed(newArgs);
//     }
//
//     /**
//      * 处理重定向GET请求，并添加'onOff'参数，如果不存在的话。
//      *
//      * @param request               HttpServletRequest对象，用于获取参数
//      * @param response              HttpServletResponse对象，用于重定向
//      * @param enableOnOffAnnotation EnableOnOff注解对象，用于获取注解参数
//      * @param token
//      * @throws IOException 如果重定向过程中遇到IO错误
//      */
//     private Object handleGetRequest(HttpServletRequest request, HttpServletResponse response, EnableOnOff enableOnOffAnnotation, String token, ProceedingJoinPoint joinPoint) throws Throwable {
//         Map<String, String[]> paramsMap = request.getParameterMap();
//         response.setHeader("Authorization", token);
//         if (!paramsMap.containsKey("onOff")) {
//             String newUrl = addParameterToUrl(request, "onOff", String.valueOf(enableOnOffAnnotation.onOff()));
//             response.sendRedirect(newUrl);
//             return null;
//         }
//         return joinPoint.proceed();
//     }
//
//     /**
//      * 构建带'onOff'参数的新URL。
//      *
//      * @param request    HttpServletRequest对象，用于获取当前URL
//      * @param paramName  要添加的参数名称
//      * @param paramValue 要添加的参数值
//      * @return 包含新参数的URL字符串
//      */
//     private String addParameterToUrl(HttpServletRequest request, String paramName, String paramValue) {
//         StringBuilder urlBuilder = new StringBuilder(request.getRequestURL().toString());
//         String queryString = request.getQueryString();
//
//         urlBuilder.append("?").append(paramName).append("=").append(paramValue);
//
//         if (queryString != null && !queryString.isEmpty()) {
//             urlBuilder.append("&").append(queryString);
//         }
//
//         return urlBuilder.toString();
//     }
// }
