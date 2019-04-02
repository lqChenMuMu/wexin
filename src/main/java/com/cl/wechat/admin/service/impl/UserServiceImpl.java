package com.cl.wechat.admin.service.impl;

import com.cl.wechat.admin.entity.User;
import com.cl.wechat.admin.mapper.UserMapper;
import com.cl.wechat.admin.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author bian
 * @since 2019-03-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
