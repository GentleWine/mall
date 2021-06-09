package com.mng.controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.domain.SellerDomain;
import com.mng.domain.UserDomain;
import com.mng.entity.Shop;
import com.mng.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@LoginRequired
public class CustomerController extends ShopControllerBase {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        //TODO: 作为请求参数amount
        //attention: zero-based page index
        Pageable pageable = PageRequest.of(0, 30, Sort.Direction.DESC, "shopid");
        Page<Shop> pages = shopRepository.findAll(pageable);
        List<Shop> shops = pages.getContent();
        UserDomain user = new UserDomain();
        Object username = request.getSession().getAttribute("username");
        if (username != null) {
            user.setName(username.toString());
        }
        List<User> u = userRepository.findByUsername(user.getName());
        Double spentmoney = u.get(0).getSpentmoney();
        user.setSpent_money(spentmoney);

        List<SellerDomain> sellers = getSellers(shops);

        //购物车请求合并
        String phone = request.getSession().getAttribute("phone").toString();
        List<com.mng.entity.redis.Shoppingcart> items = scRepository.findByPhone(phone);

        model.addAttribute("sellers", sellers);
        model.addAttribute("user", user);
        model.addAttribute("Shopping_cart", items);
        return "index";
    }
}
