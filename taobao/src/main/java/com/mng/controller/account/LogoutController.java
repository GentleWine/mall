package com.mng.controller.account;

import com.mng.component.LoginInterceptor;
import com.mng.util.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class LogoutController extends AccountControllerBase {

    public static void clearCookies(HttpServletResponse response) {
        final String[] cookies = new String[]{"phone", "usertype", "password"};
        for (String cookieString : cookies) {
            Cookie cookie = new Cookie(cookieString, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("phone");
        request.getSession().removeAttribute("username");
        request.getSession().removeAttribute("mail");
        request.getSession().removeAttribute("usertype");
        request.getSession().removeAttribute("usertypeId");
        request.getSession().removeAttribute("mail");
        clearCookies(response);
        try {
            LoginInterceptor.redirectLogin(request, response);
        } catch (IOException e) {
            Log.e(e);
        }
    }
}
