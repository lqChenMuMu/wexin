package com.cl.wechat.base.advanced.util;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cl.wechat.base.advanced.model.WeixinUserList;
import com.cl.wechat.base.wechatapi.util.CommonUtil;

import java.awt.List;



/**  
*   
* 项目名称：wechatapi  
* 类名称：GetUserList  
* 类描述：获取关注者列表 
* 创建人：WQ  
* 创建时间：2014-3-8 下午1:06:21  
* @version       
*/
public class GetUserList extends CommonUtil {
	/**
	 * 获取关注者列表
	 * 
	 * @param accessToken 调用接口凭证
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return WeixinUserList
	 */
	@SuppressWarnings({ "deprecation", "unchecked" })
	public static WeixinUserList getUserList(String accessToken, String nextOpenId) {
		WeixinUserList weixinUserList=null;
		if (null==nextOpenId){ 
			nextOpenId="";
		}
		String requestUrl=GET_USERLIST_URL.replace("ACCESS_TOKEN", accessToken).replace("NEXT_OPENID", nextOpenId);
		// 获取粉丝列表
		JSONObject jsonObject=httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null!=jsonObject) {
			try {
				weixinUserList=new WeixinUserList();
				weixinUserList.setTotal(jsonObject.getInt("total"));
				weixinUserList.setCount(jsonObject.getInt("count"));
				weixinUserList.setNextOpenId(jsonObject.getStr("next_openid"));
				JSONObject dataObject=(JSONObject)jsonObject.get("data");
				weixinUserList.setOpenIdList(JSONUtil.toList
						(dataObject.getJSONArray("openid"),String.class));
			} catch (JSONException e) {
				weixinUserList=null;
				int errorCode=jsonObject.getInt("errcode");
				String errorMsg=jsonObject.getStr("errmsg");
				log.error("获取关注者列表失败 errcode:{} errmsg:{} ", errorCode, errorMsg);  
			}
		}
		return weixinUserList;
	}
	
	public static void main(String[] args) {
		// 获取接口访问凭证
		/*String accessToken=GetAccesstoken.getAccessToken("wx13c0a227486f7e64", "864e16284d38c05c62cddc1be000351e").getAccesstoken();*/
		String accessToken=getAccessToken("wx1ac53e0aacec764c", "18aca3be707291b48535b6598d945b59").getAccesstoken();
		WeixinUserList weixinUserList=getUserList(accessToken, "");
		System.err.println("total:"+weixinUserList.getTotal());
		System.err.println("count:"+weixinUserList.getCount());
		System.err.println("openid:"+weixinUserList.getOpenIdList());
		System.err.println("next_openid:"+weixinUserList.getNextOpenId());
	}
}
