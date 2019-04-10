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
 * @since 2019-04-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class PersonnelClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 二级类别目录
     */
    private Integer classId;

    private Integer personnelId;


}
