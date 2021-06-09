package com.mng.controller.orders;

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
public class OrderGraphController extends OrderControllerBase {

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
                int xWeek = (int) (diff / (1000 * 60 * 60 * 24 * 7));
                if (xWeek <= 14 && xWeek >= 0) {
                    int k = names.indexOf(xWeek + 1);
                    values.set(k, values.get(k) + orders.getPayment());
                }
            }
            for (int i : names) {
                i--;
                if (i > 0) {
                    values.set(i, values.get(i) + values.get(i - 1));
                }
            }
            // by month
        } else {
            names = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
            values = Arrays.asList(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            for (Order orders : ordersList) {
                Date paymentTime = orders.getPaymenttime();
                Calendar timeCalendar = Calendar.getInstance();
                timeCalendar.setTime(paymentTime);
                if (timeCalendar.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    int xMonth = timeCalendar.get(Calendar.MONTH);
                    int k = names.indexOf(xMonth + 1);
                    values.set(k, values.get(k) + orders.getPayment());
                }
            }
        }
        return OrderGraphResponse.builder()
                .names(names)
                .values(values)
                .build();
    }

}
