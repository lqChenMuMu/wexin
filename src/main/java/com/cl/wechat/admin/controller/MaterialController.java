package com.cl.wechat.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.ClassMaterial;
import com.cl.wechat.admin.entity.Material;
import com.cl.wechat.admin.service.ClassMaterialService;
import com.cl.wechat.admin.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@RestController
@RequestMapping("/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @Autowired
    private ClassMaterialService classMaterialService;

    @GetMapping("/get")
    public Resp getMaterial(String classIds) {
        String[] classIdArray = classIds.split(",");
        List<ClassMaterial> classMaterialList = classMaterialService.list(new QueryWrapper<ClassMaterial>().in("class_id", classIdArray));
        List<Integer> materialIdsList = classMaterialList.stream().map(ClassMaterial::getMaterialId).distinct().collect(Collectors.toList());
        List<Material> materialList = materialService.list(new QueryWrapper<Material>().in("id", materialIdsList));
        return new Resp(materialList);
    }


}
