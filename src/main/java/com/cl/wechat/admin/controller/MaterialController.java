package com.cl.wechat.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.ClassMaterial;
import com.cl.wechat.admin.entity.FirstClass;
import com.cl.wechat.admin.entity.Material;
import com.cl.wechat.admin.service.ClassMaterialService;
import com.cl.wechat.admin.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getByClass")
    public Resp getMaterial(String classIds) {
        String[] classIdArray = classIds.split(",");
        List<ClassMaterial> classMaterialList = classMaterialService.list(new QueryWrapper<ClassMaterial>().in("class_id", classIdArray));
        List<Long> materialIdsList = classMaterialList.stream().map(ClassMaterial::getMaterialId).distinct().collect(Collectors.toList());
        List<Material> materialList = materialService.list(new QueryWrapper<Material>().in("id", materialIdsList));
        return new Resp(materialList);
    }

    @GetMapping("/back/list")
    public Resp list(){
        return new Resp(materialService.list());
    }

    @PostMapping("/back/save")
    public Resp save(@Validated Material material){
        return new Resp(materialService.save(material));
    }

    @PutMapping("/back/update")
    public Resp update(@Validated Material material){
        if(material.getId() == null){
            return new Resp(new Exception("请选择一条记录"));
        }
        return new Resp(materialService.updateById(material));
    }

    @DeleteMapping("/back/del")
    public Resp del(String id){
        return new Resp(materialService.removeById(id));
    }

    @GetMapping("/back/get")
    public Resp get(String id){
        return new Resp(materialService.getById(id));
    }

}
