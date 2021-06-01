package com.mng.Controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.domain.KindDomain;
import com.mng.domain.SellerDomain;
import com.mng.domain.UserDomain;
import com.mng.domain.ItemDomain;
import com.mng.entity.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@LoginRequired
public class SearchController extends ShopControllerBase {

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String search(@RequestParam("content") String content,Model model, HttpServletRequest request) {

        Pageable pageable = PageRequest.of(0, 30, Sort.Direction.DESC, "shopid");
        Page<Shop> pages = shopRepository.findAll(pageable);
        List<Shop> shops = pages.getContent();
        UserDomain user = new UserDomain();
        Object username = request.getSession().getAttribute("username");
        if (username != null) {
            user.setName(username.toString());
        }

        List<SellerDomain> sellers = getSellers(shops);

        //删除name不是content的元素
        for (SellerDomain i : sellers) {
            List<KindDomain> kinds=i.getKinds();
            System.out.println(kinds.toString());
            for (int j=0; j<kinds.size();j++) {
                System.out.println(kinds.get(j));
                if (!Objects.equals(kinds.get(j).getName(), content)){
                    kinds.remove(j);
                    j--;
                }
            }
            i.setKinds(kinds);
        }

        model.addAttribute("sellers", sellers);
        model.addAttribute("user", user);
        System.out.println(sellers.toString());
        return "search";
    }
}