package com.cl.wechat.admin.vo;

import com.cl.wechat.admin.entity.Appointment;
import com.cl.wechat.admin.entity.Material;
import com.cl.wechat.admin.entity.SecondClass;
import lombok.Data;

import java.util.List;

/**
 * @Author chenlin
 * @DateTime 2019/4/1418:30
 * @Desc
 **/
@Data
public class MyAppointmentVO {

    private Appointment appointment;

    private List<SecondClass> secondClassList;

    private List<Material> materialList;
}
