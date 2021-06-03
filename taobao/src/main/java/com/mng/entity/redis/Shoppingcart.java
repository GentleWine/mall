package com.mng.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Data
@RedisHash("shopingcart")
public class Shoppingcart {
    @Id Integer index;
    String phone;
    String id;
    String name;
    Integer price;
    Integer num;
    Integer remain;
}
