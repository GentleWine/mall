package com.mng.Repository;

import com.mng.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface UserRepository extends JpaRepository<User,Integer> {
    //根据账号密码查找用户
    List<User>findByAccountAndPassword(String account, String password);
    List<User>findByAccount(String account);
}
