package com.cl.wechat.admin.config;

import com.cl.wechat.base.advanced.model.AccessToken;
import com.cl.wechat.base.wechatapi.util.CommonUtil;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenThread implements CommandLineRunner{
    // 第三方用户唯一凭证
    private static final String appId = "wx99dfc8d09722faa3";
    // 第三方用户唯一凭证密钥
    private static final String appSecret = "c25870e6dbb9ba89c85c2ef33cd56d8b";

    public static AccessToken access_token;

    @Override
    public void run(String... args) throws Exception {
        while(true){
            this.access_token = CommonUtil.getAccessToken(appId, appSecret);
            System.out.println("我是token: "+access_token.getAccesstoken());
            Thread.sleep(7000*1000);
        }
    }
}
