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
@Table(name = "category")
public class Category  implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer cateid;

	private Integer parentid;

	private String name;

	private String status;

	private Integer sortorder;

}
