package com.cl.wechat.admin.config;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.entity.Wuser;
import com.cl.wechat.admin.service.WuserService;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Date;

public class ValidateCodeUtil {
    // 短信应用SDK AppID
    //private static int appid = 1400197798; // 1400开头
    private static int appid = 1400203318;

    // 短信应用SDK AppKey
    //private static String appkey = "b1b5dc309bd0b0407272284d4e71ff78";
    private static String appkey = "b8703ba30642ef5667d3504821e09787";

    // 短信模板ID，需要在短信应用中申请
    //private static int templateId = 311708; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    private static int templateId = 318705;

    //private static int noticeTemplateId = 313317;
    private static int noticeTemplateId = 318700;
    // 签名
    //private static String smsSign = "扁头娃个人使用"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台申请。
    private static String smsSign = "学府军民服务中心";

    public static String sendCode(String telphone, String openId) {
        System.out.println(telphone);
        String code = RandomUtil.randomNumbers(4);
        String[] params = {code, "10"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = null;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        try {
            result = ssender.sendWithParam("86", telphone,
                    templateId, params, smsSign, "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        if (result.result == 0) {
            return code;
        } else {
            return "";
        }
    }

    public static Integer sendNotice(String date, String classList, String telphone) {
        //String value = date + " " + "服务：" + classList;
        //value = value.substring(0,11);
        String[] params = {date};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = null;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        try {
            result = ssender.sendWithParam("86", telphone,
                    noticeTemplateId, params, smsSign, "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return result.result;
    }
}
