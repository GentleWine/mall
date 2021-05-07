package com.mng.controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.data.UserType;
import com.mng.domain.SellerDomain;
import com.mng.entity.Shop;
import com.mng.util.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@LoginRequired
@UserTypeOnly(UserType.SELLER)
public class SellerController extends ShopControllerBase {

    @RequestMapping(value = "/seller", method = RequestMethod.GET)
    public String seller(Model model, HttpServletRequest request) {
        List<Shop> shops = shopRepository.findByOwnerid(userRepository.findByPhone(request.getSession().getAttribute("phone").toString()).get(0).getUserid());
        List<SellerDomain> sellers = getsellers(shops);
        //TODO: 一个卖家可有多个商店
        if (!sellers.isEmpty()) {
            model.addAttribute("seller", sellers.get(0));
            //model.addAttribute("phone",request.getSession().getAttribute("phone"));
            Log.i("Seller session OK");
        } else {
            Log.i("Seller is empty");
            SellerDomain seller = new SellerDomain();
            seller.setName(request.getSession().getAttribute("username").toString());
            model.addAttribute("seller", seller);
        }
        return "seller";
    }
}
