package com.mng.domain;

import lombok.Data;

import java.util.List;

@Data
public class Seller {
    private String name;
    private List<Kind>kinds;
}
