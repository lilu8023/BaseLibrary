package com.lilu.apptool.router;

/**
 * Description:
 *
 * @author lilu0916 on 2021/10/19
 * No one knows this better than me
 */
public class RouterPath {

    /**
     * 基础模块路由
     *
     */
    /**
     * webview页面
     * 页面需要的参数
     * url 需要打开的网址
     * @see com.lilu.appcommon.webview.activity.WebViewActivity
     */
    public static final String ACTIVITY_WEB = "/base/activity_web";
    public static final String WEB_URL = "url";

    /**
     * 首页模块路由
     */
    public static final String ACTIVITY_MAIN = "/main/activity_main";

    public static final String FRAGMENT_MAIN = "/main/fragment_main";


    /**
     * 个人中心模块路由
     */
    public static final String FRAGMENT_MINE = "/mine/fragment_mine";

    /**
     * 问答模块路由
     */
    public static final String FRAGMENT_SOLUTION = "/solution/fragment_solution";

    /**
     * 体系模块路由
     */
    public static final String FRAGMENT_SYSTEM = "/system/fragment_solution";


}
