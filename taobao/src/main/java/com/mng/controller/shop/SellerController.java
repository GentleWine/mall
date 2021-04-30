package com.mng.Controller.shop;

import com.mng.Repository.UserRepository;
import com.mng.domain.SellerDomain;
import com.mng.entity.Shop;
import com.mng.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class SellerController extends ShopBase{
    @Autowired
    UserRepository userRepository;
    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public String seller(Model model, HttpServletRequest request) {
        List<Shop> shops = shopRepository.findByOwnerid(userRepository.findByPhone(request.getSession().getAttribute("phone").toString()).get(0).getUserid());
        List<SellerDomain> sellers = getsellers(shops);
        //TODO: 一个卖家可有多个商店
        if (!sellers.isEmpty()) {
            model.addAttribute("seller", sellers.get(0));
            Log.i("ok");
        }
        return "seller";
    }

}
