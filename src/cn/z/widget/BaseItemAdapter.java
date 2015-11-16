package cn.z.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 */
public class BaseItemAdapter {

    private Context ctx;

    private List<RotateItemBean> items;

    public BaseItemAdapter(Context ctx,List<RotateItemBean> items) {
        this.ctx = ctx;
        this.items = items;
    }

    public int getCount() {
        return items.size();
    }

    public View getItem(int index) {
        Button bt = new Button(ctx);
        bt.setText("999");
        return bt;
    }

}
