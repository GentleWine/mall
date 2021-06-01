package com.mng.Controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.annotation.UserTypeOnly;
import com.mng.bean.request.CommodityAddRequest;
import com.mng.bean.response.SimpleResponse;
import com.mng.data.UserType;
import com.mng.entity.Commodity;
import com.mng.entity.Shop;
import com.mng.exception.goods.CommodityAddFailedException;
import com.mng.exception.goods.CommodityAlterFailedException;
import com.mng.exception.goods.CommodityDeleteFailedException;
import com.mng.exception.goods.CommodityException;
import com.mng.util.VerificationUtil;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@LoginRequired
@UserTypeOnly(UserType.SELLER)
public class CommodityController extends ShopControllerBase {
    @RequestMapping(value = "/add_goods", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SimpleResponse addGoods(HttpServletRequest request, @ModelAttribute("addGoods") CommodityAddRequest commodityAddRequest) {
        Integer cateid = commodityAddRequest.getKind();
        Integer shopid;
        String name = commodityAddRequest.getName();
        //System.out.println(name);
        MultipartFile image=commodityAddRequest.getImage();
        String detail = commodityAddRequest.getDescription();
        double price = commodityAddRequest.getPrice();
        double amount = commodityAddRequest.getAmount();
        //System.out.println(amount);
        String status = "1";
        try {
            try {
                List<Shop> shops = shopRepository.findByOwnerid(userRepository.findByPhone(request.getSession().getAttribute("phone").toString()).get(0).getUserid());
                Shop shop = shops.get(0);
                shopid = shop.getShopid();
            } catch (IndexOutOfBoundsException e) {
                throw new CommodityAddFailedException("No shop found for current seller!");
            }
            if (VerificationUtil.anyIsEmpty(name, detail,image)) {
                throw new CommodityAddFailedException("Name image or detail is empty!");
            } else if (!commodityRepository.findByShopidAndName(shopid, name).isEmpty()) {
                throw new CommodityAddFailedException("commodity  exist!");
            } else {

                Commodity commodity = new Commodity();
                commodity.setCateid(cateid);
                commodity.setShopid(shopid);
                commodity.setName(name);

                commodity.setDetail(detail);
                commodity.setPrice(price);
                commodity.setAmount(amount);
                commodity.setStatus(status);
                commodityRepository.save(commodity);

                String imageName=image.getOriginalFilename();

                String suffixName=imageName.substring(imageName.lastIndexOf("."));
                //String filePath="C:/Users/LENOVO/Desktop/images/";
                String filePath="/home/ubuntu/mall/resource/images/";
                imageName= UUID.randomUUID()+suffixName;
                File dest =new File(filePath+imageName);
                System.out.println(dest.getPath().toString());
                if(!dest.getParentFile().exists()){
                    dest.getParentFile().mkdirs();
                }
                try {
                    image.transferTo(dest);
                }catch (IOException e){
                    //throw new CommodityAddFailedException("store image failed!");
                    return SimpleResponse.fail("store image failed!");
                }
                String mainimage="/images/"+imageName;

                commodity.setMainimage(mainimage);
                commodityRepository.save(commodity);

                return SimpleResponse.success();

            }
        } catch (CommodityException e) {
            return SimpleResponse.fail(e);
        }
    }

    @RequestMapping(value = "/delete_goods", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public SimpleResponse deleteGoods(HttpServletRequest request, @RequestParam("comid") Integer comid) {
        List<Commodity> commodityList;
        System.out.println(comid);
        try {
            if (comid == null) {
                throw new CommodityDeleteFailedException("Commodity ID cannot be null");
            } else if ((commodityList=commodityRepository.findByComid(comid)).isEmpty()) {
                throw new CommodityDeleteFailedException("Commodity not found with the given ID");
            } else {
                Commodity commodity=commodityList.get(0);
                //String filePath="C:/Users/LENOVO/Desktop";
                String filePath="~/mall";
                String imageName= commodity.getMainimage();
                File file =new File(filePath+imageName);
                if(file.exists()){
                    file.delete();
                }
                commodityRepository.deleteByComid(comid);
                return SimpleResponse.success();
            }
        } catch (CommodityException e) {
            return SimpleResponse.fail(e);
        }
    }

//    @RequestMapping(value = "/changeGoodsAmount", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseBody
//    public SimpleResponse changeGoodsAmount(HttpServletRequest request, @RequestParam("comid") Integer comid, @RequestParam("delta_amount") Double deltaAmount) {
//        List<Commodity> commodityList;
//        System.out.println(comid);
//        try {
//            if (comid == null || deltaAmount == null) {
//                throw new CommodityAlterFailedException("ID or changed amount is null");
//            } else if ((commodityList = commodityRepository.findByComid(comid)).isEmpty()) {
//                throw new CommodityAlterFailedException("Commodity with this id does not exist!");
//            } else if (commodityList.get(0).getAmount() + deltaAmount < 0) {
//                throw new CommodityAlterFailedException("Amount can't be changed to a negative value!");
//            } else {
//                Double newAmount = commodityList.get(0).getAmount() + deltaAmount;
//                commodityRepository.updateAmountByComid(newAmount, comid);
//                return SimpleResponse.success();
//            }
//        } catch (CommodityException e) {
//            return SimpleResponse.fail(e);
//        }
//    }
}
