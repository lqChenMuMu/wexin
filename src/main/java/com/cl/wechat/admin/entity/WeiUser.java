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
 * @since 2019-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class WeiUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private String openid;

    private String sex;

    private String city;

    private String country;

    private String province;

    private String headimgurl;

    private Integer subcribe;

    private String language;

    private String remark;

    private String nickname;
}
