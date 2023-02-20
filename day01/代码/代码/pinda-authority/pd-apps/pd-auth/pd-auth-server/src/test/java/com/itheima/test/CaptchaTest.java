package com.itheima.test;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.base.Captcha;
import java.io.File;
import java.io.FileOutputStream;

public class CaptchaTest {
    public static void main(String[] args) throws Exception{
        //中文验证码
        Captcha captcha = new ChineseCaptcha(150,60);
        //获取本次生成的验证码
        String text = captcha.text();
        System.out.println(text);
        captcha.out(new FileOutputStream(new File(("d:\\test.png"))));

        //算术验证码
        Captcha captcha1 = new ArithmeticCaptcha();
        //captcha1.setCharType();
        //获取本次生成的验证码
        String text1 = captcha1.text();
        System.out.println(text1);
        captcha1.out(new FileOutputStream(new File(("d:\\test1.png"))));
    }
}
