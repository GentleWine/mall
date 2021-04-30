package com.mng.bean;


import lombok.Data;

@Data
public class AddGoodsBody {
    public String name;
    public double price;
    public int kind;
    public String description;
}
