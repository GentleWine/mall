package com.mng.controller.orders;

import com.alibaba.fastjson.JSONObject;
import com.mng.bean.response.OrderGraphResponse;
import com.mng.entity.Order;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
public class OrderPlotController extends OrderControllerBase {

    @PostMapping(
            value = "/barVO",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public OrderGraphResponse generateOrderGraph(
            // 0 = 按周, 1 = 按月
            @RequestParam("sign") Integer sign,
            @RequestParam("shopid") Integer shopid
    ) {

        List<Order> ordersList = orderRepository.findByShopid(shopid);
        Date nowDate = new Date();
        Calendar now = Calendar.getInstance();
        now.setTime(nowDate);
        JSONObject p = new JSONObject();
        // Names of the X-axis (maybe?)
        List<Integer> names;
        List<Double> values;
        // by week
        if (sign == 0) {
            names = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);
            values = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            for (Order orders : ordersList) {
                Date paymentTime = orders.getPaymenttime();
                long diff = nowDate.getTime() - paymentTime.getTime();
                long x = diff / (1000 * 60 * 60 * 24 * 7);
                int xWeek = (int) x;
                if (xWeek <= 14 && xWeek >= 0) {
                    int k = names.indexOf(xWeek + 1);
                    values.set(k, values.get(k) + orders.getPayment());
                }
            }
            // by month
        } else {
            names = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            values = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            for (Order orders : ordersList) {
                Date paymenttime = orders.getPaymenttime();
                Calendar bef = Calendar.getInstance();
                bef.setTime(paymenttime);
                if(bef.get(Calendar.YEAR)==now.get(Calendar.YEAR)){
                    Integer xMonth=bef.get(Calendar.MONTH);
                    Integer k=name.indexOf(xMonth+1);
                    value.set(k, value.get(k) + orders.getPayment());
                }
            }
        }
        return OrderGraphResponse.builder()
                .names(names)
                .values(values)
                .build();
    }

}
