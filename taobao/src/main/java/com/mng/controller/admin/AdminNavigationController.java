package com.mng.controller.admin;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.data.UserType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/admin")
@Controller
@LoginRequired
@UserTypeOnly(UserType.ADMIN)
public class AdminNavigationController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String resolveIndex() {
        return "admin/admin-index";
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String resolveUsers() {
        return "admin/users/user-table";
    }

    @RequestMapping(value = "/users/table/add", method = RequestMethod.GET)
    public String resolveTableAdd() {
        return "admin/users/table/add";
    }

    @RequestMapping(value = "/users/table/edit", method = RequestMethod.GET)
    public String resolveTableEdit() {
        return "admin/users/table/edit";
    }

}
