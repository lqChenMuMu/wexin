package com.cl.wechat.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cl.wechat.admin.config.Resp;
import com.cl.wechat.admin.entity.FirstClass;
import com.cl.wechat.admin.entity.SecondClass;
import com.cl.wechat.admin.service.FirstClassService;
import com.cl.wechat.admin.service.SecondClassService;
import com.cl.wechat.admin.vo.ClassShowVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/second-class")
public class SecondClassController {

    private FirstClassService firstClassService;

    private SecondClassService secondClassService;

    @GetMapping("/classInfo")
    public Resp getClassInfo(){
        List<ClassShowVO> result = new ArrayList<>();
        firstClassService.list().forEach(firstClass -> {
            SecondClass qSecondClass = new SecondClass();
            qSecondClass.setFirstClassId(firstClass.getId());
            ClassShowVO showVO = new ClassShowVO();
            showVO.setFirstClass(firstClass);
            showVO.setSecondClassList(secondClassService.list(new QueryWrapper<>(qSecondClass)));
            result.add(showVO);
        });
        return new Resp(result);
    }
}
