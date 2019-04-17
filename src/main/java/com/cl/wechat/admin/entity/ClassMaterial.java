package com.cl.wechat.admin.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2019-04-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ClassMaterial implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long classId;

    private Long materialId;

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getId() {
        return id;
    }

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getClassId() {
        return classId;
    }

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getMaterialId() {
        return materialId;
    }
}
