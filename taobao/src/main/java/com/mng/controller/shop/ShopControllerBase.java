package com.mng.controller.shop;

import com.mng.domain.ItemDomain;
import com.mng.domain.KindDomain;
import com.mng.domain.SellerDomain;
import com.mng.entity.Commodity;
import com.mng.entity.Shop;
import com.mng.repository.CategoryRepository;
import com.mng.repository.CommodityRepository;
import com.mng.repository.ShopRepository;
import com.mng.repository.UserRepository;
import com.mng.repository.redis.ScRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ShopControllerBase {
    @Autowired
    protected CommodityRepository commodityRepository;
    @Autowired
    protected ShopRepository shopRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected ScRepository scRepository;

    public List<SellerDomain> getSellers(List<Shop> shops) {
        List<SellerDomain> sellers = new ArrayList<>();
        for (Shop shop : shops) {
            SellerDomain seller = new SellerDomain();
            seller.setName(shop.getShopname());
            seller.setAddress(shop.getAddress());
            seller.setShopid(shop.getShopid());
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
                    item.setId(commodity.getComid());
                    item.setAmount(commodity.getAmount());
                    item.setAddress("ShangHai");
                    if (commodity.getMainimage().equals("1")) {
                        item.setImgUrl("favicon.png");
                    } else {
                        item.setImgUrl(commodity.getMainimage());
                    }
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
