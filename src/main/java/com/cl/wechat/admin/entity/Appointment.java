package com.cl.wechat.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String classId;

    private String openId;

    private String name;

    private String company;

    private String time;

    private String telphone;

    private String submitTime;

    @TableLogic
    private Integer isDelete;

    private String validateCode;

    private String remark;

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getId() {
        return id;
    }
}
