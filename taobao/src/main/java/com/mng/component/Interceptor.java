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

        // Admin Panel
        if(request.getRequestURL().toString().matches("^(https?://)?[a-zA-Z0-9.-]+/admin(/\\S+)?/?$")) {
            Log.i("Request URL on Admin Section: " + request.getRequestURL());
            Log.i("Request Method on Admin Section: " + request.getMethod());
            return true;
        }
        if (request.getSession().getAttribute("phone") == null) {
            Log.i("Request URL: " + request.getRequestURL());
            Log.i("Request Method: " + request.getMethod());
            request.getRequestDispatcher("/").forward(request, response);
            return false;
        } else {
            return true;
        }
    }
}
