package com.perfectcodes.component;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "gj.prop")
public class PropConfig {

    private String appid;
    private String secret;
    private String affixDir;
    private String webDomain;
    private static String accessToken = null;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getAffixDir() {
        return affixDir;
    }

    public void setAffixDir(String affixDir) {
        this.affixDir = affixDir;
    }

    public String getWebDomain() {
        return webDomain;
    }

    public void setWebDomain(String webDomain) {
        this.webDomain = webDomain;
    }

    public static String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        PropConfig.accessToken = accessToken;
    }
}
