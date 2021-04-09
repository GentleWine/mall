package com.mng.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/user")
public class UserHomepageController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String jumpLogin() {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String jumpRegister() {
        return "register";
    }
}
