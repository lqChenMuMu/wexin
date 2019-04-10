package com.cl.wechat.admin.controller;


import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.Appointment;
import com.cl.wechat.admin.entity.Personnel;
import com.cl.wechat.admin.entity.PersonnelClass;
import com.cl.wechat.admin.service.AppointmentService;
import com.cl.wechat.admin.service.PersonnelClassService;
import com.cl.wechat.admin.service.PersonnelService;
import com.cl.wechat.admin.vo.PersonnelSaveVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-04-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/admin/personnel")
public class PersonnelController {

    private PersonnelService personnelService;

    private PersonnelClassService personnelClassService;

    private AppointmentService appointmentService;

    @GetMapping("/page")
    public Resp page(Page<Personnel> page, Personnel personnel) {
        return new Resp(personnelService.page(page, new QueryWrapper<>(personnel)));
    }

    @PostMapping("/save")
    public Resp save(PersonnelSaveVO personnel) {

        personnelService.save(personnel);
        personnel.getClassIds().forEach(classId -> {
            PersonnelClass personnelClass = new PersonnelClass();
            personnelClass.setClassId(classId);
            personnelClass.setPersonnelId(personnel.getId());
            personnelClassService.save(personnelClass);
        });
        return new Resp(true);
    }

    @PutMapping("/edit")
    public Resp update(PersonnelSaveVO personnel) {
        if (null == personnel.getId()) {
            return new Resp(new Exception("缺少id信息"));
        }
        personnelService.updateById(personnel);
        /*删除之前所有类别*/
        PersonnelClass qPersonnelClass = new PersonnelClass();
        qPersonnelClass.setPersonnelId(personnel.getId());
        personnelClassService.remove(new QueryWrapper<>(qPersonnelClass));
        /*添加新的类别*/
        personnel.getClassIds().forEach(classId -> {
            PersonnelClass personnelClass = new PersonnelClass();
            personnelClass.setClassId(classId);
            personnelClass.setPersonnelId(personnel.getId());
            personnelClassService.save(personnelClass);
        });
        return new Resp(true);
    }

    @DeleteMapping("/del")
    public Resp del(String id) {
        PersonnelClass qPersonnelClass = new PersonnelClass();
        qPersonnelClass.setPersonnelId(Integer.valueOf(id));
        personnelClassService.remove(new QueryWrapper<>(qPersonnelClass));
        return new Resp(personnelService.removeById(id));
    }

    @GetMapping("/queryByOption")
    public Resp queryByClassAndDate(Integer classId, String date) {
        PersonnelClass qPersonnelClass = new PersonnelClass();
        qPersonnelClass.setClassId(classId);
        List<PersonnelClass> personnelClasses = personnelClassService.list(new QueryWrapper<>(qPersonnelClass));
        if (CollUtil.isEmpty(personnelClasses)) {
            return new Resp();
        } else {
            List<Integer> personnelIds = personnelClasses.stream().map(PersonnelClass::getPersonnelId).collect(Collectors.toList());
            if (StrUtil.isNotBlank(date)) {
                personnelIds.stream().filter(id -> {
                    Appointment qAppointment = new Appointment();
                    qAppointment.setPersonnelId(id);
                    if (appointmentService.count(new QueryWrapper<>(qAppointment).like("time", date)) >= 2) {
                        return false;
                    } else {
                        return true;
                    }
                });
            }
            return new Resp( personnelService.list(new QueryWrapper<Personnel>().in("personnel_id", personnelIds)));
        }
    }

    @GetMapping("/getById")
    public Resp personnelInfo(String id){
        return new Resp(personnelService.getById(id));
    }
}
