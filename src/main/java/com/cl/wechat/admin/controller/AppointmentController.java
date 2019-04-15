package com.cl.wechat.admin.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
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


    @GetMapping("/sendCode")
    public Resp sendCode(String telphone, HttpServletRequest request){

        String result = ValidateCodeUtil.sendCode(telphone,(String) request.getSession().getAttribute("openid"));
            if(StrUtil.isNotBlank((String) request.getSession().getAttribute("openid"))){
                Wuser wuser = new Wuser();
                wuser.setValidateCode(result);
                wuser.setValidateTime(String.valueOf(new Date().getTime()));
                Wuser qWuser = new Wuser();
                qWuser.setOpenid((String) request.getSession().getAttribute("openid"));
                wuserService.update(wuser,new QueryWrapper<>(qWuser));
                return new Resp(0);
            }else {
                return new Resp(1);
            }
    }

    @PostMapping("/affirm")
    public Resp affirm(Appointment appointment, HttpServletRequest request){
        String openid = (String) request.getSession().getAttribute("openid");
        Wuser qwuser = new Wuser();
        qwuser.setOpenid(openid);
        Wuser wuser = wuserService.getOne(new QueryWrapper<>(qwuser));
        Long failureTime = Long.valueOf(wuser.getValidateTime()) + 10 * 60 * 1000;
        if(wuser.getValidateCode().equals(appointment.getValidateCode()) && new Date().getTime() < failureTime){
            appointment.setSubmitTime(String.valueOf(new Date().getTime()));
            appointment.setOpenId(openid);
            appointmentService.save(appointment);
            List<SecondClass> secondClassList = secondClassService.list(new QueryWrapper<SecondClass>().in("id",appointment.getClassId().split(",")));
            String classStr = "";
            List<String> classNames = secondClassList.stream().map(SecondClass::getClassName).collect(Collectors.toList());
            for (Integer i = 0; i<classNames.size(); i++) {
                classStr += (i+1) +":";
                classStr += classNames.get(i)+" ";
            }
           ValidateCodeUtil.sendNotice(appointment.getTime(),classStr,appointment.getTelphone());
            return  new Resp(true);
        }else{
            return  new Resp(new Exception("验证码错误"));
        }
    }


    @GetMapping("/myAppointment")
    public Resp getMyAppointment(HttpServletRequest request){
        Appointment appointment = new Appointment();
        appointment.setOpenId(request.getSession().getAttribute("openid").toString());
//        appointment.setOpenId("os1do6Dn0iFOVb1HHhwwHr4BxNpM");
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
