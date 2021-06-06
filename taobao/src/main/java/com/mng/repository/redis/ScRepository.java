package com.mng.repository.redis;

import com.mng.entity.redis.Shoppingcart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ScRepository extends CrudRepository<Shoppingcart, Integer> {
    Shoppingcart findByIdAndPhone(String id, String phone);

    List<Shoppingcart> findByPhone(String phone);

    void deleteByIdAndPhone(String id, String phone);

    void deleteByPhone(String phone);
}
