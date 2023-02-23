package com.itheima.pinda.database.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.itheima.pinda.database.properties.DatabaseProperties.PREFIX;
/**
 * 客户端认证配置
 */
@ConfigurationProperties(prefix = PREFIX)
@Data
@NoArgsConstructor
public class DatabaseProperties {
    public static final String PREFIX = "pinda.database";
    /**
     * 攻击 SQL 阻断解析器
     */
    public Boolean isBlockAttack = false;
    /**
     * 是否启用数据权限
     */
    //private Boolean isDataScope = true;
    /**
     * 事务超时时间
     */
    private int txTimeout = 60 * 60;
    /**
     * 统一管理事务的方法名
     */
    private List<String> transactionAttributeList = new ArrayList<>(Arrays.asList("add*", "save*", "insert*",
            "create*", "update*", "edit*", "upload*", "delete*", "remove*",
            "clean*", "recycle*", "batch*", "mark*", "disable*", "enable*", "handle*", "syn*",
            "reg*", "gen*", "*Tx"
    ));
    /**
     * 事务扫描基础包
     */
    private String transactionScanPackage = "com.itheima.pinda";
}
