package com.crm.gym.logging.controllers;

import org.slf4j.MDC;
import java.util.UUID;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class TraceIdInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler)
    {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex)
    {
        MDC.remove("traceId");
    }
}
