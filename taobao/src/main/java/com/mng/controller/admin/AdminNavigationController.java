package com.mng.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin")
@Controller
public class AdminNavigationController {
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String resolveLogin() {
        return "admin/admin-login";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String resolveUsers() {
        return "admin/user-table";
    }

    @RequestMapping(value = "/table/add", method = RequestMethod.GET)
    public String resolveTableAdd() {
        return "admin/table/add";
    }

    @RequestMapping(value = "/table/edit", method = RequestMethod.GET)
    public String resolveTableEdit() {
        return "admin/table/edit";
    }

}
