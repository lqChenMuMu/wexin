package com.cl.wechat.base.event.model;

/**  
*   
* 项目名称：wechatapi  
* 类名称：MenuEvent  
* 类描述：自定义菜单事件  
* 创建人：WQ  
* 创建时间：2014-3-11 上午11:41:49  
* @version       
*/
public class MenuEvent extends BasicEvent{
	// 事件KEY值，与自定义菜单接口中KEY值对应 
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
}
