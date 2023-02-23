package com.shf.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "shf")
public class HelloProperties {
    private String name;
    private String address;
}
