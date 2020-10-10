package com.tyreal.myTest.core.configuration;

import com.tyreal.myTest.core.interceptors.PermissionInterceptors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InerceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getPermissionInterceptor(){
        return new PermissionInterceptors();
    };

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getPermissionInterceptor());
    }
}
