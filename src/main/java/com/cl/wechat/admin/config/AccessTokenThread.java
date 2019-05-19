package com.cl.wechat.admin.config;

import com.cl.wechat.base.advanced.model.AccessToken;
import com.cl.wechat.base.wechatapi.util.CommonUtil;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenThread implements CommandLineRunner{
    // 第三方用户唯一凭证
    public static final String appId = "wx04e95443a3ac67e3";
    //public static final String appId = "wx99dfc8d09722faa3";
    //public static final String appId = "wxeefa15131f319997";
    // 第三方用户唯一凭证密钥
    public static final String appSecret = "7ff170c67c641cb40b93935480b8b1c8";
    //public static final String appSecret = "c25870e6dbb9ba89c85c2ef33cd56d8b";
    //public static final String appSecret = "2b058802c75f691cde05089d8b78b067";

    public static final String real_url = "http://zzyyf.natapp1.cc";

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
