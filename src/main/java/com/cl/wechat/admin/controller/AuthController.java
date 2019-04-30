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
import com.cl.wechat.base.advanced.model.AccessToken;
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

    private static final String real_url = "http://zzyyf.natapp1.cc";

    @Autowired
    private WuserService wuserService;

    @RequestMapping("/auth")
    public void auth(HttpServletResponse response) throws IOException {
        String path = real_url + "/wechat/invoke";
        path = URLEncoder.encode(path, "UTF-8");

        String url = "https://open.weixin.qq.com/connect" +
                "/oauth2/authorize?appid=" + AccessTokenThread.appId +
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
                "appid= " + AccessTokenThread.appId +
                "&secret=" + AccessTokenThread.appSecret +
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

    /*@PostMapping
    public String receptionMsg(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> requestParam = XmlMessUtil.parseXml(request);
        GetTextMessage getTextMessage = BeanUtil.mapToBean(requestParam, GetTextMessage.class, true);
        if (WeixinUtil.RECRIVE_EVENT.equals(requestParam.get("MsgType"))) {
            if (WeixinUtil.EVENT_SUBSCRIBE.equals(requestParam.get("Event"))) {
                Wuser weiUser = GetPersoninf.getPersonalInf(AccessTokenThread.access_token.getAccesstoken(), getTextMessage.getFromUserName());
                wuserService.save(weiUser);
                SendTextMessage sendTextMessage = new SendTextMessage();
                sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                sendTextMessage.setCreateTime(new Date().getTime());
                sendTextMessage.setContent("欢迎您！");
                return XmlMessUtil.textMessageToXml(sendTextMessage);
            } else if (WeixinUtil.EVENT_CLICK.equals(requestParam.get("Event"))) {
                if ("11".equals(requestParam.get("EventKey"))) {
                    SendTextMessage sendTextMessage = new SendTextMessage();
                    sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                    sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                    sendTextMessage.setCreateTime(new Date().getTime());
                    sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                    StringBuffer sb = new StringBuffer();
                    sb.append("展厅位于西北工业大学三航科技大厦一层及二层，装修面积约1800平方米，主要建设内容包括展厅装修、多媒体设备安装、展品展示等内容。");
                    sendTextMessage.setContent(sb.toString());
                    return XmlMessUtil.textMessageToXml(sendTextMessage);
                } else if ("12".equals(requestParam.get("EventKey"))) {
                    SendTextMessage sendTextMessage = new SendTextMessage();
                    sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                    sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                    sendTextMessage.setCreateTime(new Date().getTime());
                    sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                    StringBuffer sb = new StringBuffer();
                    sb.append("服务中心位于北航大厦五层，装修面积约1200平方米，主要装修内容包括参军咨询服务大厅、行政综合业务办理区、检测认证办公区、洽谈室等服务空间;");
                    sendTextMessage.setContent(sb.toString());
                    return XmlMessUtil.textMessageToXml(sendTextMessage);
                }else if ("31".equals(requestParam.get("EventKey"))) {
                    SendTextMessage sendTextMessage = new SendTextMessage();
                    sendTextMessage.setFromUserName(getTextMessage.getToUserName());
                    sendTextMessage.setToUserName(getTextMessage.getFromUserName());
                    sendTextMessage.setCreateTime(new Date().getTime());
                    sendTextMessage.setMsgType(WeixinUtil.RECRIVE_TEXT);
                    StringBuffer sb = new StringBuffer();
                    sb.append("地址：深圳市南山区粤海街道粤兴四道北航大厦一号楼5楼").append("\n");
                    sb.append("联系方式：13825216821（李总）").append("\n");
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append("0755-22670807").append("\n");
                    sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append("0755-22670656").append("\n");
                    sendTextMessage.setContent(sb.toString());
                    return XmlMessUtil.textMessageToXml(sendTextMessage);
                }
            } else if(WeixinUtil.EVENT_VIEW.equals(requestParam.get("Event"))){
                HttpSession session = request.getSession();
                session.setAttribute("openid",getTextMessage.getFromUserName());
                *//*request.getSession().setAttribute("openid","1111");*//*
                System.out.println( request.getSession().getAttribute("openid"));
                return null;
            }
        }
        return null;
    }*/

}
