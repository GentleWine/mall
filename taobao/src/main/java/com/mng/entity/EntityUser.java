package com.mng.entity;

import com.mng.data.IUser;
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
public class EntityUser implements Serializable, IUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;
    private String username;
    private String password;
    private Integer usertype;
    private String phone;
    private String mail;

    @Override
    public int getId() {
        return getUserid();
    }

    @Override
    public UserType getUserType() {
        return UserType.getFromId(usertype);
    }
}
