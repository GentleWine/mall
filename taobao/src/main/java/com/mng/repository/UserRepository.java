package com.mng.repository;

import com.mng.entity.EntityUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<EntityUser, Integer> {
    //根据账号密码查找用户
    List<EntityUser> findByPhoneAndPassword(String phone, String password);

    List<EntityUser> findByPhone(String phone);
}
