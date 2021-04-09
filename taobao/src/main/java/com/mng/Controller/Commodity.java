package com.mng.Controller;

import com.mng.Repository.CategoryRepository;
import com.mng.Repository.CommodityRepository;
import com.mng.Repository.ShopRepository;
import com.mng.domain.Item;
import com.mng.domain.Kind;
import com.mng.domain.Seller;
import com.mng.domain.User;
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
public class Commodity {
    @Autowired
    CommodityRepository commodityRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public String home(Model model, HttpServletRequest request)
    {
        //TODO: 作为请求参数amount
        Pageable pageable = PageRequest.of(1,30, Sort.Direction.DESC,"time");
        Page<com.mng.entity.Shop> pages = shopRepository.findAll(pageable);
        List<com.mng.entity.Shop> shops = pages.getContent();
        User user = new User();
        user.setName(request.getSession().getAttribute("username").toString());

        List<Seller> sellers = new ArrayList<>();
        for (com.mng.entity.Shop shop:shops)
        {
            Seller seller = new Seller();
            seller.setName(shop.getShopname());
            List<Kind>kinds = new ArrayList<>();
            List<com.mng.entity.Commodity>commodities = commodityRepository.findByShopid(shop.getShopid());
            //通过map按属性分组
            Map<Integer,List<com.mng.entity.Commodity>> collect = commodities.stream().collect(Collectors.groupingBy(com.mng.entity.Commodity::getCateid));
            for(Integer key:collect.keySet())
            {
                Kind kind = new Kind();
                kind.setName(categoryRepository.findByCateid(key).get(0).getName());
                List<com.mng.entity.Commodity>partition = collect.get(key);
                List<Item>items = new ArrayList<>();
                for(com.mng.entity.Commodity commodity:partition)
                {
                    Item item = new Item();
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
        model.addAttribute("sellers",sellers);
        return "index";
    }
}
