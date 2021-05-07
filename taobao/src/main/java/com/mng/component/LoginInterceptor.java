package com.mng.component;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.bean.LoginBody;
import com.mng.controller.account.LoginController;
import com.mng.controller.account.LogoutController;
import com.mng.data.UserType;
import com.mng.repository.UserRepository;
import com.mng.util.CookieCipher;
import com.mng.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    protected UserRepository userRepository;

    @Autowired
    public LoginInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static void redirectLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(request.getContextPath() + "/");
    }

    public static boolean attemptLoginWithCookieOrRedirect(UserRepository repository, HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean result = loginWithCookie(repository, request, response);
        if (!result) {
            LogoutController.clearCookies(response);
        }
        return result;
    }

    public static boolean loginWithCookie(UserRepository repository, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            redirectLogin(request, response);
            return false;
        } else {
            Cookie phone = null;
            Cookie cipheredPassword = null;
            for (Cookie cookie : cookies) {
                if ("phone".equals(cookie.getName())) {
                    phone = cookie;
                }
                if ("password".equals(cookie.getName())) {
                    cipheredPassword = cookie;
                }
            }
            if (phone == null || cipheredPassword == null) {
                redirectLogin(request, response);
                return false;
            } else {
                try {
                    String phoneNumber = phone.getValue();
                    String password = CookieCipher.getInstance().decrypt(cipheredPassword.getValue());
                    LoginController.resolveLogin(repository, request, response, new LoginBody(phoneNumber, password, false));
                    Object userTypeAttribute = request.getSession().getAttribute("usertype");
                    Object phoneAttribute = request.getSession().getAttribute("phone");
                    if (userTypeAttribute != null && phoneAttribute != null) {
                        return true;
                    }
                    Log.i("Log in With Cookie Failed");
                    redirectLogin(request, response);
                    return false;
                } catch (GeneralSecurityException e) {
                    Log.e(e);
                    redirectLogin(request, response);
                    return false;
                }
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            LoginRequired loginRequired = method.getAnnotation(LoginRequired.class);
            Object userType = request.getSession().getAttribute("usertype");
            Object phone = request.getSession().getAttribute("phone");
            if (loginRequired == null) {
                loginRequired = method.getDeclaringClass().getAnnotation(LoginRequired.class);
            }
            if (loginRequired != null) {
                if (phone == null || userType == null) {
                    return attemptLoginWithCookieOrRedirect(userRepository, request, response);
                } else {
                    UserTypeOnly userTypeOnly = method.getAnnotation(UserTypeOnly.class);
                    if (userTypeOnly == null) {
                        userTypeOnly = method.getDeclaringClass().getAnnotation(UserTypeOnly.class);
                    }
                    if (userTypeOnly != null) {
                        List<UserType> allowedTypes = Arrays.asList(userTypeOnly.value());
                        if (!(userType instanceof UserType) || !allowedTypes.contains((UserType) userType)) {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
