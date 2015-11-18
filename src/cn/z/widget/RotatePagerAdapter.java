package cn.z.widget;

import android.view.View;

/**
 * 作者 : byhook
 * 时间 : 15-11-16.
 * 邮箱 : byhook@163.com
 * 功能 :
 * 旋转页面适配器
 */
public class RotatePagerAdapter {

    private RotateGroup[] mPages;

    public RotatePagerAdapter(RotateGroup[] mPages) {
        this.mPages = mPages;
    }

    /**
     * 获取页面数量
     * @return
     */
    public int getCount() {
        return mPages.length;
    }

    /**
     * 获取旋转页面
     * @param index
     * @return
     */
    public RotateGroup getPage(int index) {
        return mPages[index];
    }
}
