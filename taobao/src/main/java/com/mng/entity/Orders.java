package com.mng.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "orders")
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderid;

    private Integer userid;

    private Integer comid;

    private Integer number;

    private Double payment;

    private Integer paymenttype;

    private Integer status;

    private Date paymenttime;

    private Integer shopid;

    public Orders() {

    }
}