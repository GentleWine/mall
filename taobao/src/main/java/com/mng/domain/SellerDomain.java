package com.mng.domain;

import lombok.Data;

import java.util.List;

@Data
public class SellerDomain {
    private String name;
    private List<KindDomain> kinds;
    private String address;
    private Integer shopid;
}
