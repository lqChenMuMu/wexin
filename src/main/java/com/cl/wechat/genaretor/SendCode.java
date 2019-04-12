package com.cl.wechat.genaretor;

import java.util.*;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.cl.wechat.genaretor.CheckSumBuilder;

/**
 * 发送验证码
 * @author liuxuanlin
 *
 */
public class SendCode {
    //发送验证码的请求路径URL
    private static final String
            SERVER_URL="https://api.netease.im/sms/sendcode.action";

    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    private static final String
            APP_KEY="3332f54e0e60cf89c59e7625d6b557ff";
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    private static final String APP_SECRET="44ce60722d35";
    //随机数
    private static final String NONCE="123456";
    //短信模板ID
    private static final String TEMPLATEID="9684548";
    //手机号
    private static final String MOBILE="18371952812";
    //验证码长度，范围4～10，默认为4
    private static final String CODELEN="6";

    public static void main(String[] args) throws Exception {

        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("templateid", TEMPLATEID);
        paramMap.put("mobile", MOBILE);
        paramMap.put("codeLen", CODELEN);

        String curTime = String.valueOf((new Date()).getTime() / 1000L);

         /*
         * 参考计算CheckSum的java代码，在上述文档的参数列表中，有CheckSum的计算文档示例
         */
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);



        String result2 = HttpRequest.post(SERVER_URL)
                .header("AppKey", APP_KEY)//头信息，多个头信息多次调用此方法即可
                .header("Nonce", NONCE)
                .header("CurTime", curTime)
                .header("CheckSum", checkSum)
                .header(Header.CONTENT_TYPE,"application/x-www-form-urlencoded;charset=utf-8")
                .form(paramMap)//表单内容
                .timeout(20000)//超时，毫秒
                .execute().body();
        System.out.println(result2);

        /*DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(SERVER_URL);



        // 设置请求的header
        httpPost.addHeader("AppKey", APP_KEY);
        httpPost.addHeader("Nonce", NONCE);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的的参数，requestBody参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        *//*
         * 1.如果是模板短信，请注意参数mobile是有s的，详细参数配置请参考“发送模板短信文档”
         * 2.参数格式是jsonArray的格式，例如 "['13888888888','13666666666']"
         * 3.params是根据你模板里面有几个参数，那里面的参数也是jsonArray格式
         *//*
        nvps.add(new BasicNameValuePair("templateid", TEMPLATEID));
        nvps.add(new BasicNameValuePair("mobile", MOBILE));
        nvps.add(new BasicNameValuePair("codeLen", CODELEN));

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
        *//*
         * 1.打印执行结果，打印结果一般会200、315、403、404、413、414、500
         * 2.具体的code有问题的可以参考官网的Code状态表
         *//*
        System.out.println(EntityUtils.toString(response.getEntity(), "utf-8"));*/

    }
}