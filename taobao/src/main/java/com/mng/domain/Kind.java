package com.mng.domain;

import lombok.Data;

import java.util.List;

@Data
public class Kind {
    private String name;
    private List<Item> items;
}
