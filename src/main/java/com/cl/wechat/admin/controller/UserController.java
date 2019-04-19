package com.cl.wechat.admin.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.User;
import com.cl.wechat.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-03-29
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/back/login")
    public Resp login(HttpServletRequest request, String userName, String password){
        String md5Password = SecureUtil.md5(password);
        User quser = new User();
        quser.setName(userName);
        User user = userService.getOne(new QueryWrapper<>(quser));
        if(user.getPassword().equals(md5Password)){
            HttpSession session = request.getSession();
            session.setAttribute("userName",userName);
        }else{
           return new Resp(new Exception("账号或密码错误！"));
        }
        return new Resp(true);
    }

    @GetMapping("/back/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("userName");
        response.sendRedirect("/back/login");
    }


}
