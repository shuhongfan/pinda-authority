package cn.itcast.annotation;

import java.lang.annotation.*;

/**
 * 绑定当前登录用户
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
}