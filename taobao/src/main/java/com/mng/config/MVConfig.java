package com.mng.config;

import com.mng.component.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
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


    private final LoginInterceptor loginInterceptor;

    @Autowired
    public MVConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).excludePathPatterns(
                "/",
                "/register",
                "/error",
                "/user/login",
                "/user/register",
                "/*/*.eot",
                "/*/*.svg",
                "/*/*.woff2",
                "/*/*.woff",
                "/*/*.ttf",
                "/*/*.html",
                "/*/*.htm",
                "/*/*.css",
                "/*/*.js",
                "/*/*.png",
                "/*/*.jpg",
                "/*/*.jpeg",
                "/*/*.ico"
        );
    }
}