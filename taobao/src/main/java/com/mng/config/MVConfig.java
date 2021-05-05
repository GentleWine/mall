package com.mng.config;

import com.mng.component.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVConfig implements WebMvcConfigurer {
    /*
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/user/register").setViewName("index");
    }
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor()).excludePathPatterns("/", "/register", "/error", "/user/login", "/user/register", "/*/*.svg", "/*/*.woff2", "/*/*.woff", "/*/*.ttf", "/*/*.html", "/*/*.htm", "/*/*.css", "/*/*.js", "/*/*.png", "/*/*.jpg", "/*/*.jpeg", "/*/*.ico");

    }

}