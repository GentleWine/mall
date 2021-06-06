package com.mng.bean.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OrderEditRequest extends OrderAddRequest {
    private Integer orderId;
}
