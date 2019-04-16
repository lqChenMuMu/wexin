package com.cl.wechat.admin.controller;


import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.service.FirstClassService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/first-class")
public class FirstClassController {

    @Autowired
    private FirstClassService firstClassService;

    @GetMapping("/list")
    public Resp list(){
        return new Resp(firstClassService.list());
    }
}
