package com.itheima.entity;

import lombok.Data;
import java.util.Date;

@Data
public class UserEntity {
    private String id;
    private String name;
    private int age;
    private String address;
    private Date birthday;
}
