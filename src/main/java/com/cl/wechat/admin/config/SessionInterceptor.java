package com.cl.wechat.admin.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class SessionInterceptor implements HandlerInterceptor {


    private static final String appId = "wx04e95443a3ac67e3";
    private static final String appSecret = "7ff170c67c641cb40b93935480b8b1c8";
    private static final String real_url = "http://zzyyf.natapp1.cc";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        /*if (request.getRequestURI().equals("/login")) {
            return true;
        }*/

        System.out.println(request.getRequestURI());
        //验证session是否存在
        Object openid = request.getSession().getAttribute("openid");
        if (openid == null) {
            if(request.getRequestURI().equals("/appointment/myAppointment")){
                this.auth(response,"myState");
            }else{
                this.auth(response,"state");
            }
            return false;
        }
        return true;
    }

    public void auth(HttpServletResponse response,String state) throws IOException {
        String path = real_url + "/wechat/invoke";
        //path = URLEncoder.encode(path, "UTF-8");

        String url = "https://open.weixin.qq.com/connect" +
                "/oauth2/authorize?appid=" + appId +
                "&redirect_uri=" + path +
                "&response_type=code&" +
                "scope=snsapi_userinfo" +
                "&state="+ state +
                "#wechat_redirect";

        response.sendRedirect(url);
    }
}