package com.mng.controller.orders;


import com.mng.entity.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ShopOrdersController extends OrderControllerBase {
    @RequestMapping(value = "/seller/orders", method = RequestMethod.GET)
    public String seller(Model model, HttpServletRequest request, @RequestParam("shopid") Integer shopid) {
        //Integer
        //Integer shopid=Integer.parseInt(request.getSession().getAttribute("shopid").toString());
        List<Order> ordersList = orderRepository.findByShopid(shopid);
        model.addAttribute("allProducts", ordersList);
        model.addAttribute("shopid", shopid);
        return "orders";
    }
}
