package com.mng.Controller.orders;


import com.mng.entity.Orders;
import com.mng.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ShopOrdersController {
    @Autowired
    OrderRepository orderRepository;
    @RequestMapping(value = "/seller/orders", method = RequestMethod.GET)
    public String seller(Model model, HttpServletRequest request, @RequestParam("shopid")Integer shopid) {
        //Integer
        //Integer shopid=Integer.parseInt(request.getSession().getAttribute("shopid").toString());
        List<Orders> ordersList = orderRepository.findByShopid(shopid);
        model.addAttribute("allProducts",ordersList);
        return "orders";
    }
}
