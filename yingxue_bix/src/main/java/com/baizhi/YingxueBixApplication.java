package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@tk.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@org.mybatis.spring.annotation.MapperScan("com.baizhi.dao")
@SpringBootApplication
public class YingxueBixApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxueBixApplication.class, args);
    }

}
