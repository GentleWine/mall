package com.mng.component;

import com.mng.util.API;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("phone") == null) {
            API.logInfo("Request URL: " + request.getRequestURL().toString());
            API.logInfo("Request Method: " + request.getMethod());
            request.getRequestDispatcher("/user/login").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
