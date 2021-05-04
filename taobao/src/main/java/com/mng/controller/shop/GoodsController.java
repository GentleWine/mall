package com.mng.controller.shop;

import com.mng.bean.AddGoodsBody;
import com.mng.entity.Commodity;
import com.mng.entity.Shop;
import com.mng.exception.goods.GoodAddFailedException;
import com.mng.exception.goods.GoodAlterFailedException;
import com.mng.exception.goods.GoodDeleteFailedException;
import com.mng.exception.goods.GoodException;
import com.mng.util.JsonBuilder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class GoodsController extends ShopControllerBase {
    @RequestMapping(value = "/add_goods", method = RequestMethod.POST)
    public String addGoods(HttpServletRequest request, @ModelAttribute("addGoods") AddGoodsBody addGoodsBody) {

        Integer cateid = addGoodsBody.getKind();
        Integer shopid;
        String name = addGoodsBody.getName();
        String mainimage = "1";
        String detail = addGoodsBody.getDescription();
        double price = addGoodsBody.getPrice();
        double amount = 100;
        String status = "1";

        List<Shop> shops = shopRepository.findByOwnerid(userRepository.findByPhone(request.getSession().getAttribute("phone").toString()).get(0).getUserid());
        Shop shop = shops.get(0);
        shopid = shop.getShopid();

        try {
            if ("".equals(name) || "".equals(detail)) {
                throw new GoodAddFailedException("Name or detail is empty!");
            } else if (!commodityRepository.findByShopidAndName(shopid, name).isEmpty()) {
                throw new GoodAddFailedException("Shop does not exist!");
            } else {

                Commodity commodity = new Commodity();
                commodity.setCateid(cateid);
                commodity.setShopid(shopid);
                commodity.setName(name);
                commodity.setMainimage(mainimage);
                commodity.setDetail(detail);
                commodity.setPrice(price);
                commodity.setAmount(amount);
                commodity.setStatus(status);
                commodityRepository.save(commodity);
                return JsonBuilder.newObject()
                        .put("success", true)
                        .build();

            }
        } catch (GoodException e) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("error", e.toString())
                    .build();
        }
    }

    @RequestMapping(value = "/delete_goods", method = RequestMethod.POST)
    public String deleteGoods(HttpServletRequest request, @RequestParam("comid") Integer comid) {

        System.out.println(comid);
        try {
            if (comid == null) {
                throw new GoodDeleteFailedException("Commodity cannot be null");
            } else if (commodityRepository.findByComid(comid).isEmpty()) {
                throw new GoodDeleteFailedException("Commodity cannot be empty");
            } else {
                commodityRepository.deleteByComid(comid);
                return JsonBuilder.newObject()
                        .put("success", true)
                        .build();
            }
        } catch (GoodException e) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("error", e.toString())
                    .build();
        }
    }


    @RequestMapping(value = "/changeGoodsAmount", method = RequestMethod.POST)
    public String changeGoodsAmount(HttpServletRequest request, @RequestParam("comid") Integer comid, @RequestParam("delta_amount") Double delta_amount) {
        List<Commodity> commodityList;
        System.out.println(comid);
        try {
            if (comid == null || delta_amount == null) {
                throw new GoodAlterFailedException("ID or changed amount is null");
            } else if ((commodityList = commodityRepository.findByComid(comid)).isEmpty()) {
                throw new GoodAlterFailedException("Commodity with this id does not exist!");
            } else if (commodityList.get(0).getAmount() + delta_amount < 0) {
                throw new GoodAlterFailedException("Amount can't be changed to a negative value!");
            } else {
                Double newAmount = commodityList.get(0).getAmount() + delta_amount;
                commodityRepository.updateAmountByComid(newAmount, comid);
                return JsonBuilder.newObject()
                        .put("success", true)
                        .build();
            }
        } catch (GoodException e) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("error", e.toString())
                    .build();
        }
    }
}
