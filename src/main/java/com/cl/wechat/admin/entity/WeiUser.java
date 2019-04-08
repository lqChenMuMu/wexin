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

    private Integer subscribe;

    private String openid;

    private String nickname;

    private Integer sex;

    private String city;

    private String province;

    private String country;

    private String language;

    private String headimgurl;

    private String subscribetime;


}
