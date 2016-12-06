package com.edu.gdqy.Tool;

import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.edu.gdqy.bean.UserInfoBean;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by HXY on 2016/11/21.
 */

public class Publicmethod {

    //检查是否有网
    public static boolean checkNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        boolean flag = false;
        if (ni != null)
            flag = ni.isAvailable();
        else
            Toast.makeText(context, "网络没有连接", Toast.LENGTH_SHORT).show();
        return flag;
    }

    //1代表获得width 2代表height
    public static int getScreenHeightWidth(int code, Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        Point size = new Point();
        defaultDisplay.getSize(size);
        if (code == 1)
            return size.x;
        else
            return size.y;
    }

    //创建一个带对象JSON
    public static String createJson(String obj, String[] key, String[] velue) {
        String result = "";
        JSONObject object = new JSONObject();//创建一个总的对象，这个对象对整个json串
        try {
            JSONObject jsonObj = new JSONObject();//pet对象，json形式
            for (int i = 0; i < key.length; i++) {
                jsonObj.put(key[i], velue[i]);
            }
            object.put(obj, jsonObj);
            result = object.toString();
            Log.w("TAG", result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    //JSON转换成Map
    public static Map<String, JsonElement> jsonToMap(String code) {
        Map<String, JsonElement> map = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = parser.parse(code).getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> entrySet = jsonObj.entrySet();
        for (Iterator<Map.Entry<String, JsonElement>> iter = entrySet.iterator(); iter.hasNext(); ) {
            Map.Entry<String, JsonElement> entry = iter.next();
            String key = entry.getKey();
            JsonElement value = entry.getValue();
            map.put(key, value);
        }
        return map;
    }

    public static UserInfoBean jsonToObject(String json) {
        Gson gson = new Gson();
        UserInfoBean bean = gson.fromJson(json, UserInfoBean.class);
        Log.w("TAG", bean.getAvatarUrl() + " " + bean.getLoginName() + " " + bean.getNickName() + " " + bean.getStatus());
        return bean;
    }

}
