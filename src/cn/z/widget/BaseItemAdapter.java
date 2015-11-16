package cn.z.widget;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sample.byhook.rotatemenu.R;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 */
public class BaseItemAdapter {

    private Context ctx;

    private List<ApplicationInfo> items;

    private LayoutInflater inflater;

    private PackageManager pm;

    public BaseItemAdapter(Context ctx,List<ApplicationInfo> items) {
        this.ctx = ctx;
        this.items = items;
        this.inflater = LayoutInflater.from(ctx);
        this.pm = ctx.getPackageManager();
    }

    public int getCount() {
        return items.size();
    }

    public View getItem(int index) {

        TextView child = (TextView) inflater.inflate(R.layout.rotate_item,null);

        Drawable drawable = items.get(index).loadIcon(pm);
        drawable.setBounds(0,0,81,81);
        child.setCompoundDrawables(null, drawable, null, null);
        child.setText(items.get(index).loadLabel(pm));
        return child;
    }

}
