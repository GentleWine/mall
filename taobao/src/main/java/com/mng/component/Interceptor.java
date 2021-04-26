package com.mng.component;

import com.mng.util.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("phone") == null) {
            Log.i("Request URL: " + request.getRequestURL());
            Log.i("Request Method: " + request.getMethod());
            request.getRequestDispatcher("/user/login").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
