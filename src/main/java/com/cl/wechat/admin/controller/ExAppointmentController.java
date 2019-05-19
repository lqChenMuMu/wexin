package com.cl.wechat.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.config.ValidateCodeUtil;
import com.cl.wechat.admin.entity.ExAppointment;
import com.cl.wechat.admin.entity.SecondClass;
import com.cl.wechat.admin.entity.Wuser;
import com.cl.wechat.admin.service.ExAppointmentService;
import com.cl.wechat.admin.service.WuserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-05-19
 */
@AllArgsConstructor
@RestController
@RequestMapping("/ex-appointment")
public class ExAppointmentController {

    private ExAppointmentService exAppointmentService;

    private WuserService wuserService;


    @PostMapping("/affirm")
    public Resp affirm(ExAppointment appointment, HttpServletRequest request){
        String openid = (String) request.getSession().getAttribute("openid");
        Wuser qwuser = new Wuser();
        qwuser.setOpenid(openid);
        Wuser wuser = wuserService.getOne(new QueryWrapper<>(qwuser));
        Long failureTime = Long.valueOf(wuser.getValidateTime()) + 10 * 60 * 1000;
        if (wuser.getValidateCode().equals(appointment.getValidateCode()) && new Date().getTime() < failureTime) {
            appointment.setSubTime(String.valueOf(new Date().getTime()));
            appointment.setOpenId(openid);
            exAppointmentService.save(appointment);
            ValidateCodeUtil.sendNotice(appointment.getFormDate(), null, appointment.getTelphone()+"");
            wuser.setValidateTime("0");
            wuserService.updateById(wuser);
        } else {
            return new Resp(new Exception("验证码错误"));
        }
        return new Resp();
    }

}
