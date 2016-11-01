package com.smileven.transview.application;

import android.app.Application;

import com.smileven.transview.util.ScreenUtil;

/**
 * author: 魏军刚
 * email: weijungang@innobuddy.com
 * date: Created on 16/11/1.
 */
public class TransApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ScreenUtil.init(this);
    }
}
