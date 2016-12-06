package com.edu.gdqy.bean;

/**
 * Created by HXY on 2016/12/1.
 * 注册信息的Bean
 */

public class RegisterInfoBean {
    private String nickName; //昵称
    private String loginName;   //登录名
    private String mobile;  //手机号
    private String email;   //邮箱
    private String code;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
