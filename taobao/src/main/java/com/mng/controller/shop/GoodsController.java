package com.mng.controller.shop;

import com.alibaba.fastjson.JSONObject;
import com.mng.bean.AddGoodsBody;
import com.mng.entity.Commodity;

import com.mng.entity.Shop;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class GoodsController extends ShopBase{
    @RequestMapping(value = "/add_goods",method = RequestMethod.POST)
    public JSONObject addGoods(HttpServletRequest request, @ModelAttribute("addGoods") AddGoodsBody addGoodsBody ) throws Exception {
        List<Commodity>  commodityList;

        Integer cateid = addGoodsBody.getKind();
        Integer shopid;
        String name = addGoodsBody.getName();
        String mainimage="1";
        String detail =addGoodsBody.getDescription();
        double price = addGoodsBody.getPrice();
        double amount=100;
        String status="1";

        //System.out.println(name);

        List<Shop> shops = shopRepository.findByOwnerid(userRepository.findByPhone(request.getSession().getAttribute("phone").toString()).get(0).getUserid());
        Shop shop= shops.get(0);
        shopid=shop.getShopid();

        //System.out.println(shopid+"asd");
        //System.out.println(request.getSession().getAttribute("phone").toString());
        try {
            if(name.isEmpty()||detail.isEmpty()){
                throw new Exception("false");
            }else if (!(commodityList = commodityRepository.findByShopidAndName(shopid,name)).isEmpty()) {
                throw new Exception("false");
            } else {

                Commodity commodity=new Commodity();
                commodity.setCateid(cateid);
                commodity.setShopid(shopid);
                commodity.setName(name);
                commodity.setMainimage(mainimage);
                commodity.setDetail(detail);
                commodity.setPrice(price);
                commodity.setAmount(amount);
                commodity.setStatus(status);
                commodityRepository.save(commodity);
                JSONObject json = new JSONObject();
                json.put("type","true");
                return json;

            }
        }catch( Exception e){
            JSONObject json = new JSONObject();
            json.put("type", e.getMessage());
            return json;
        }
    }
    @RequestMapping(value = "/delete_goods",method = RequestMethod.POST)
    public JSONObject deleteGoods(HttpServletRequest request, @RequestParam("comid") Integer comid) throws Exception {
        List<Commodity>  commodityList;
        //Integer comid = (Integer) request.getSession().getAttribute("comid");

        System.out.println(comid);
//        commodityList=commodityRepository.findAll();
//        for(Commodity c : commodityList){
//            System.out.println(c.toString()+"\n");
//        }

        try {
            if(comid.equals(null)){
                throw new Exception("false");
            }else if ((commodityList = commodityRepository.findByComid(comid)).isEmpty()) {
                throw new Exception("false");
            } else {
                commodityRepository.deleteByComid(comid);
                JSONObject json = new JSONObject();
                json.put("type","success");
                return json;

            }
        }catch( Exception e){
            JSONObject json = new JSONObject();
            json.put("type", e.getMessage());
            return json;
        }
    }
}
