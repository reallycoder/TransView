
package com.smileven.transview.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * author：armyjust on 2016/11/1 22:13
 * email：armyjust@126.com
 */
public class ScreenUtil {

    // 目标模糊半径
    public static final float GBLUR_RADIUS_DEST = 220;

    // 屏幕宽
    public static int widthPixels;

    // 屏幕高
    public static int heightPixels;

    // 屏幕密度
    public static float density;

    // 图片在沟通中显示最大宽度
    public static int maxImageWidth;

    // 图片在沟通中显示最小宽度
    public static int minImageWidth;

    // 图片在沟通中显示最大高度
    public static int maxImageHeight;

    // 图片在沟通中显示最小高度
    public static int minImageHeight;

    public static void init(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        density = dm.density;
        maxImageWidth = widthPixels / 2;
        minImageWidth = widthPixels / 4;
        maxImageHeight = heightPixels / 3;
        minImageHeight = heightPixels / 8;
    }

    /**
     * dp转px
     *
     * @param dpValue dp
     * @return int px
     * @throws
     */
    public static int dp2px(float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * px 转 dp
     *
     * @param pxValue px
     * @return int dp
     * @throws
     */
    public static int px2dp(float pxValue) {
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 获取状态栏高度
     *
     * @return int
     * @throws
     */
    public static int getStatusBarHeight() {
        return Resources.getSystem().getDimensionPixelSize(
                Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android"));
    }

    /**
     * 根据屏幕的高度计算相应的视图高度
     *
     * @param high
     * @return
     */
    public static int getViewHeight(int high) {
        return high * heightPixels / 1920;
    }

    /**
     * 根据屏幕的高度计算相应的视图高度
     *
     * @param width
     * @return
     */
    public static int getViewWidth(int width) {
        return width * widthPixels / 1080;
    }

    public static int[] getScreenInfo() {
        int info[] = {
                widthPixels, heightPixels
        };
        return info;
    }

    public static int getTextSize(int size) {
        return size * heightPixels / 1920;
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

}
