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
public class Personnel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @TableField("userName")
    private String userName;

    private String tag;

    /**
     * 擅长
     */
    private String adept;

    private String profile;

    private String details;

    @TableField("appinmentNum")
    private Integer appinmentNum;

    private String synopsis1;

    private String synopsis2;

    private String synopsis3;


}
