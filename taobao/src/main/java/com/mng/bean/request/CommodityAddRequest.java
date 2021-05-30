package com.mng.bean.request;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CommodityAddRequest {
    public String name;
    public Double price;
    public Integer kind;
    public String description;
    public MultipartFile image;
}
