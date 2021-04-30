package com.mng.controller.shop;

import com.mng.Repository.CategoryRepository;
import com.mng.Repository.CommodityRepository;
import com.mng.Repository.ShopRepository;
import com.mng.Repository.UserRepository;
import com.mng.domain.ItemDomain;
import com.mng.domain.KindDomain;
import com.mng.domain.SellerDomain;
import com.mng.entity.Commodity;
import com.mng.entity.Shop;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopBase {
    @Autowired
    CommodityRepository commodityRepository;
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserRepository userRepository;
    public List<SellerDomain> getsellers(List<Shop> shops)
    {
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
        return sellers;
    }
}
