package com.mng.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "commodity")
public class Commodity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer comid;

    private Integer cateid;

    private Integer shopid;

    private String name;

    private String mainimage;

    private String detail;

    private Double price;

    private Double amount;

    private String status;

}
