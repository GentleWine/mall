package com.mng.Controller.shop;

import com.mng.domain.ItemDomain;
import com.mng.domain.KindDomain;
import com.mng.domain.SellerDomain;
import com.mng.domain.UserDomain;
import com.mng.entity.Commodity;
import com.mng.entity.Shop;
import com.mng.repository.CategoryRepository;
import com.mng.repository.CommodityRepository;
import com.mng.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class CommodityController {
    @Autowired
    CommodityRepository commodityRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request) {
        //TODO: 作为请求参数amount
        Pageable pageable = PageRequest.of(1, 30, Sort.Direction.DESC, "shopid");
        Page<Shop> pages = shopRepository.findAll(pageable);
        List<Shop> shops = pages.getContent();
        UserDomain user = new UserDomain();
        user.setName(request.getSession().getAttribute("username").toString());

        List<SellerDomain> sellers = new ArrayList<>();
        for (Shop shop : shops) {
            SellerDomain seller = new SellerDomain();
            seller.setName(shop.getShopname());
            List<KindDomain> kinds = new ArrayList<>();
            List<Commodity> commodities = commodityRepository.findByShopid(shop.getShopid());
            //通过map按属性分组
            Map<Integer, List<Commodity>> collect = commodities.stream().collect(Collectors.groupingBy(Commodity::getCateid));
            for (Integer key : collect.keySet()) {
                KindDomain kind = new KindDomain();
                kind.setName(categoryRepository.findByCateid(key).get(0).getName());
                List<Commodity> partition = collect.get(key);
                List<ItemDomain> items = new ArrayList<>();
                for (Commodity commodity : partition) {
                    ItemDomain item = new ItemDomain();
                    item.setItem_title(commodity.getName());
                    item.setPrice(commodity.getPrice());
                    item.setSeller_info(commodity.getDetail());
                    items.add(item);
                }
                kind.setItems(items);
                kinds.add(kind);
            }
            seller.setKinds(kinds);
            sellers.add(seller);
        }
        model.addAttribute("user", user);
        model.addAttribute("sellers", sellers);
        return "index";
    }
}
