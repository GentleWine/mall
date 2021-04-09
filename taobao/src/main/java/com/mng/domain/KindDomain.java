package com.mng.domain;

import lombok.Data;

import java.util.List;

@Data
public class KindDomain {
    private String name;
    private List<ItemDomain> items;
}
