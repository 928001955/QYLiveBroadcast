package com.edu.gdqy.test;

import com.edu.gdqy.Model.ConnectServer;
import com.edu.gdqy.Tool.PublicVariable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by HXY on 2016/12/6.
 *
 */

public class TestUrl {

    private void requestCode() {
        String key[] = new String[]{"recipient", "type"};
        String velue[] = new String[]{"1300235348@qq.com"
                , PublicVariable.SEND_EMAIL_CODE};
        String s = "928001955%40qq.com";
        try {
            String decode = URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ConnectServer cs = new ConnectServer();
        cs.sendParameterToServer(null, key, velue, "/font/user/verifyCode");
    }

    public static void main(String[] args) {
        TestUrl testUrl = new TestUrl();
        testUrl.requestCode();
    }

}
