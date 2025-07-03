package com.lxx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lxx.mapper")
public class LxxChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxxChatApplication.class, args);
    }

}
