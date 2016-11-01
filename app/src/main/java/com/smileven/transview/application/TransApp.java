package com.smileven.transview.application;

import android.app.Application;

import com.smileven.transview.util.ScreenUtil;

/**
 * author：armyjust on 2016/11/1 22:13
 * email：armyjust@126.com
 */
public class TransApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtil.init(this);
    }
}
