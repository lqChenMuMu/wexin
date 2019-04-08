package com.cl.wechat.base.advanced.model;

import lombok.Data;

/**
*   
* 项目名称：wechat
* 类名称：PersonalInf  
* 类描述：用户个人基本信息  
* 创建人：WQ  
* 创建时间：2014-1-18 下午4:24:09  
* @version       
*/
@Data
public class PersonalInf {
	// 用户是否订阅该公众号标识，值为1时，代表此用户关注了该公众号；没有值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
	private int subscribe;
	// 用户的标识，对当前公众号唯一 
	private String openid;
	// 用户的昵称 
	private String nickname;
	// 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知 
	private int sex;
	// 用户所在城市 
	private String city;
	// 用户所在省份 
	private String province;
	// 用户所在国家 
	private String country;
	// 用户的语言，简体中文为zh_CN  
	private String language;
	// 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空  
	private String headimgurl;
	// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间 
	private String subscribetime;
}
