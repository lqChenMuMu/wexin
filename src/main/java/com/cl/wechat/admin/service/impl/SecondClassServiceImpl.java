package com.cl.wechat.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.entity.ClassMaterial;
import com.cl.wechat.admin.entity.SecondClass;
import com.cl.wechat.admin.mapper.ClassMaterialMapper;
import com.cl.wechat.admin.mapper.SecondClassMapper;
import com.cl.wechat.admin.service.SecondClassService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@AllArgsConstructor
@Service
public class SecondClassServiceImpl extends ServiceImpl<SecondClassMapper, SecondClass> implements SecondClassService {

    private SecondClassMapper secondClassMapper;

    private ClassMaterialMapper classMaterialMapper;

    @Transactional
    @Override
    public void saveSecondClass(SecondClass secondClass) {
        secondClassMapper.insert(secondClass);
        if(StrUtil.isBlank(secondClass.getMaterialIds())){
            return;
        }
        List<String> materialIds = Arrays.asList(secondClass.getMaterialIds().split(","));
        materialIds.forEach(id -> {
            ClassMaterial classMaterial = new ClassMaterial();
            classMaterial.setClassId(secondClass.getId());
            classMaterial.setMaterialId(Long.valueOf(id));
            classMaterialMapper.insert(classMaterial);
        });
    }

    @Transactional
    @Override
    public void updateSecondClass(SecondClass secondClass) {
        secondClassMapper.updateById(secondClass);
        ClassMaterial qClassMaterial = new ClassMaterial();
        qClassMaterial.setClassId(secondClass.getId());
        classMaterialMapper.delete(new QueryWrapper<>(qClassMaterial));
        if(StrUtil.isBlank(secondClass.getMaterialIds())){
            return;
        }
        List<String> materialIds = Arrays.asList(secondClass.getMaterialIds().split(","));
        materialIds.forEach(id -> {
            ClassMaterial classMaterial = new ClassMaterial();
            classMaterial.setClassId(secondClass.getId());
            classMaterial.setMaterialId(Long.valueOf(id));
            classMaterialMapper.insert(classMaterial);
        });
    }

    @Transactional
    @Override
    public void delSecondClass(String id) {
        ClassMaterial qClassMaterial = new ClassMaterial();
        qClassMaterial.setClassId(Long.valueOf(id));
        classMaterialMapper.delete(new QueryWrapper<>(qClassMaterial));
        secondClassMapper.deleteById(id);
    }
}
