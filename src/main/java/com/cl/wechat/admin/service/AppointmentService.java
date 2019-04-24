package com.cl.wechat.admin.service;

import com.cl.wechat.admin.entity.Appointment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
public interface AppointmentService extends IService<Appointment> {

    Object affirm(Appointment appointment, String openid);

}
