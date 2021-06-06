package com.mng.bean.response;

import com.mng.entity.Order;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderListResponse implements ListResponse<Order> {
    private Integer code;
    private String msg;
    private Long count;
    private List<Order> data;
}
