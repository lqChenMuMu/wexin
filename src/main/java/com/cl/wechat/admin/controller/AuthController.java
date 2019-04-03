package com.cl.wechat.admin.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cl.wechat.admin.config.AccessTokenThread;
import com.cl.wechat.base.advanced.model.AccessToken;
import com.cl.wechat.base.basic.model.*;
import com.cl.wechat.base.wechatapi.util.WeixinUtil;
import com.cl.wechat.base.wechatapi.util.XmlMessUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
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
            SendNewsMessage sendNewsMessage = new SendNewsMessage();
            sendNewsMessage.setMsgType(WeixinUtil.REQUEST_NEWS);
            sendNewsMessage.setCreateTime(new Date().getTime());
            sendNewsMessage.setFromUserName(getTextMessage.getToUserName());
            sendNewsMessage.setToUserName(getTextMessage.getFromUserName());
            sendNewsMessage.setArticleCount(1);
            List<SendArticle> sendArticles = new ArrayList<>();
            SendArticle sendArticle = new SendArticle();
            sendArticle.setTitle("这是个什么玩意儿？");
            sendArticle.setDescription("测试公众号玩玩，不行吃粑粑。呵呵哈哈嘿嘿伯建瓯阿瑟东啊是大峰哥");
            sendArticle.setPicUrl("https://ss3.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/image/h%3D300/sign=7dac85b2825494ee982209191df4e0e1/c2cec3fdfc03924558fae5028994a4c27d1e256b.jpg");
            sendArticle.setUrl("www.java1234.com");
            sendArticles.add(sendArticle);
            sendNewsMessage.setArticles(sendArticles);
            return XmlMessUtil.newsMessageToXml(sendNewsMessage);
        }else{
            SendTextMessage sendTextMessage = new SendTextMessage();
            BeanUtil.copyProperties(getTextMessage,sendTextMessage);
            sendTextMessage.setFromUserName(getTextMessage.getToUserName());
            sendTextMessage.setToUserName(getTextMessage.getFromUserName());
            if(StrUtil.isBlank(sendTextMessage.getContent())){
                sendTextMessage.setContent("默认的返回！！！");
            }
            return XmlMessUtil.textMessageToXml(sendTextMessage);
        }


    }

}
