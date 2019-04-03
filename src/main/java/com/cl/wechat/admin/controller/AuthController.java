package com.cl.wechat.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cl.wechat.admin.config.AccessTokenThread;
import com.cl.wechat.base.advanced.model.AccessToken;
import com.cl.wechat.base.basic.model.*;
import com.cl.wechat.base.wechatapi.util.WeixinUtil;
import com.cl.wechat.base.wechatapi.util.XmlMessUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            SendNewsMessage sendNewsMessage = new SendNewsMessage();
            BeanUtil.copyProperties(getTextMessage,sendNewsMessage);
            sendNewsMessage.setFromUserName(getTextMessage.getToUserName());
            sendNewsMessage.setToUserName(getTextMessage.getFromUserName());
            sendNewsMessage.setArticleCount(1);
            List<SendArticle> sendArticles = new ArrayList<>();
            SendArticle sendArticle = new SendArticle();
            sendArticle.setTitle("这是个什么玩意儿？");
            sendArticle.setDescription("测试公众号玩玩，不行吃粑粑。呵呵哈哈嘿嘿伯建瓯阿瑟东啊是大峰哥");
            sendArticle.setPicUrl("http://image.baidu.com/search/detail?ct=503316480&z=undefined&tn=baiduimagedetail&ipn=d&word=%E5%A5%BD%E7%9C%8B%E7%9A%84%E5%9B%BE%E7%89%87&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=undefined&latest=undefined&copyright=undefined&cs=2373560198,301921565&os=1061699437,881936489&simid=4244879780,566305109&pn=0&rn=1&di=86588059470&ln=1659&fr=&fmq=1554192362649_R&fm=rs12&ic=undefined&s=undefined&se=&sme=&tab=0&width=undefined&height=undefined&face=undefined&is=0,0&istype=0&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&oriquery=%E5%9B%BE%E7%89%87&objurl=http%3A%2F%2Fimg17.3lian.com%2Fd%2Ffile%2F201701%2F23%2Fcbf3ce5314f244113bd011f1a6d40bbd.jpg&rpstart=0&rpnum=0&adpicid=0&force=undefined");
            sendArticle.setUrl("www.java1234.com");
            sendArticles.add(sendArticle);
            sendNewsMessage.setArticles(sendArticles);
            return XmlMessUtil.newsMessageToXml(sendNewsMessage);
        }else{
            /*AccessToken at = AccessTokenThread.access_token;
            System.out.println(at.getAccesstoken());*/
            SendTextMessage sendTextMessage = new SendTextMessage();
            BeanUtil.copyProperties(getTextMessage,sendTextMessage);
            sendTextMessage.setFromUserName(getTextMessage.getToUserName());
            sendTextMessage.setToUserName(getTextMessage.getFromUserName());
            sendTextMessage = new SendTextMessage();
            return XmlMessUtil.textMessageToXml(sendTextMessage);
        }


    }

}
