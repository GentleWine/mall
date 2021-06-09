package com.mng.entity;

import com.mng.data.UserType;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@DynamicInsert
@DynamicUpdate
@Data
@Entity
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    private String username;

    private String password;

    private String usertype;

    private String phone;

    private String mail;

    private Double spentmoney;

    public User() {

    }

    public UserType getEnumUserType() throws IllegalArgumentException {
        return UserType.getFromId(Integer.parseInt(usertype));
    }

    public UserType getEnumUserTypeOrNull() {
        return UserType.getFromIdOrNull(Integer.parseInt(usertype));
    }
}
