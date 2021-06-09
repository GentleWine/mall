package com.mng.controller.orders;


import com.mng.entity.Order;
import com.mng.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserOrdersController extends OrderControllerBase {
    @RequestMapping(value = "/user/orders", method = RequestMethod.GET)
    public String seller(Model model, HttpServletRequest request, @RequestParam("username") String name) {
        //Integer
        //Integer shopid=Integer.parseInt(request.getSession().getAttribute("shopid").toString());
        List<User> users = userRepository.findByUsername(name);
        User user = users.get(0);
        List<Order> ordersList = orderRepository.findByUserid(user.getUserid());

        model.addAttribute("allProducts", ordersList);
        return "userorders";
    }
}
