package com.cl.wechat.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.entity.Material;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
public interface MaterialService extends IService<Material> {

    boolean delMaterial(Long id);

}
