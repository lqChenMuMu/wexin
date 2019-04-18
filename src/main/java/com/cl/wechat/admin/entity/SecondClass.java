package com.cl.wechat.admin.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class SecondClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String className;

    private Long firstClassId;

    private String details;

    private String remark;

    @TableField(exist = false)
    private String materialIds;

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getId() {
        return id;
    }

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getFirstClassId() {
        return firstClassId;
    }
}
