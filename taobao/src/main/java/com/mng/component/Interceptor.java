package com.mng.component;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;
@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("phone")==null) {
            System.out.println(request.getRequestURL());
            System.out.println(request.getMethod());
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        }
        else
            return true;
    }
}
