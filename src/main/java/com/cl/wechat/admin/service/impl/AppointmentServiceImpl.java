package com.cl.wechat.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.config.ValidateCodeUtil;
import com.cl.wechat.admin.entity.Appointment;
import com.cl.wechat.admin.entity.SecondClass;
import com.cl.wechat.admin.entity.Wuser;
import com.cl.wechat.admin.mapper.AppointmentMapper;
import com.cl.wechat.admin.mapper.SecondClassMapper;
import com.cl.wechat.admin.mapper.WuserMapper;
import com.cl.wechat.admin.service.AppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@Service
@AllArgsConstructor
public class AppointmentServiceImpl extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {

    private SecondClassMapper secondClassMapper;

    private WuserMapper wuserMapper;

    /**
     * 提交预约信息
     * @param appointment
     * @param openid
     * @return
     */
    @Transactional
    @Override
    public Object affirm(Appointment appointment, String openid) {
        Wuser qwuser = new Wuser();
        qwuser.setOpenid(openid);
        Wuser wuser = wuserMapper.selectOne(new QueryWrapper<>(qwuser));
        Long failureTime = Long.valueOf(wuser.getValidateTime()) + 10 * 60 * 1000;
        if (wuser.getValidateCode().equals(appointment.getValidateCode()) && new Date().getTime() < failureTime) {
            appointment.setSubmitTime(String.valueOf(new Date().getTime()));
            appointment.setOpenId(openid);
            this.save(appointment);
            List<SecondClass> secondClassList = secondClassMapper.selectList(new QueryWrapper<SecondClass>().in("id", appointment.getClassId().split(",")));
           /* String classStr = "";
            List<String> classNames = secondClassList.stream().map(SecondClass::getClassName).collect(Collectors.toList());
            for (Integer i = 0; i < classNames.size(); i++) {
                classStr += (i + 1) + ":";
                classStr += classNames.get(i) + " ";
            }*/
            ValidateCodeUtil.sendNotice(appointment.getTime(), null, appointment.getTelphone());
            wuser.setValidateTime("0");
            wuserMapper.updateById(wuser);
            return true;
        } else {
            return new Exception("验证码错误");
        }
    }
}
