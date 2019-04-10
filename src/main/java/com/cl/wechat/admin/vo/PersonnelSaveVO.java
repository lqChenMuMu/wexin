package com.cl.wechat.admin.vo;

import com.cl.wechat.admin.entity.Personnel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersonnelSaveVO extends Personnel {

    private List<Integer> classIds;
}
