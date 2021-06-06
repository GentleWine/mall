package com.mng.repository;

import com.mng.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //根据账号密码查找用户
    List<User> findByPhoneAndPassword(String phone, String password);

    List<User> findByPhone(String phone);

    List<User> findByUsername(String username);
}
