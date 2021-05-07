package com.mng.controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.domain.SellerDomain;
import com.mng.domain.UserDomain;
import com.mng.entity.Shop;
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
public class CommodityController extends ShopControllerBase {

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

        List<SellerDomain> sellers = getsellers(shops);
        model.addAttribute("sellers", sellers);
        model.addAttribute("user", user);
        return "index";
    }
}
