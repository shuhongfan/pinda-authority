package com.shf.controller;

import com.shf.log.MyLog;
import com.shf.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    private HelloService helloService;

    @GetMapping("/say")
    @MyLog(desc = "sayHello方法")
    public String sayHello() {
        return helloService.sayHello();
    }
}
