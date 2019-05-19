package com.cl.wechat.admin.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        System.out.println(request.getRequestURI());
        if(request.getRequestURI().indexOf("back")!=-1){
            Object userName = request.getSession().getAttribute("userName");
            if(userName == null){
                response.sendRedirect("/back/login.html");
                return false;
            }
        }else{
            //验证session是否存在
            Object openid = request.getSession().getAttribute("openid");
            if (openid == null) {
                if(request.getRequestURI().equals("/appointment/myAppointment") || request.getRequestURI().equals("/myAppointment")){
                    this.auth(response,"myState");
                }else if(request.getRequestURI().equals("/exAppointment")){
                    this.auth(response,"exState");
                }else{
                    this.auth(response,"state");
                }
                return true;
            }
        }
        return true;
    }

    public void auth(HttpServletResponse response,String state) throws IOException {
        String path = AccessTokenThread.real_url + "/wechat/invoke";
        //path = URLEncoder.encode(path, "UTF-8");
        String url = "https://open.weixin.qq.com/connect" +
                "/oauth2/authorize?appid=" + AccessTokenThread.appId +
                "&redirect_uri=" + path +
                "&response_type=code&" +
                "scope=snsapi_userinfo" +
                "&state="+ state +
                "#wechat_redirect";
        response.sendRedirect(url);
    }


}