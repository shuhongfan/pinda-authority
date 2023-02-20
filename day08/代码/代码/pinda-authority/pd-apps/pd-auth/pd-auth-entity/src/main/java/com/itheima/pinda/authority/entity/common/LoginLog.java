package com.itheima.pinda.authority.entity.common;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.itheima.pinda.base.entity.SuperEntity;

import com.itheima.pinda.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 登录日志
 * </p>
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pd_common_login_log")
@ApiModel(value = "LoginLog", description = "登录日志")
public class LoginLog extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 操作IP
     */
    @ApiModelProperty(value = "操作IP")
    @Length(max = 50, message = "操作IP长度不能超过50")
    @TableField(value = "request_ip", condition = LIKE)
    private String requestIp;

    /**
     * 登录人ID
     */
    @ApiModelProperty(value = "登录人ID")
    @TableField("user_id")
    private Long userId;

    /**
     * 登录人姓名
     */
    @ApiModelProperty(value = "登录人姓名")
    @Length(max = 50, message = "登录人姓名长度不能超过50")
    @TableField(value = "user_name", condition = LIKE)
    private String userName;

    /**
     * 登录人账号
     */
    @ApiModelProperty(value = "登录人账号")
    @Length(max = 30, message = "登录人账号长度不能超过30")
    @TableField(value = "account", condition = LIKE)
    private String account;

    /**
     * 登录描述
     */
    @ApiModelProperty(value = "登录描述")
    @Length(max = 255, message = "登录描述长度不能超过255")
    @TableField(value = "description", condition = LIKE)
    private String description;

    /**
     * 登录时间
     */
    @ApiModelProperty(value = "登录时间")
    @TableField("login_date")
    private LocalDate loginDate;

    /**
     * 浏览器请求头
     */
    @ApiModelProperty(value = "浏览器请求头")
    @Length(max = 500, message = "浏览器请求头长度不能超过500")
    @TableField(value = "ua", condition = LIKE)
    private String ua;

    /**
     * 浏览器名称
     */
    @ApiModelProperty(value = "浏览器名称")
    @Length(max = 255, message = "浏览器名称长度不能超过255")
    @TableField(value = "browser", condition = LIKE)
    private String browser;

    /**
     * 浏览器版本
     */
    @ApiModelProperty(value = "浏览器版本")
    @Length(max = 255, message = "浏览器版本长度不能超过255")
    @TableField(value = "browser_version", condition = LIKE)
    private String browserVersion;

    /**
     * 操作系统
     */
    @ApiModelProperty(value = "操作系统")
    @Length(max = 255, message = "操作系统长度不能超过255")
    @TableField(value = "operating_system", condition = LIKE)
    private String operatingSystem;

    /**
     * 登录地点
     */
    @ApiModelProperty(value = "登录地点")
    @Length(max = 50, message = "登录地点长度不能超过50")
    @TableField(value = "location", condition = LIKE)
    private String location;


    @Builder
    public LoginLog(Long id, LocalDateTime createTime, Long createUser,
                    String requestIp, Long userId, String userName, String account, String description,
                    LocalDate loginDate, String ua, String browser, String browserVersion, String operatingSystem, String location) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.requestIp = requestIp;
        this.userId = userId;
        this.userName = userName;
        this.account = account;
        this.description = description;
        this.loginDate = loginDate;
        this.ua = ua;
        this.browser = browser;
        this.browserVersion = browserVersion;
        this.operatingSystem = operatingSystem;
        this.location = location;
    }

}
