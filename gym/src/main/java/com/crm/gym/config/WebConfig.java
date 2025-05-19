package com.crm.gym.config;

import com.crm.gym.logging.controllers.TraceIdInterceptor;
import org.springframework.context.annotation.Configuration;

import com.crm.gym.logging.controllers.RestLoggingInterceptor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    private TraceIdInterceptor traceIdInterceptor;
    private RestLoggingInterceptor restLoggingInterceptor;

    public WebConfig(TraceIdInterceptor traceIdInterceptor, RestLoggingInterceptor restLoggingInterceptor)
    {
        this.traceIdInterceptor = traceIdInterceptor;
        this.restLoggingInterceptor = restLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(traceIdInterceptor);
        registry.addInterceptor(restLoggingInterceptor);
    }
}
