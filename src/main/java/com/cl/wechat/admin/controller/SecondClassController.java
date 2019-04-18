package com.cl.wechat.admin.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.ClassMaterial;
import com.cl.wechat.admin.entity.FirstClass;
import com.cl.wechat.admin.entity.Material;
import com.cl.wechat.admin.entity.SecondClass;
import com.cl.wechat.admin.service.ClassMaterialService;
import com.cl.wechat.admin.service.FirstClassService;
import com.cl.wechat.admin.service.MaterialService;
import com.cl.wechat.admin.service.SecondClassService;
import com.cl.wechat.admin.vo.ClassShowVO;
import com.cl.wechat.admin.vo.SecondClassVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
@AllArgsConstructor
@RequestMapping("/second-class")
public class SecondClassController {

    private FirstClassService firstClassService;

    private SecondClassService secondClassService;

    private ClassMaterialService classMaterialService;

    private MaterialService materialService;


    @GetMapping("/classInfo")
    public Resp getClassInfo() throws IOException {
        List<ClassShowVO> result = new ArrayList<>();
        firstClassService.list().forEach(firstClass -> {
            SecondClass qSecondClass = new SecondClass();
            qSecondClass.setFirstClassId(firstClass.getId());
            ClassShowVO showVO = new ClassShowVO();
            showVO.setFirstClass(firstClass);
            showVO.setSecondClassList(secondClassService.list(new QueryWrapper<>(qSecondClass)));
            result.add(showVO);
        });
        return new Resp(result);
    }

    @GetMapping("/back/list")
    public Resp list(Page<SecondClass> page){
        IPage<SecondClassVO> result = new Page<>();
        List<SecondClassVO> secondClassVOS = new ArrayList<>();
        IPage<SecondClass> secondClassList = secondClassService.page(page);
        secondClassList.getRecords().forEach(secondClass -> {
            SecondClassVO secondClassVO = new SecondClassVO();
            BeanUtil.copyProperties(secondClass,secondClassVO);
            FirstClass firstClass = firstClassService.getById(secondClass.getFirstClassId());
            secondClassVO.setFirstClassName(firstClass.getClassName());
            ClassMaterial qClassMaterial = new ClassMaterial();
            qClassMaterial.setClassId(secondClass.getId());
            List<ClassMaterial> classMaterialList = classMaterialService.list(new QueryWrapper<>(qClassMaterial));
            if(CollUtil.isNotEmpty(classMaterialList)){
                List<Material> materials = materialService.list(new QueryWrapper<Material>().in("id",classMaterialList.stream().map(ClassMaterial::getMaterialId).collect(Collectors.toList())));
                StringBuffer materialNames = new StringBuffer();
                for (int i=0; i<materials.size(); i++) {
                    materialNames.append(i+1).append(":").append(materials.get(i).getMaterialText()).append(" ");
                }
                secondClassVO.setMaterialNames(materialNames.toString());
            }
            secondClassVOS.add(secondClassVO);
        });
        result.setRecords(secondClassVOS);
        result.setTotal(secondClassList.getTotal());
        result.setPages(secondClassList.getPages());
        return new Resp(result);
    }

    @PostMapping("/back/save")
    public Resp save(SecondClass secondClass){
        secondClassService.saveSecondClass(secondClass);
        return new Resp(true);
    }

    @PutMapping("/back/update")
    public Resp update(SecondClass secondClass){
        if(secondClass.getId() == null){
            return new Resp(new Exception("请选择记录"));
        }
        secondClassService.updateSecondClass(secondClass);
        return new Resp(true);
    }

    @DeleteMapping("/back/del")
    public Resp del(String id){
        secondClassService.delSecondClass(id);
        return new Resp();
    }

    @GetMapping("/back/get")
    public Resp get(String id){
        SecondClassVO secondClassVO = new SecondClassVO();
        SecondClass secondClass = secondClassService.getById(id);
        FirstClass firstClass = firstClassService.getById(secondClass.getFirstClassId());
        ClassMaterial qClassMaterial = new ClassMaterial();
        qClassMaterial.setClassId(Long.valueOf(id));
        List<ClassMaterial> classMaterials = classMaterialService.list(new QueryWrapper<>(qClassMaterial));
        List<Material> materials = new ArrayList<>();
        if(CollUtil.isNotEmpty(classMaterials)){
             materials = materialService.list(new QueryWrapper<Material>()
                    .in("id",classMaterials.stream().map(ClassMaterial::getMaterialId).collect(Collectors.toList())));
        }

        BeanUtil.copyProperties(secondClass,secondClassVO);
        secondClassVO.setFirstClassName(firstClass.getClassName());
        StringBuffer materialNames = new StringBuffer();
        for (int i=0; i<materials.size(); i++) {
            materialNames.append(materials.get(i).getId()).append(",");
        }
        secondClassVO.setMaterialNames(materialNames.toString());
        return new Resp(secondClassVO);
    }


}
