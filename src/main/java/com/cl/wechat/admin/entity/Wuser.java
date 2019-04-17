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
public class Wuser implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String country;

    private String province;

    private String city;

    private String openid;

    private Integer sex;

    private String nickname;

    private String headimgurl;

    private String validateCode;

    private String validateTime;

    @JsonSerialize(using=ToStringSerializer.class)
    public Long getId() {
        return id;
    }
}
