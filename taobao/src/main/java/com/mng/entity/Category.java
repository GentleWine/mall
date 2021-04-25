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
@Table(name = "category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cateid;

    private Integer parentid;

    private String name;

    private String status;

    private Integer sortorder;

}
