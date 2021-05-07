package com.mng.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
// TODO：预留类
public class NewGoods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String name;

    public String price;

    public String kind;

    public String description;
}
