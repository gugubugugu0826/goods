package com.billy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.billy.mapper")
public class TextbookBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(TextbookBookApplication.class, args);
    }

}
