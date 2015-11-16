package cn.z.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

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

    public enum CenterType {
        LEFT_TOP,
        RIGHT_TOP,
        LEFT_BOTTOM,
        RIGHT_BOTTOM
    }

    private int mWidth = 120;
    private int mHeight = 120;

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

        if(DEBUG) Log.d(TAG,""+changed+"/"+l+"/"+t+"/"+r+"/"+b+"//"+diameter);

        int k = 0;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int px,py;
            if(i<4){
                px = (int) ((diameter - mWidth)  * Math.cos(18 * (i + 1) * Math.PI / 180))-mWidth/2;
                py = (int) ((diameter - mHeight)  * Math.sin(18 * (i + 1) * Math.PI / 180))-mHeight/2;

                //child.layout(px-mWidth, py, px, py + mHeight);


            }else{
                px = (int) (diameter  * Math.cos(15 * (k + 1) * Math.PI / 180));
                py = (int) (diameter  * Math.sin(15 * (k + 1) * Math.PI / 180));

                //child.layout(px-mWidth, py, px, py + mHeight);
                //child.layout(diameter - px , diameter - py, diameter - px + mWidth, diameter - py + mHeight);

                k++;
            }

            switch (mType){
                case RIGHT_TOP:
                    child.layout(diameter - px, py-mHeight, diameter - px + mWidth, py);
                    break;
                case LEFT_BOTTOM:
                    child.layout(px-mWidth, diameter - py, px, diameter - py + mHeight);
                    break;
                case RIGHT_BOTTOM:
                    child.layout(diameter - px , diameter - py, diameter - px + mWidth, diameter - py + mHeight);
                    break;
            }
        }
    }


    public void setItemAdapter(CenterType type,BaseItemAdapter adapter){
        this.mType = type;

        float rotation = 0;
        switch (type){
            case RIGHT_TOP:
                rotation = -90;
                break;
            case LEFT_BOTTOM:
                rotation = 90;
                break;
        }

        int count = adapter.getCount();
        View child = null;
        for(int i=0;i<count;i++){
            child = adapter.getItem(i);
            child.setRotation(rotation);
            addView(child);
        }
    }

    public void setItemSize(int width,int height){
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int diameter = Math.min(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(diameter, diameter);

    }

}
