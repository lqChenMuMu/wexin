package com.cl.wechat.admin.vo;

import com.cl.wechat.admin.entity.SecondClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SecondClassVO extends SecondClass{

    private String firstClassName;

    private String materialNames;
}
