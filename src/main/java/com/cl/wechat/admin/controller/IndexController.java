package com.cl.wechat.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {

    @RequestMapping("/classShow")
    public String classShow(){
        return "classShow";
    }

    @RequestMapping("/myAppointment")
    public String myAppointment(){
        return "myAppointment";
    }
}
