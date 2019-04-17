package com.cl.wechat.admin.service;

import com.cl.wechat.admin.entity.SecondClass;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
public interface SecondClassService extends IService<SecondClass> {

    public void saveSecondClass(SecondClass secondClass);

    public void updateSecondClass(SecondClass secondClass);

    public void delSecondClass(String id);

}
