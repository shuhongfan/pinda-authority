package cn.itcast.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.*;

@Data
public class User {
    @NotNull(message = "用户id不能为空")
    private Integer id;

    @NotEmpty(message = "用户名不能为空")
    @Length(message = "用户名长度最大为40",max = 40)
    private String username;

    @Min(message = "年龄最小为18岁",value = 18)
    @Max(message = "年龄最大为80岁",value = 80)
    private int age;

    @Email(message = "请输入正确的邮箱")
    private String email;
}