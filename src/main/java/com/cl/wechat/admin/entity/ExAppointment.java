package com.cl.wechat.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author bian
 * @since 2019-05-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExAppointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String openId;

    private String name;

    private String company;

    private String job;

    private String telphone;

    private String type;

    private String object;

    private String formDate;

    private String toDate;

    private String subTime;

    @TableField(exist = false)
    private String validateCode;

    private Integer people;

    private String remark;

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getId() {
        return id;
    }
}
