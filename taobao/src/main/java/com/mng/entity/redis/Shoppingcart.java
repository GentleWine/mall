package com.mng.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Data
//expired time is set to 1 day
@RedisHash(value = "shoppingcart", timeToLive = 24 * 3600)
public class Shoppingcart {
    @Id
    Integer index;
    @Indexed
    String phone;
    @Indexed
    String id;
    String name;
    Double price;
    Integer num;
    Integer remain;
}
