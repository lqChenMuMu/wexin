package com.cl.wechat.admin.vo;

import com.cl.wechat.admin.entity.FirstClass;
import com.cl.wechat.admin.entity.SecondClass;
import lombok.Data;

import java.util.List;

@Data
public class ClassShowVO {

    private FirstClass firstClass;

    private List<SecondClass> secondClassList;
}
