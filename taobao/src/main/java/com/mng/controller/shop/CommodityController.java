package com.mng.controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.bean.AddGoodsBody;
import com.mng.data.UserType;
import com.mng.entity.Commodity;
import com.mng.entity.Shop;
import com.mng.exception.goods.CommodityAddFailedException;
import com.mng.exception.goods.CommodityAlterFailedException;
import com.mng.exception.goods.CommodityDeleteFailedException;
import com.mng.exception.goods.CommodityException;
import com.mng.util.JsonBuilder;
import com.mng.util.VerificationUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@LoginRequired
@UserTypeOnly(UserType.SELLER)
public class CommodityController extends ShopControllerBase {
    @RequestMapping(value = "/add-goods", method = RequestMethod.POST)
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
            if (VerificationUtil.anyIsEmpty(name, detail)) {
                throw new CommodityAddFailedException("Name or detail is empty!");
            } else if (!commodityRepository.findByShopidAndName(shopid, name).isEmpty()) {
                throw new CommodityAddFailedException("Shop does not exist!");
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
        } catch (CommodityException e) {
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
                throw new CommodityDeleteFailedException("Commodity ID cannot be null");
            } else if (commodityRepository.findByComid(comid).isEmpty()) {
                throw new CommodityDeleteFailedException("Commodity not found with the given ID");
            } else {
                commodityRepository.deleteByComid(comid);
                return JsonBuilder.newObject()
                        .put("success", true)
                        .build();
            }
        } catch (CommodityException e) {
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
                throw new CommodityAlterFailedException("ID or changed amount is null");
            } else if ((commodityList = commodityRepository.findByComid(comid)).isEmpty()) {
                throw new CommodityAlterFailedException("Commodity with this id does not exist!");
            } else if (commodityList.get(0).getAmount() + delta_amount < 0) {
                throw new CommodityAlterFailedException("Amount can't be changed to a negative value!");
            } else {
                Double newAmount = commodityList.get(0).getAmount() + delta_amount;
                commodityRepository.updateAmountByComid(newAmount, comid);
                return JsonBuilder.newObject()
                        .put("success", true)
                        .build();
            }
        } catch (CommodityException e) {
            return JsonBuilder.newObject()
                    .put("success", false)
                    .put("error", e.toString())
                    .build();
        }
    }
}
