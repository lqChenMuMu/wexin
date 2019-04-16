package com.cl.wechat.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 
 * </p>
 *
 * @author bian
 * @since 2019-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class FirstClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "类别名称不能为空！")
    private String className;

    private String remark;

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getId() {
        return id;
    }
}