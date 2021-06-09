package com.mng.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//for static resource mapping
@Configuration
public class ResourceMap implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/register").setViewName("register");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        //registry.addResourceHandler("/imagess/**").addResourceLocations("file:C:/Users/LENOVO/Desktop/imagess/");
        registry.addResourceHandler("/imagess/**").addResourceLocations("file:/home/ubuntu/mall/resource/imagess/");
    }

}
