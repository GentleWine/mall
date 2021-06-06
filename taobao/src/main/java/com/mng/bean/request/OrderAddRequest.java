package com.mng.bean.request;

import lombok.Data;

@Data
public class OrderAddRequest {
    public static final String timeFormat = "yyyy-MM-dd HH:mm:ss";
    private Integer userId;
    private Integer commodityId;
    private Integer number;
    private Double payment;
    private Integer paymentType;
    private Integer status;
    private String paymentTime;
    private Integer shopId;
}
