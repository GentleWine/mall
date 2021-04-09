package com.mng.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.DynamicInsert;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "shop")
public class Shop  implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer shopid;

	private Integer ownerid;

	private String shopname;

}
