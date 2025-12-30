package com.codewithkz.demokz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.ZoneId;

@SpringBootApplication
@EnableScheduling
public class DemoKzApplication {

    public static void main(String[] args) {
        System.out.println(ZoneId.systemDefault());

        SpringApplication.run(DemoKzApplication.class, args);
    }

}
