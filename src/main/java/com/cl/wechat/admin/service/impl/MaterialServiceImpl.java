package com.cl.wechat.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.entity.ClassMaterial;
import com.cl.wechat.admin.entity.Material;
import com.cl.wechat.admin.mapper.ClassMaterialMapper;
import com.cl.wechat.admin.mapper.MaterialMapper;
import com.cl.wechat.admin.service.MaterialService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@Service
@AllArgsConstructor
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    private ClassMaterialMapper classMaterialMapper;

    @Transactional
    @Override
    public boolean delMaterial(Long id) {
        this.removeById(id);
        ClassMaterial classMaterial = new ClassMaterial();
        classMaterial.setMaterialId(id);
        classMaterialMapper.delete(new QueryWrapper<>(classMaterial));
        return true;
    }
}
