package com.mng.controller.shop;

import com.mng.annotation.LoginRequired;
import com.mng.entity.redis.Shoppingcart;
import com.mng.repository.CommodityRepository;
import com.mng.repository.redis.ScRepository;
import com.mng.util.JsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Logger;

@LoginRequired
@Controller
public class ShoppingCartController {
    @Autowired
    ScRepository scRepository;
    @Autowired
    CommodityRepository commodityRepository;

    @GetMapping("/shopping_cart/change")
        //id: stuff id , op: operations
    void change(@RequestParam("id") String id, @RequestParam("op") Integer op,
                HttpServletRequest request, HttpServletResponse response) {
        JsonBuilder json = JsonBuilder.newObject();
        PrintWriter writer;
        com.mng.entity.redis.Shoppingcart shoppingcart;
        String phone = request.getSession().getAttribute("phone").toString();
        switch (op) {
            case 0:
                if ((shoppingcart = scRepository.findByIdAndPhone(id, phone)) == null) {
                    shoppingcart = new com.mng.entity.redis.Shoppingcart();
                    shoppingcart.setPhone(phone);
                    shoppingcart.setId(id);
                    shoppingcart.setName(request.getParameter("name"));
                    shoppingcart.setPrice(Double.parseDouble(request.getParameter("price")));
                    shoppingcart.setNum(1);
                    //TODO: Why's the double is the type of amount here?
                    int amount = (int) commodityRepository.findByComid(Integer.parseInt(id)).get(0).getAmount().doubleValue();
                    shoppingcart.setRemain(amount - 1);
                    scRepository.save(shoppingcart);
                } else {
                    shoppingcart.setNum(shoppingcart.getNum() + 1);
                    shoppingcart.setRemain(shoppingcart.getRemain() - 1);
                    scRepository.save(shoppingcart);
                }
                break;
            case 1:

                if ((shoppingcart = scRepository.findByIdAndPhone(id, phone)).getNum() >= 2) {
                    shoppingcart.setNum(shoppingcart.getNum() - 1);
                    shoppingcart.setRemain(shoppingcart.getRemain() + 1);
                    scRepository.save(shoppingcart);
                } else {
                    scRepository.delete(scRepository.findByIdAndPhone(id, phone));
                }
                break;
            case 2:
                if (scRepository.findByIdAndPhone(id, phone) != null)
                    scRepository.delete(scRepository.findByIdAndPhone(id, phone));
                break;
            default: {
                try {
                    writer = response.getWriter();
                    json.put("state", "Failed");
                    json.put("errorinfo", "argument error!");
                    writer.write(json.toString());
                    writer.flush();
                } catch (IOException e) {
                    //TODO: some optimizations
                    return;
                }
            }

        }
        try {
            writer = response.getWriter();
            json.put("state", "SUCCESS");
            writer.write(json.toString());
            writer.flush();
        } catch (IOException e) {
            //TODO: some optimizations
        }

    }

    @GetMapping("/shopping_cart/clear")
    void clear(HttpServletRequest request, HttpServletResponse response) {
        JsonBuilder json = JsonBuilder.newObject();
        String phone = request.getSession().getAttribute("phone").toString();
        PrintWriter writer;
        List<Shoppingcart> shoppingcarts =scRepository.findByPhone(phone);
        for(Shoppingcart item : shoppingcarts)
            scRepository.delete(item);
        try {
            writer = response.getWriter();
            json.put("state", "SUCCESS");
            writer.write(json.toString());
            writer.flush();
        } catch (IOException ignored) {
        }
    }

}
