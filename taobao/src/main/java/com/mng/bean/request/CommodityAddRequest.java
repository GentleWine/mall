package com.mng.bean.request;


import lombok.Data;

@Data
public class CommodityAddRequest {
    public String name;
    public Double price;
    public Integer kind;
    public String description;
}
