package com.itheima;

import com.itheima.pinda.auth.server.EnableAuthServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAuthServer
public class MyJwtApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyJwtApplication.class,args);
    }
}
