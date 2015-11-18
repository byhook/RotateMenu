package cn.z.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 * 旋转菜单容器
 * 容纳菜单选项
 */
public class RotateGroup extends ViewGroup {

    private static final boolean DEBUG = true;
    private static final String TAG = "RotateGroup";

    private CenterType mType;

    private int mDiameter;

    public enum CenterType {
        LEFT_TOP,
        RIGHT_TOP,
        LEFT_BOTTOM,
        RIGHT_BOTTOM
    }

    private int mItemWidth = 120;
    private int mItemHeight = 120;

    public RotateGroup(Context context) {
        super(context);
    }

    public RotateGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RotateGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = getChildCount();

        int diameter = r - l;

        if(DEBUG) Log.e(TAG, "" + changed + "/" + l + "/" + t + "/" + r + "/" + b + "//" + diameter);

        int k = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int px=0,py=0;
            if(i<4){
                px = (int) ((diameter - mItemWidth)  * Math.cos(18 * (i + 1) * Math.PI / 180)) - mItemWidth /2;
                py = (int) ((diameter - mItemHeight)  * Math.sin(18 * (i + 1) * Math.PI / 180)) - mItemHeight /2;
            }else if(i<9){
                px = (int) ((diameter - mItemWidth/2)  * Math.cos(15 * (k + 1) * Math.PI / 180));
                py = (int) ((diameter - mItemHeight/2)  * Math.sin(15 * (k + 1) * Math.PI / 180));
                k++;
            }else{
                break;
            }

            if(DEBUG) Log.d(TAG,""+diameter+"px="+px+"/py="+py);

            child.layout(diameter - px , diameter - py, diameter - px + mItemWidth, diameter - py + mItemHeight);
        }
    }

    public void setItemAdapter(CenterType type,BaseItemAdapter adapter){
        this.mType = type;
        int count = adapter.getCount();
        View child = null;
        for(int i=0;i<count;i++){
            child = adapter.getItem(i);
            addView(child);
        }
    }

    public void setItemSize(int width,int height){
        this.mItemWidth = width;
        this.mItemHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int diameter = Math.min(widthMeasureSpec, heightMeasureSpec);
        mDiameter = MeasureSpec.getSize(diameter);

        setMeasuredDimension(diameter, diameter);
    }

}
