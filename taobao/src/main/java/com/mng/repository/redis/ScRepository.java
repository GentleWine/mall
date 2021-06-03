package com.mng.repository.redis;

import com.mng.entity.redis.Shopingcart;
import org.springframework.data.repository.CrudRepository;

public interface ScRepository extends CrudRepository<Shopingcart,Integer> {
}
