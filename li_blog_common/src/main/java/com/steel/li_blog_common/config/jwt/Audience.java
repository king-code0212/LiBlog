package com.steel.li_blog_common.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Auther: steel
 * @Date: 2020/10/18 14:30
 * @Description: JWT相关配置
 */
@ConfigurationProperties(prefix = "audience")
@Component
public class Audience {

    private String clientId;

    private String base64Secret;

    private String name;

    private int expiresSecond;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBase64Secret() {
        return base64Secret;
    }

    public void setBase64Secret(String base64Secret) {
        this.base64Secret = base64Secret;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExpiresSecond() {
        return expiresSecond;
    }

    public void setExpiresSecond(int expiresSecond) {
        this.expiresSecond = expiresSecond;
    }

    @Override
    public String toString() {
        return "Audience [clientId=" + clientId + ", base64Secret=" + base64Secret + ", name=" + name
                + ", expiresSecond=" + expiresSecond + "]";
    }

}
