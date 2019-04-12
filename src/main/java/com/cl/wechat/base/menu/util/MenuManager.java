package com.cl.wechat.base.menu.util;

import com.cl.wechat.admin.config.AccessTokenThread;
import com.cl.wechat.base.advanced.model.AccessToken;
import com.cl.wechat.base.menu.model.Button;
import com.cl.wechat.base.menu.model.CommonButton;
import com.cl.wechat.base.menu.model.ComplexButton;
import com.cl.wechat.base.menu.model.Menu;
import com.cl.wechat.base.wechatapi.util.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**  
*   
* 项目名称：wechatapi  
* 类名称：MenuManager  
* 类描述：菜单管理器类   
* 创建人：WQ  
* 创建时间：2014-3-11 下午1:09:25  
* @version       
*/
public class MenuManager {  
    private static Logger log = LoggerFactory.getLogger(MenuManager.class);  
  
    public static void main(String[] args) {  
        // 第三方用户唯一凭证  
        //String appId = "wx04e95443a3ac67e3";
        // 第三方用户唯一凭证密钥  
        //String appSecret = "7ff170c67c641cb40b93935480b8b1c8";
        // 调用接口获取access_token  
        //AccessToken at = CommonUtil.getAccessToken(appId, appSecret);
//        AccessToken at = new AccessToken();
//        at.setAccesstoken("20_Q5ekgTTuTOPdNftvmWSJwcaC1lqp9FTgd8lKkBwaKtXVz4fU_u3wAs4ei-28SnvudAhCYvvYNEYn9Nr3Vd49ZwfWHZXjx9ycGt4BqkHIOvP5GErL_9Ebc79N5evDnGXBeJ-jKVaOH9SE0jmBWPGhACAKDU");
       // AccessToken at = AccessTokenThread.access_token;
//        if (null != at) {
            // 调用接口创建菜单  
            boolean result = MenuUtil.createMenu(getMenu(), "20_M0-yoadlHeKHt54_fR3WtHwSapA4hr1BhVEWle0xaPdKZjOaUrZKU44J8ihE8sqwL66FxXmXX4yC1-p176BHvYS_kXnkY1wr8k-po6oWD-725zpc8Rr7mz0_2_xcw1hzat5fGFkceUQhU-Q0IHDaACAXWS");
            // 判断菜单创建结果
            if (result)  
                log.info("菜单创建成功！ok");  
            else  
                log.info("菜单创建失败，错误码：" + result);  
        }  
//    }
  
    /** 
     * 组装菜单数据 
     *  
     * @return 
     */  
    public static Menu getMenu() {
 
        CommonButton btn21 = new CommonButton();
        btn21.setName("在线贷款");  
        btn21.setType("view");
        btn21.setUrl("http://zzyyf.natapp1.cc/index");

        CommonButton btn22 = new CommonButton();  
        btn22.setName("申请条件");  
        btn22.setType("click");
        btn22.setKey("22");
  
        CommonButton btn23 = new CommonButton();  
        btn23.setName("进度查询");  
        btn23.setType("view");
        btn23.setUrl("http://www.baidu.com/");

        ComplexButton mainBtn1 = new ComplexButton();
        mainBtn1.setName("在线预约");
        mainBtn1.setType("click");
        mainBtn1.setKey("1");

        ComplexButton mainBtn2 = new ComplexButton();  
        mainBtn2.setName("我要贷款");  
        mainBtn2.setSub_button(new CommonButton[] { btn21, btn22, btn23});  
  
        ComplexButton mainBtn3 = new ComplexButton();  
        mainBtn3.setName("微公益");
        mainBtn3.setType("click");  
        mainBtn3.setKey("3+"); 
        /** 
         * 每个一级菜单都有二级菜单项 
         * 在某个一级菜单下没有二级菜单的情况，menu该如何定义呢？
         * 比如，第三个一级菜单项不是“更多体验”，而直接是“幽默笑话”，那么menu应该这样定义：
         * menu.setButton(new Button[] { mainBtn1, mainBtn2, btn33 });
         */  
        Menu menu = new Menu();  
        menu.setButton(new Button[] { mainBtn1, mainBtn2,mainBtn3});
        return menu;  
    }  
}  
