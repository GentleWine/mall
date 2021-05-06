package com.mng.component;

import com.mng.util.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Interceptor implements HandlerInterceptor {

    public static String[] requireLoginPaths = new String[]{
            "home",
            "seller",
            "add_goods",
            "delete_goods",
            "changeGoodsAmount"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        // Admin Panel
        if (request.getRequestURL().toString().matches("^(https?://)?[a-zA-Z0-9.-]+/admin(/\\S+)?/?$")) {
            String loginUrl = request.getContextPath() + "/admin/login";
            Log.i("Request URL on Admin Section: " + request.getRequestURL());
            Log.i("Request Method on Admin Section: " + request.getMethod());
            if (request.getSession().getAttribute("admin_username") == null && !request.getRequestURL().toString().endsWith(loginUrl)) {
                response.sendRedirect(loginUrl);
                return false;
            }
            return true;
        }

        for (String path : requireLoginPaths) {
            if (request.getRequestURL().toString().matches("^(https?://)?[a-zA-Z0-9.-]+/" + path + "(/\\S+)?/?$")) {
                if (request.getSession().getAttribute("phone") == null) {
                    Log.i("Request URL: " + request.getRequestURL());
                    Log.i("Request Method: " + request.getMethod());
                    request.getRequestDispatcher("/").forward(request, response);
                    return false;
                }
            }
        }
        return true;

    }
}
