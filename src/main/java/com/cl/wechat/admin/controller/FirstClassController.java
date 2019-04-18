package com.cl.wechat.admin.controller;


import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.FirstClass;
import com.cl.wechat.admin.service.FirstClassService;
import net.sf.jsqlparser.statement.select.First;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/back/list")
    public Resp list(){
        return new Resp(firstClassService.list());
    }

    @PostMapping("/back/save")
    public Resp save(@Validated FirstClass firstClass){
        return new Resp(firstClassService.save(firstClass));
    }

    @PutMapping("/back/update")
    public Resp update(@Validated FirstClass firstClass){
        if(firstClass.getId() == null){
            return new Resp(new Exception("请选择一条记录"));
        }
        return new Resp(firstClassService.updateById(firstClass));
    }

    @DeleteMapping("/back/del")
    public Resp del(String id){
        return new Resp(firstClassService.removeById(id));
    }

    @GetMapping("/back/get")
    public Resp get(String id){
        return new Resp(firstClassService.getById(id));
    }
}
