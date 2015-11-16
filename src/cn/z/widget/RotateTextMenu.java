package cn.z.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 */
public class RotateTextMenu extends ViewGroup {

    public RotateTextMenu(Context context) {
        super(context);
    }

    public RotateTextMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateTextMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
    }
}
