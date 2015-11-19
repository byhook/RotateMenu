package cn.z.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

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
        this(context,null);
    }

    public RotateGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateGroup(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView(){
        mItemWidth = DimenUtils.getDis(getContext()).widthPixels/6;
        mItemHeight = mItemWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int count = Math.min(9,getChildCount());

        int diameter = r - l;

        if(DEBUG) Log.e(TAG, "" + changed + "/" + l + "/" + t + "/" + r + "/" + b + "//" + diameter);

        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);

            final float temp = i<4?18:15;

            final int k = i < 4 ? i : i - 4;

            final double px = Math.cos(temp * (k + 1) * Math.PI / 180);
            final double py = Math.sin(temp * (k + 1) * Math.PI / 180);

            final int distanceX = i<4?mItemWidth*2: (int) (mItemWidth * 0.8F);
            final int distanceY = i<4?mItemHeight*2: (int) (mItemWidth * 0.8F);

            if(DEBUG) Log.d(TAG,""+diameter+"px="+px+"/py="+py);

            final int left = (int) (diameter - (diameter-distanceX) * (px));
            final int top = (int) (diameter - (diameter-distanceY) * (py));

            child.layout(left, top, left+mItemWidth, top+mItemHeight);

            //child.layout(0, 0, mItemWidth, mItemHeight);
            //child.layout(diameter-px-mItemWidth/2 , diameter-py-mItemHeight/2, diameter-px + mItemWidth/2, diameter-py + mItemHeight/2);
        }
    }

    /**
     * 设置文本适配器
     * @param type
     * @param adapter
     */
    public void setItemAdapter(CenterType type, final BaseItemAdapter adapter){
        this.mType = type;
        int count = adapter.getCount();
        View child = null;
        for(int i=0;i<count;i++){
            child = adapter.getItem(i);
            addView(child);
        }
    }

    /**
     * 获取内距
     * @return
     */
    private int getInPitch(){
        return mDiameter / 3 * 2;
    }

    /**
     * 获取外距
     * @return
     */
    private int getOutPitch(){
        return mDiameter / 3 * 2+mItemWidth*3;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        int count = getChildCount();
        View child;

        for(int i=0;i<count;i++){
            child = getChildAt(i);
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setDuration(500);
            anim.setInterpolator(new DecelerateInterpolator(0.8f));
            child.setAnimation(anim);
        }
//        AnimatorSet anim = new AnimatorSet();
//        ObjectAnimator pageX = ObjectAnimator.ofFloat(child, "scaleX", 0.0F, 1.0F);
//        ObjectAnimator pageY = ObjectAnimator.ofFloat(child, "scaleY", 0.0F, 1.0F);
//        anim.play(pageX);
//        anim.play(pageY);
//        anim.setInterpolator(new DecelerateInterpolator(0.8f));
//        anim.setDuration(300);
//        anim.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int diameter = Math.min(widthMeasureSpec, heightMeasureSpec);
        mDiameter = MeasureSpec.getSize(diameter);

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(diameter, diameter);
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setStrokeWidth(mItemWidth * 3);
//        paint.setAntiAlias(true);
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.parseColor("#50848095"));
//        canvas.drawCircle(mDiameter, mDiameter, mDiameter / 3 * 2, paint);
//
//
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            drawChild(canvas, child, 0);
//        }
//    }
}
