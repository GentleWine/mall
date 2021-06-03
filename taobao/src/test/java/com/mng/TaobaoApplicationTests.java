package com.mng;


import com.mng.entity.redis.Shopingcart;
import com.mng.repository.CategoryRepository;
import com.mng.repository.ShopRepository;
import com.mng.repository.redis.ScRepository;
import com.mng.util.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TaobaoApplicationTests {
    @Autowired
    ScRepository scRepository;
    @Test
    void contextLoads() {
        Shopingcart shopingcart = new Shopingcart();
        shopingcart.setId(1);
        shopingcart.setPhone("222");
        shopingcart.setTest_content("hello");
        scRepository.save(shopingcart);
        Log.i(scRepository.findById(1).get().getTest_content());
    }

}
