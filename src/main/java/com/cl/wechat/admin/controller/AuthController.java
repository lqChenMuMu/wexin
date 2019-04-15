package com.cl.wechat.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.AccessTokenThread;
import com.cl.wechat.admin.entity.Wuser;
import com.cl.wechat.admin.service.WuserService;
import com.cl.wechat.base.advanced.util.GetPersoninf;
import com.cl.wechat.base.basic.model.*;
import com.cl.wechat.base.wechatapi.util.WeixinUtil;
import com.cl.wechat.base.wechatapi.util.XmlMessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("wechat")
public class AuthController {

    private static final String tooken = "biantouwa"; //开发者自行定义Tooken
    private static final String appId = "wx04e95443a3ac67e3";
    private static final String appSecret = "7ff170c67c641cb40b93935480b8b1c8";
    private static final String real_url = "http://zzyyf.natapp1.cc";

    @Autowired
    private WuserService wuserService;

    @RequestMapping("/auth")
    public void auth(HttpServletResponse response) throws IOException {
        String path = real_url + "/wechat/invoke";
        path = URLEncoder.encode(path, "UTF-8");

        String url = "https://open.weixin.qq.com/connect" +
                "/oauth2/authorize?appid=" + appId +
                "&redirect_uri=" + path +
                "&response_type=code&" +
                "scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";

        response.sendRedirect(url);
    }

    @RequestMapping("invoke")
    public void oauthInvoke(String code, String state, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(code);
        System.out.println(state);
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid= " + appId +
                "&secret=" + appSecret +
                "&code=" + code +
                "&grant_type=authorization_code";
        String jsonStr = HttpUtil.post(url, "");
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        Object access_token = jsonObject.get("access_token");
        Object openid = jsonObject.get("openId");
        String getUserUrl = "https://api.weixin.qq.com/sns/userinfo?" +
                "access_token=" + access_token.toString() +
                "&openid=" + openid +
                "&lang=zh_CN";
        String userInfoStr = HttpUtil.get(getUserUrl);
        JSONObject userInfo = JSONUtil.parseObj(userInfoStr);
        HttpSession session = request.getSession();
        session.setAttribute("openid",userInfo.get("openid"));
        session.setAttribute("nickname",userInfo.get("nickname"));

        //添加微信用户信息
        Wuser wuser = new Wuser();
        wuser.setOpenid((String) userInfo.get("openid"));
        if(null == wuserService.getOne(new QueryWrapper<>(wuser))){
            wuser.setCountry((String) userInfo.get("country"));
            wuser.setCity((String) userInfo.get("city"));
            wuser.setProvince((String) userInfo.get("province"));
            wuser.setNickname((String) userInfo.get("nickname"));
            wuser.setSex((Integer) userInfo.get("sex"));
            wuser.setHeadimgurl((String) userInfo.get("headimgurl"));
            wuserService.save(wuser);
        }

        System.out.println(wuser.toString());
        if(state.equals("state")){
            response.sendRedirect("/classShow");
        }else{
            response.sendRedirect("/myAppointment");
        }
    }

    @GetMapping
    public String validate(@RequestParam("signature") String signature, @RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce, @RequestParam("echostr") String echostr) {
        String[] arr = {tooken, timestamp, nonce};
        //2.对数组进行排序
        Arrays.sort(arr);
        //3.生成字符串
        StringBuffer sb = new StringBuffer();
        for (String s : arr) {
            sb.append(s);
        }
        String validateStr = DigestUtil.sha1Hex(sb.toString());
        if (signature.equals(validateStr)) {
            return echostr;
        }
        return null;
    }

    @PostMapping
    public String receptionMsg(HttpServletRequest request) throws Exception {
        Map<String, String> requestParam = XmlMessUtil.parseXml(request);
        GetTextMessage getTextMessage = BeanUtil.mapToBean(requestParam, GetTextMessage.class, true);
        if (WeixinUtil.RECRIVE_EVENT.equals(requestParam.get("MsgType"))) {
            if (WeixinUtil.EVENT_SUBSCRIBE.equals(requestParam.get("Event"))) {
               /* WeiUser weiUser = GetPersoninf.getPersonalInf(AccessTokenThread.access_token.getAccesstoken(),getTextMessage.getFromUserName());
                weiUserService.save(weiUser);*/
                SendTextMessage sendTextMessage = new SendTextMessage();
                sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                sendTextMessage.setCreateTime(new Date().getTime());
                sendTextMessage.setContent("欢迎您！");
                return XmlMessUtil.textMessageToXml(sendTextMessage);
            } else if (WeixinUtil.EVENT_CLICK.equals(requestParam.get("Event"))) {
                if ("1".equals(requestParam.get("EventKey"))) {
                    SendTextMessage sendTextMessage = new SendTextMessage();
                    sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                    sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                    sendTextMessage.setCreateTime(new Date().getTime());
                    sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                    StringBuffer sb = new StringBuffer();
                    sb.append("尊敬的xxx您好").append("\n");
                    sb.append("您的预约号码为76号").append("\n");
                    sb.append("前面还有2位正在排队").append("\n");
                    sb.append("请耐心等待！");
                    sendTextMessage.setContent(sb.toString());
                    return XmlMessUtil.textMessageToXml(sendTextMessage);
                } else {
                    return null;
                }
            } else {
                SendNewsMessage sendNewsMessage = new SendNewsMessage();
                BeanUtil.copyProperties(getTextMessage, sendNewsMessage);
                sendNewsMessage.setFromUserName(getTextMessage.getToUserName());
                sendNewsMessage.setToUserName(getTextMessage.getFromUserName());
                sendNewsMessage.setMsgType(WeixinUtil.REQUEST_NEWS);
                sendNewsMessage.setArticleCount(1);
                List<SendArticle> sendArticles = new ArrayList<>();
                SendArticle sendArticle = new SendArticle();
                sendArticle.setTitle("这是个什么玩意儿？");
                sendArticle.setDescription("测试公众号玩玩，不行吃粑粑。呵呵哈哈嘿嘿伯建瓯阿瑟东啊是大峰哥");
                sendArticle.setPicUrl("https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=60b8e72a8901a18befeb144fae2e0761/8644ebf81a4c510f3295831c6e59252dd42aa567.jpg");
                sendArticle.setUrl("www.java1234.com");
                sendArticles.add(sendArticle);
                sendNewsMessage.setArticles(sendArticles);
                return XmlMessUtil.newsMessageToXml(sendNewsMessage);
            }
        } else {
            /*AccessToken at = AccessTokenThread.access_token;
            System.out.println(at.getAccesstoken());*/
            SendTextMessage sendTextMessage = new SendTextMessage();
            BeanUtil.copyProperties(getTextMessage, sendTextMessage);
            sendTextMessage.setFromUserName(getTextMessage.getToUserName());
            sendTextMessage.setToUserName(getTextMessage.getFromUserName());
            return XmlMessUtil.textMessageToXml(sendTextMessage);
        }
    }

}
