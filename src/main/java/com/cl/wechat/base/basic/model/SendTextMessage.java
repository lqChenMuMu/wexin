package com.cl.wechat.base.basic.model;

/**  
*   
* 项目名称：wechatapi  
* 类名称：SendTextMessage  
* 类描述：回复文本消息  
* 创建人：WQ  
* 创建时间：2014-1-26 下午7:28:49  
* @version       
*/
public class SendTextMessage extends SendBaseMessage{
	 // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
} 
