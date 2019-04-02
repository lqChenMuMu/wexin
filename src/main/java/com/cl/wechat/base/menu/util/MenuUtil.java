package com.cl.wechat.base.menu.util;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.cl.wechat.base.menu.model.Menu;
import com.cl.wechat.base.wechatapi.util.CommonUtil;

public class MenuUtil extends CommonUtil {
	/** 
     * 1.创建菜单 
     * @param menu 菜单实例 
     * @param accessToken 有效的access_token 
     * @return true|false
     */  
    public static boolean createMenu(Menu menu, String accessToken) {
        boolean result = false;       
        // 拼装创建菜单的url  
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);  
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSONUtil.parse(menu).toString();
        // 调用接口创建菜单  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);
        if (null != jsonObject) {
        	int errorCode=jsonObject.getInt("errcode");
			String errorMsg=jsonObject.getStr("errmsg");
            if (0 == errorCode) {  
            	result=true;
            }else {
            	result=false;
            	log.error("创建菜单失败 errcode:{} errmsg:{}", errorCode,errorMsg); 
			}  
        }        
        return result;  
    }  
    
    /**
     * 2.查询菜单
     * @param accessToken 有效的access_token 
     * @return String
     */
    public static String getMenu(String accessToken) {
		String result=null;
		String requestUrl=GET_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求查询菜单
		JSONObject jsonObject=httpRequest(requestUrl, "GET", null);
		if (null !=jsonObject) {
			result=jsonObject.toString();
		}
		return result;
	}
    
    /**
     * 删除菜单
     * @param accessToken 有效的access_token 
     * @return true|false
     */
    public static boolean deleteMenu(String accessToken) {
		boolean result=false;
		String requestUrl=DELETE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		// 发起GET请求删除菜单
		JSONObject jsonObject=httpRequest(requestUrl, "GET", null);
		if (null!=jsonObject) {
			int errorCode=jsonObject.getInt("errcode");
			String errorMsg=jsonObject.getStr("errmsg");
            if (0 == errorCode) {  
            	result=true;
            }else {
            	result=false;
            	log.error("删除菜单失败 errcode:{} errmsg:{}", errorCode,errorMsg); 
			}  
		}
		return result;
	}
}
