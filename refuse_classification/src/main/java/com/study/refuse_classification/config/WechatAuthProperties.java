package com.study.refuse_classification.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WechatAuthProperties implements InitializingBean {
    @Value("${auth.wechat.sessionHost}")
    private String sessionHost;

    @Value("${auth.wechat.appId}")
    private String appId;

    @Value("${auth.wechat.secret}")
    private String secret;

    @Value("${auth.wechat.grantType}")
    private String grantType;

    //定义公开静态常量
    public static String SESSION_Host;
    public static String APP_ID;
    public static String APP_SECRET;
    public static String GRANT_TYPE;

    @Override
    public void afterPropertiesSet() throws Exception {
        SESSION_Host = sessionHost;
        APP_ID = appId;
        APP_SECRET = secret;
        GRANT_TYPE = grantType;
    }
}
