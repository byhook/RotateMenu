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
            }
            else if(i<9){
                px = (int) ((diameter - mItemWidth/2)  * Math.cos(15 * (k + 1) * Math.PI / 180));
                py = (int) ((diameter - mItemHeight/2)  * Math.sin(15 * (k + 1) * Math.PI / 180));
                k++;
            }
            else{
                break;
            }

            if(DEBUG) Log.d(TAG,""+diameter+"px="+px+"/py="+py);

            //第三象限
            //child.layout(diameter+px+mItemWidth, py-mItemHeight, diameter+px+mItemWidth * 2, py);
            //第一象限
            //child.layout(px-mItemWidth, diameter+py+mItemHeight, px, diameter+py+mItemHeight*2);
            //第二象限
            //child.layout(px-mItemWidth, diameter+py-mItemHeight, px, diameter+py);


            //child.layout(px, diameter+py, px + mItemWidth, diameter+py+mItemHeight);

            switch (mType){
                case RIGHT_TOP:
                    //第三象限
                    //child.layout(diameter+px+mItemWidth, py-mItemHeight, diameter+px+mItemWidth * 2, py);
                    child.layout(diameter - px, py- mItemHeight, diameter - px + mItemWidth, py);
                    break;
                case LEFT_BOTTOM:
                    //第一象限
                    //child.layout(px-mItemWidth, diameter+py+mItemHeight, px, diameter+py+mItemHeight*2);
                    child.layout(px- mItemWidth, diameter - py, px, diameter - py + mItemHeight);
                    break;
                case RIGHT_BOTTOM:
                    //第二象限
                    //child.layout(px-mItemWidth, diameter+py-mItemHeight, px, diameter+py);
                    child.layout(diameter - px , diameter - py, diameter - px + mItemWidth, diameter - py + mItemHeight);
                    break;

            }
        }
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        //canvas.drawColor(Color.YELLOW);
//
//        Paint paint = new Paint();
//        paint.setStrokeWidth(240);
//        paint.setAntiAlias(true);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.parseColor("#50848095"));
//        canvas.drawCircle(mDiameter, mDiameter, 520, paint);
//
//        int count = getChildCount();
//        for(int i=0;i<count;i++){
//            View child = getChildAt(i);
//            drawChild(canvas, child, 0);
//        }
////        drawChild(canvas, mPageMiddle, 0);
////        drawChild(canvas, mPageRight,0);
////        drawChild(canvas, mPageDown,0);
////        drawChild(canvas, rotateTextMenu,0);
//    }

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
