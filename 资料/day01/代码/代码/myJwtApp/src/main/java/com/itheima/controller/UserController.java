package com.itheima.controller;

import com.itheima.pinda.auth.server.utils.JwtTokenServerUtils;
import com.itheima.pinda.auth.utils.JwtUserInfo;
import com.itheima.pinda.auth.utils.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtTokenServerUtils jwtTokenServerUtils;

    //用户登录功能，如果登录成功则签发jwt令牌给客户端
    @GetMapping("/login")
    public Token login(){
        String userName = "admin";
        String password = "admin123";
        //查询数据库进行用户名密码校验...

        //如果校验通过，则为客户端生成jwt令牌
        JwtUserInfo jwtUserInfo = new JwtUserInfo();
        jwtUserInfo.setName(userName);
        jwtUserInfo.setOrgId(10L);
        jwtUserInfo.setUserId(1L);
        jwtUserInfo.setAccount(userName);
        jwtUserInfo.setStationId(20L);
        Token token = jwtTokenServerUtils.generateUserToken(jwtUserInfo, null);

        //实际应该是在过滤器中进行jwt令牌的解析
        JwtUserInfo userInfo = jwtTokenServerUtils.getUserInfo(token.getToken());
        System.out.println(userInfo);
        return token;
    }
}