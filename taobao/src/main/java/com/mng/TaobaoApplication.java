package com.mng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class TaobaoApplication {
    public static void main(String[] args) {
        SpringApplication.run(TaobaoApplication.class, args);
    }

}
