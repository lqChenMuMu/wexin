package com.cl.wechat.admin.entity;

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
 * @since 2019-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SecondClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String className;

    private Integer firstClassId;

    private String details;

    private String remark;


}
