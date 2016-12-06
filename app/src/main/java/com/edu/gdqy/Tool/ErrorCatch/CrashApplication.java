package com.edu.gdqy.Tool.ErrorCatch;

import android.app.Application;

/**
 * Created by HXY on 2016/11/12.
 */

public class CrashApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
    }
}
