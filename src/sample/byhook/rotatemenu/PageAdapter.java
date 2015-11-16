package sample.byhook.rotatemenu;

import android.view.ViewGroup;

import cn.z.widget.BasePageAdapter;
import cn.z.widget.RotateGroup;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 */
public class PageAdapter extends BasePageAdapter {

    private ViewGroup[] mPages;

    public PageAdapter(ViewGroup[] mPages){
        this.mPages = mPages;
    }

    @Override
    public int getCount() {
        return mPages.length;
    }

    @Override
    public ViewGroup getItem(int index) {
        return mPages[index];
    }
}
