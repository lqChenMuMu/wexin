package com.cl.wechat.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-03-29
 */
@Controller
@RequestMapping("/admin")
public class UserController {

    @RequestMapping
    public String index(){
        return "index";
    }

 /*   @RequestMapping("/index2")
    public String index2(){
        System.out.println("11111");
        return "index2";
    }*/

}
