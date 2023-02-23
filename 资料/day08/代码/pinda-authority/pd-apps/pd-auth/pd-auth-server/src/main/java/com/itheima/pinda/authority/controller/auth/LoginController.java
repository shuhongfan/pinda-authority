package com.itheima.pinda.authority.controller.auth;

import com.itheima.pinda.authority.biz.service.auth.ValidateCodeService;
import com.itheima.pinda.authority.biz.service.auth.impl.AuthManager;
import com.itheima.pinda.authority.dto.auth.LoginDTO;
import com.itheima.pinda.authority.dto.auth.LoginParamDTO;
import com.itheima.pinda.base.BaseController;
import com.itheima.pinda.base.R;
import com.itheima.pinda.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录（认证）控制器
 */
@RestController
@RequestMapping("/anno")
@Api(tags = "登录控制器",value = "LoginController")
public class LoginController extends BaseController{
    @Autowired
    private ValidateCodeService validateCodeService;
    @Autowired
    private AuthManager authManager;

    //为前端系统生成验证码
    @GetMapping(value = "/captcha",produces = "image/png")
    @ApiOperation(notes = "验证码",value = "验证码")
    @SysLog("生成验证码")
    public void captcha(@RequestParam(value = "key") String key, HttpServletResponse response) throws IOException{
        validateCodeService.create(key,response);
    }

    //登录认证
    @PostMapping("/login")
    @ApiOperation(notes = "登录",value = "登录")
    @SysLog("登录")
    public R<LoginDTO> login(@Validated @RequestBody LoginParamDTO loginParamDTO){
        //校验验证码是否正确
        boolean check = validateCodeService.check(loginParamDTO.getKey(), loginParamDTO.getCode());
        if(check){
            //验证码校验通过，执行具体的登录认证逻辑
            R<LoginDTO> r = authManager.login(loginParamDTO.getAccount(),loginParamDTO.getPassword());
            return r;
        }
        //验证码校验不通过，直接返回
        return this.success(null);
    }

    //校验验证码
    @PostMapping("/check")
    @ApiOperation(notes = "校验验证码",value = "校验验证码")
    public boolean check(@RequestBody LoginParamDTO loginParamDTO){
        //校验验证码是否正确
        return validateCodeService.check(loginParamDTO.getKey(),loginParamDTO.getCode());
    }
}
