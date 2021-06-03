package com.mng.entity.redis;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;

@Data
@RedisHash("shopingcart")
public class Shopingcart {
    @Id int id;
    @Indexed String phone;
    String test_content;
}
