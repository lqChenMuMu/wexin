package com.cl.wechat.admin.controller;


import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.config.ValidateCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    @GetMapping("/sendCode")
    public Resp sendCode(String telphone){
        return ValidateCodeUtil.sendCode(telphone);
    }

}
