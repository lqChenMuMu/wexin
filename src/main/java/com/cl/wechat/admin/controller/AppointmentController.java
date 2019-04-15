package com.cl.wechat.admin.controller;


import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.config.ValidateCodeUtil;
import com.cl.wechat.admin.entity.*;
import com.cl.wechat.admin.service.*;
import com.cl.wechat.admin.vo.MyAppointmentVO;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private WuserService wuserService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private SecondClassService secondClassService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ClassMaterialService classMaterialService;

    // 短信应用SDK AppID
    private static int appid = 1400197798; // 1400开头

    // 短信应用SDK AppKey
    private static String appkey = "b1b5dc309bd0b0407272284d4e71ff78";

    // 短信模板ID，需要在短信应用中申请
    private static int templateId = 311708; // NOTE: 这里的模板ID`7839`只是一个示例，真实的模板ID需要在短信控制台中申请
    //templateId7839对应的内容是"您的验证码是: {1}"
    // 签名
    private static String smsSign = "扁头娃个人使用"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是一个示例，真实的签名需要在短信控制台申请。

    @GetMapping("/sendCode")
    public Resp sendCode(String telphone, HttpServletRequest request){
        String code = RandomUtil.randomNumbers(4);
        String[] params = {code,"10"};//数组具体的元素个数和模板中变量个数必须一致，例如事例中templateId:5678对应一个变量，参数数组中元素个数也必须是一个
        SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
        SmsSingleSenderResult result = null;  // 签名参数未提供或者为空时，会使用默认签名发送短信
        try {
            result = ssender.sendWithParam("86", telphone,
                    templateId, params, smsSign, "", "");
            if(result.result == 0){
                Wuser wuser = new Wuser();
                wuser.setValidateCode(code);
                wuser.setValidateTime(String.valueOf(new Date().getTime()));
                Wuser qWuser = new Wuser();
                qWuser.setOpenid((String) request.getSession().getAttribute("openid"));
                wuserService.update(wuser,new QueryWrapper<>(qWuser));
            }

        } catch (HTTPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(result);
        return new Resp(result.result);
    }

    @PostMapping("/affirm")
    public Resp affirm(Appointment appointment, HttpServletRequest request){
        String openid = (String) request.getSession().getAttribute("openid");
        Wuser qwuser = new Wuser();
        qwuser.setOpenid(openid);
        Wuser wuser = wuserService.getOne(new QueryWrapper<>(qwuser));
        if(wuser.getValidateCode().equals(appointment.getValidateCode())){
            appointment.setSubmitTime(String.valueOf(new Date().getTime()));
            return  new Resp(appointmentService.save(appointment));
        }else{
            return  new Resp(new Exception("验证码错误"));
        }
    }


    @GetMapping("/myAppointment")
    public Resp getMyAppointment(HttpServletRequest request){
        Appointment appointment = new Appointment();
        appointment.setOpenId(request.getSession().getAttribute("openid").toString());
        List<MyAppointmentVO> myAppointmentVOS = new ArrayList<>();
        appointmentService.list(new QueryWrapper<>(appointment)).forEach(item -> {
            MyAppointmentVO myAppointmentVO = new  MyAppointmentVO();
            List<SecondClass> secondClassList = secondClassService.list(new QueryWrapper<SecondClass>().in("id",item.getClassId().split(",")));
            List<ClassMaterial> classMaterialList = classMaterialService.list(new QueryWrapper<ClassMaterial>().in("class_id",secondClassList.stream().map(SecondClass::getId).collect(Collectors.toList())));
            List<Material> materialList = materialService.list(new QueryWrapper<Material>().in("id",classMaterialList.stream().map(ClassMaterial::getMaterialId).distinct().collect(Collectors.toList())));
            myAppointmentVO.setAppointment(item);
            myAppointmentVO.setMaterialList(materialList);
            myAppointmentVO.setSecondClassList(secondClassList);
            myAppointmentVOS.add(myAppointmentVO);
        });
        return new Resp(myAppointmentVOS);
    }

}
