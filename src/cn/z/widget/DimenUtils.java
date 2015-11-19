package cn.z.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 作者 : byhook
 * 时间 : 15-11-19.
 * 邮箱 : byhook@163.com
 * 功能 :
 * 适配屏幕大小
 */
public class DimenUtils {


    public static DisplayMetrics getDis(Context ctx){
        DisplayMetrics dm = ctx.getResources().getDisplayMetrics();

        return dm;
    }



}
