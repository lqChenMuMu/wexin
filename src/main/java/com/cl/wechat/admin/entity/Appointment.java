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
 * @since 2019-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer personnelId;

    private Integer classId;

    private String time;

    private String telphone;

    @TableField("submitTime")
    private String submitTime;

    /**
     * 1：正常预约 2：手动设置
     */
    @TableField("appointmentType")
    private Integer appointmentType;

    /**
     * 0：为处理 1：已处理
     */
    @TableField("appointmentState")
    private Integer appointmentState;

    private Integer isDelete;


}
