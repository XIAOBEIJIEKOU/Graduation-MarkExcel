package com.example.mark;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
@MapperScan("com.example.mark.dao")
public class MarkApplication {

//    非Spring容器类调用spring管理的bean
    public static ConfigurableApplicationContext ac;


    public static void main(String[] args) throws IOException {
        MarkApplication.ac = SpringApplication.run(MarkApplication.class, args);
        HandleFourItems.handleStart();
    }

}
