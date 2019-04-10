package com.cl.wechat.admin.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author bian
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecondClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("className")
    private String className;

    @TableField("firstClassId")
    private Integer firstClassId;

    private String photo;

    private String remark;


}
