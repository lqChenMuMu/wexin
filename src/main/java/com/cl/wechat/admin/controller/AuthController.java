package com.cl.wechat.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cl.wechat.admin.config.AccessTokenThread;
import com.cl.wechat.base.advanced.util.GetPersoninf;
import com.cl.wechat.base.basic.model.*;
import com.cl.wechat.base.wechatapi.util.WeixinUtil;
import com.cl.wechat.base.wechatapi.util.XmlMessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("wechat")
public class AuthController {


    private static final String tooken = "biantouwa"; //开发者自行定义Tooken
    private static final String appId = "wx04e95443a3ac67e3";
    private static final String appSecret = "7ff170c67c641cb40b93935480b8b1c8";

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
        if(signature.equals(validateStr)){
            return echostr;
        }
        return null;
    }

    @PostMapping
    public String receptionMsg(HttpServletRequest request) throws Exception {
        Map<String,String> requestParam = XmlMessUtil.parseXml(request);
        GetTextMessage getTextMessage = BeanUtil.mapToBean(requestParam, GetTextMessage.class,true);
        if(WeixinUtil.RECRIVE_EVENT.equals(requestParam.get("MsgType"))){
            if(WeixinUtil.EVENT_SUBSCRIBE.equals(requestParam.get("Event"))){
               /* WeiUser weiUser = GetPersoninf.getPersonalInf(AccessTokenThread.access_token.getAccesstoken(),getTextMessage.getFromUserName());
                weiUserService.save(weiUser);*/
                SendTextMessage sendTextMessage = new SendTextMessage();
                sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                sendTextMessage.setCreateTime(new Date().getTime());
                sendTextMessage.setContent("欢迎您！");
                return XmlMessUtil.textMessageToXml(sendTextMessage);
            }else if(WeixinUtil.EVENT_CLICK.equals(requestParam.get("Event"))){
                if("1".equals(requestParam.get("EventKey"))){
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
                }else{
                    return null;
                }
            }
            else{
                SendNewsMessage sendNewsMessage = new SendNewsMessage();
                BeanUtil.copyProperties(getTextMessage,sendNewsMessage);
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
        }else{
            /*AccessToken at = AccessTokenThread.access_token;
            System.out.println(at.getAccesstoken());*/
            SendTextMessage sendTextMessage = new SendTextMessage();
            BeanUtil.copyProperties(getTextMessage,sendTextMessage);
            sendTextMessage.setFromUserName(getTextMessage.getToUserName());
            sendTextMessage.setToUserName(getTextMessage.getFromUserName());
            return XmlMessUtil.textMessageToXml(sendTextMessage);
        }
    }

}
