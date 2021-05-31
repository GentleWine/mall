package com.mng.domain;

import lombok.Data;

@Data
public class ItemDomain {
    private String item_title;
    private Double price;
    private String seller_info;
    private Integer id;
    private Double amount;
    private String  imgUrl;
}
