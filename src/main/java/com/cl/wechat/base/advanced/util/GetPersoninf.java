package com.cl.wechat.base.advanced.util;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cl.wechat.admin.entity.Wuser;
import com.cl.wechat.base.wechatapi.util.CommonUtil;

/**
*
* 项目名称：wechatapi
* 类名称：GetPersoninf
* 类描述：获取用户基本个人信息方法
* 创建人：WQ
* 创建时间：2014-3-7 下午1:43:39
* @version
*/
public class GetPersoninf extends CommonUtil {
	/**
	 * 获取用户基本个人信息
	 *
	 * @param accessToken 调用接口凭证
	 * @param openId 普通用户的标识，对当前公众号唯一
	 * @returnPersonalInf 基本个人信息
	 */
	public static Wuser getPersonalInf(String accessToken, String openId){
		Wuser weiUser=null;
		String requestUrl = GET_PERSONALINF_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
    	// 获取用户信息
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
	    // 如果请求成功
	    if (null != jsonObject) {
				weiUser = JSONUtil.toBean(jsonObject,Wuser.class);
	    }
		return weiUser;
	}

	/**
	 * 查询用户所在分组
	 *
	 * @param accessToken 调用接口凭证
	 * @param openId 普通用户的标识，对当前公众号唯一
	 * @return groupid
	 */
	public static int getPersonGroupId(String accessToken,String openId) {
		int groupId=0;
		String requestUrl=GET_PERSONGROUPID_URL.replace("ACCESS_TOKEN", accessToken);
		// 需要提交的json数据
		String jsonData="{\"openid\":\"%s\"}";
		// 创建分组
		JSONObject jsonObject=httpRequest(requestUrl, "POST",
				String.format(jsonData, openId));
		if (null!=jsonObject) {
			try {
				groupId=jsonObject.getInt("groupid");
			} catch (JSONException e) {
				groupId=-1;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getStr("errmsg");
				log.error("查询用户所在分组失败 errcode:{} errmsg:{} ", errorCode, errorMsg);
			}
		}
		return groupId;
	}

}
