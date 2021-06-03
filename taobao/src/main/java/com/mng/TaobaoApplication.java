package com.mng;

import com.mng.repository.redis.ScRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaobaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaobaoApplication.class, args);
    }

}
