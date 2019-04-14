package com.cl.wechat.genaretor;

import cn.hutool.core.util.RandomUtil;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;

import java.io.IOException;

public class TXSendCode {

    // 短信应用SDK AppID
    private static int appid = 1400197798; // 1400开头

    // 短信应用SDK AppKey
    private static String appkey = "b1b5dc309bd0b0407272284d4e71ff78";

    // 需要发送短信的手机号码
    private static String[] phoneNumbers = {"13597857551"};

    // 短信模板ID，需要在短信应用中申请
    private static int templateId = 311708; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    //templateId7839对应的内容是"您的验证码是: {1}"
// 签名
    private static String smsSign = "扁头娃个人使用"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台申请。


    public static void main(String[] args) {
        Integer code = RandomUtil.randomInt(4);
        System.out.println(code.toString());
        String[] params = {code.toString(),"10"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = null;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        try {
            result = ssender.sendWithParam("86", phoneNumbers[0],
                    templateId, params, smsSign, "", "");
        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
