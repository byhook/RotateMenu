package cn.z.widget;

import android.view.ViewGroup;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 */
public abstract class BasePageAdapter {

    public abstract int getCount();
    public abstract ViewGroup getItem(int index);
}
