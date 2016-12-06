package com.edu.gdqy.Tool;

/**
 * Created by HXY on 2016/10/7.
 * 公共变量
 */

public final class PublicVariable {
    public static final String HOMEPAGE_FRAGMENT = "HOMEPAGE_FRAGMENT";//主页界面代号
    public static final String SQUARE_FRAGMENT = "SQUARE_FRAGMENT";//广场界面代号
    public static final String SUBSCRIBER_FRAGMENT = "SUBSCRIBER_FRAGMENT"; //订阅界面代号
    public static final String MY_FRAGMENT = "MY_FRAGMENT"; //我的界面代号

    public static final String SET_ACTION = "SET_ACTION"; //设置界面中广播的Action用于各个界面
    public static final String SET_REVISEDATE="SET_REVISEDATE"; //用户资料修改界面
    public static final String MODIFYPASSWORDFRAGMENT = "MODIFYPASSWORDFRAGMENT"; //修改密码界面

    public static final String MORESETFRAGMENT_ACTION="com.edu.gdqy.Controller.MainView.My.MoreSetFragment";
    public static final String REVISEDATEFRAGMENT_ACTION="com.edu.gdqy.Controller.MainView.My.ReviseDateFragment";
    public static final String LOGINFRAGMENTFRAGMENT_ACTION="com.edu.gdqy.Controller.MainView.My.LoginFragment";

    public static final String SEND_EMAIL_CODE="222";     //注册时获取验证码发过去账号的类型编码
    public static final String SEND_MOBILE_CODE="111";     //注册时获取验证码发过去账号的类型编码

    public static final String LIVE="live";     //进入直播或观看时所需要传递的参数
    public static final String WATCH="watch";

    public static final String SERVER_IP="http://114.215.111.75:8080/jeesite";

    public static boolean isLogin =false ; //是否登录
}
