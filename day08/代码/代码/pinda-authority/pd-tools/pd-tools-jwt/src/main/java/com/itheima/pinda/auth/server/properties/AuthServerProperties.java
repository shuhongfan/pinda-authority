package com.itheima.pinda.auth.server.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.itheima.pinda.auth.server.properties.AuthServerProperties.PREFIX;

/**
 * 认证服务端 属性
 *
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = PREFIX)
public class AuthServerProperties {
    public static final String PREFIX = "authentication";

    private TokenInfo user;

    @Data
    public static class TokenInfo {
        /**
         * 过期时间
         */
        private Integer expire = 7200;
        /**
         * 加密 服务使用
         */
        private String priKey;
        /**
         * 解密
         */
        private String pubKey;
    }

}
