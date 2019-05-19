package com.cl.wechat.admin.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.config.ValidateCodeUtil;
import com.cl.wechat.admin.entity.*;
import com.cl.wechat.admin.service.*;
import com.cl.wechat.admin.vo.ApplicationQueryVO;
import com.cl.wechat.admin.vo.MyAppointmentVO;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@AllArgsConstructor
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private WuserService wuserService;

    private AppointmentService appointmentService;

    private SecondClassService secondClassService;

    private MaterialService materialService;

    private ClassMaterialService classMaterialService;

    private ExAppointmentService exAppointmentService;


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
        Object res = appointmentService.affirm(appointment,openid);
        if(res instanceof String){
            return  new Resp(new Exception(res.toString()));
        }
        return  new Resp(res);
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
            myAppointmentVO.setAppointmentType(1);
            myAppointmentVO.setTime(item.getTime());
            myAppointmentVO.setMaterialList(materialList);
            myAppointmentVO.setClassList(secondClassList.stream().map(SecondClass::getClassName).collect(Collectors.toList()));
            myAppointmentVOS.add(myAppointmentVO);
        });
        ExAppointment exAppointment = new ExAppointment();
        exAppointment.setOpenId(request.getSession().getAttribute("openid").toString());
        exAppointmentService.list(new QueryWrapper<>(exAppointment)).forEach(item -> {
            MyAppointmentVO myAppointmentVO = new  MyAppointmentVO();
            myAppointmentVO.setAppointmentType(2);
            String time = item.getFormDate();
            time = time.replace("AM","上午");
            time = time.replace("PM","下午");
            myAppointmentVO.setTime(time);
            myAppointmentVO.setClassList(Arrays.asList(item.getType()));
            myAppointmentVO.setMaterialList(new ArrayList<>());
            myAppointmentVOS.add(myAppointmentVO);
        });
        myAppointmentVOS.stream().sorted(Comparator.comparing(MyAppointmentVO::getTime));
        return new Resp(myAppointmentVOS);
    }

    @GetMapping("/back/list")
    public Resp list(Page<Appointment> page, ApplicationQueryVO applicationQueryVO){
        Appointment qAppointment = new Appointment();
        if(StrUtil.isNotBlank(applicationQueryVO.getName())){
            qAppointment.setName(applicationQueryVO.getName());
        }
        if(StrUtil.isNotBlank(applicationQueryVO.getCompany())){
            qAppointment.setCompany(applicationQueryVO.getCompany());
        }
        IPage<Appointment> appointmentList = new Page<>();
        if(null != applicationQueryVO.getSortTime()){
            if(1 == applicationQueryVO.getSortTime()){
                appointmentList = appointmentService.page(page,new QueryWrapper<>(qAppointment).orderByAsc("time"));
            }else if(0 == applicationQueryVO.getSortTime()){
                appointmentList = appointmentService.page(page,new QueryWrapper<>(qAppointment).orderByDesc("time"));
            }
        }else{
            appointmentList = appointmentService.page(page,new QueryWrapper<>(qAppointment));
        }

        appointmentList.getRecords().forEach(appointment -> {
            if(StrUtil.isNotBlank(appointment.getClassId())){
                List<SecondClass> secondClassList = secondClassService.list(new QueryWrapper<SecondClass>()
                        .in("id",appointment.getClassId().split(",")));
                StringBuffer classNames = new StringBuffer();
                for (int i=0; i<secondClassList.size(); i++) {
                    classNames.append((i+1)).append(":").append(secondClassList.get(i).getClassName());
                }
                appointment.setClassId(classNames.toString());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                long lt = new Long(appointment.getSubmitTime());
                Date date = new Date(lt);
                appointment.setSubmitTime(simpleDateFormat.format(date));
            }
        });
        return new Resp(appointmentList);
    }

}
