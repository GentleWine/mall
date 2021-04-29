package com.mng.controller.admin;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin")
public class AdminNavigationController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String resolveLogin() {
        return "admin_login";
    }
}
