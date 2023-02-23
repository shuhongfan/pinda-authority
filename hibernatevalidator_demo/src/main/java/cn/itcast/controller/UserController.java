package cn.itcast.controller;

import cn.itcast.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
@RestController
@RequestMapping("/user")
@Validated //开启hibernate-validator校验
public class UserController {
    //简单类型的校验
    @RequestMapping("/delete")
    public String delete(@NotNull(message = "用户id不能为空") Integer id){
        System.out.println("delete...");
        return "delete success";
    }

    //对象属性校验
    @RequestMapping("/save")
    public String save(@Validated User user){
        System.out.println("save...");
        return "save success";
    }
}
