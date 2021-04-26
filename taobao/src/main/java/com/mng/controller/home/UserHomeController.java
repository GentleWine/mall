package com.mng.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserHomeController {
    @RequestMapping(value = "/user/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String register() {
        return "register";
    }

    @RequestMapping(value = "/user/index",method = RequestMethod.GET)
    public  String index(){ return "redirect:/index";}

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public  String index2(){ return "index";}

    @RequestMapping(value = "/user/seller",method = RequestMethod.GET)
    public  String seller(){ return "redirect:/seller";}

    @RequestMapping(value = "/seller",method = RequestMethod.GET)
    public  String seller2(){ return "seller";}
}
