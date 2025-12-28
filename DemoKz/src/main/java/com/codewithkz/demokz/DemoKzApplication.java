package com.codewithkz.demokz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;

@SpringBootApplication
public class DemoKzApplication {

    public static void main(String[] args) {
        System.out.println(ZoneId.systemDefault());

        SpringApplication.run(DemoKzApplication.class, args);
    }

}
