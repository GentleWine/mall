package com.mng.controller.orders;


import com.alibaba.fastjson.JSONObject;
import com.mng.entity.Orders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class orderPlotController extends OrderBase {
    @RequestMapping(value = "/barVO", method = RequestMethod.POST)
    public JSONObject plotname(Model model, HttpServletRequest request, @RequestParam("sign") Integer sign, @RequestParam("shopid") Integer shopid) {

        List<Orders> ordersList = orderRepository.findByShopid(shopid);
        Date nowDate = new Date(System.currentTimeMillis());
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        JSONObject p = new JSONObject();
        if (sign == 0) {
            List<Integer> name = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
            List<Double> value = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            for (Orders orders : ordersList) {
                Date paymenttime = orders.getPaymenttime();
                long diff = nowDate.getTime() - paymenttime.getTime();
                Long x = diff / (1000 * 60 * 60 * 24 * 7);
                Integer xWeek = x.intValue();
                if (xWeek <= 14 && xWeek >= 0) {
                    Integer k = name.indexOf(xWeek + 1);
                    value.set(k, value.get(k) + orders.getPayment());
                }
            }
            p.put("names", name);
            p.put("values", value);

        } else {
            List<Integer> name = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            List<Double> value = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            for (Orders orders : ordersList) {
                Date paymenttime = orders.getPaymenttime();
                Calendar bef = Calendar.getInstance();
                bef.setTime(paymenttime);
                Integer xMonth = (now.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12 + (now.get(Calendar.MONTH) - bef.get(Calendar.MONTH));
                if (xMonth <= 11 && xMonth >= 0) {
                    Integer k = name.indexOf(xMonth + 1);
                    value.set(k, value.get(k) + orders.getPayment());
                }
            }
            p.put("names", name);
            p.put("values", value);
        }
        return p;
    }

}
