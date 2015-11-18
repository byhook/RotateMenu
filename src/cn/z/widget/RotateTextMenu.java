package cn.z.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

/**
 * 作者 : byhook
 * 时间 : 15-11-16.
 * 邮箱 : byhook@163.com
 * 自定义选项卡文本内容
 * 带转盘滚动效果
 */
public class RotateTextMenu extends ViewGroup implements View.OnClickListener {

    private static final boolean DEBUG = true;
    private static final String TAG = "RotateTextMenu";

    /**
     * 游标
     */
    private View mCursor;


    /**
     * 当前容器宽高
     */
    private int mWidth;
    private int mHeight;


    private View[] mTitles;


    private OnItemClickImpl mOnItemClickImpl;

    public RotateTextMenu(Context context) {
        this(context,null);
    }

    public RotateTextMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateTextMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView(){

    }

    /**
     * 相对坐标
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (DEBUG) Log.d(TAG, "onLayout=" + changed + "/" + l + "/" + t + "/" + r + "/" + b);

        int count = getChildCount();

        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            //child.layout(0,0,r,b);
            child.layout(0, (b-t)-child.getMeasuredHeight()/2, child.getMeasuredWidth(),  (b-t)+child.getMeasuredHeight()/2);
            //child.layout(0, (b-t)/2-child.getMeasuredHeight()/2, 0 + child.getMeasuredWidth(), (b-t)/2 + child.getMeasuredHeight()/2);
        }
    }

    /**
     * 设置文本适配器
     * @param adapter
     */
    float temp = 0;
    public void setTextAdapter(final RotateTextAdapter adapter){  //String[] titles  //RotateTextAdapter adapter
        if(null==adapter)
            return ;

        final int count = adapter.getCount();

        mTitles = new View[adapter.getCount()];
        for(int i=0;i<mTitles.length;i++){
            mTitles[i] = adapter.getView(i);
            mTitles[i].setOnClickListener(this);
            mTitles[i].setTag(i);
            addView(mTitles[i]);
        }

        mCursor = adapter.getCursor();
        mCursor.setBackgroundColor(Color.parseColor("#B0FFFFFF"));
        addView(mCursor);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                temp = (float) 90 / (count+1);

                for(int i=0;i<count;i++){
                    mTitles[i].setPivotX(mWidth / 2);
                    mTitles[i].setPivotY(mCursor.getMeasuredHeight()/2);
                    mTitles[i].setRotation(temp * (1 + i));
                }

                mCursor.setPivotX(mWidth / 2);
                mCursor.setPivotY(mCursor.getMeasuredHeight() / 2);
                mCursor.setRotation(temp);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
//        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);

        //测量子控件
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int diameter = Math.min(widthMeasureSpec, heightMeasureSpec);

        int width = diameter;
        if(getChildCount()>0){
            View child = getChildAt(0);
            width = (int) (child.getMeasuredWidth()*2.5F);
        }
        //测量父容器
        this.mWidth = width*2;
        this.mHeight = width*2;
        setMeasuredDimension(width, width);
    }

    /**
     * 获取旋转动画
     * @param child
     * @param rotation
     * @return
     */
    private AnimatorSet getRotate(View child,float rotation){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator pageMiddle = ObjectAnimator.ofFloat(child, "rotation", child.getRotation(), child.getRotation()+rotation);
        animatorSet.play(pageMiddle);
        return animatorSet;
    }

    /**
     * 旋转游标
     */
    public void rotateCursor(int index){
        final float rotation = mTitles[(index)%3].getRotation();
        final float result = -(mCursor.getRotation()-rotation)%360;
        AnimatorSet anim = getRotate(mCursor, result);
        anim.start();
    }

    /**
     * 监听回调
     * @param mOnItemClickImpl
     */
    public void setOnItemClickListener(OnItemClickImpl mOnItemClickImpl){
        this.mOnItemClickImpl = mOnItemClickImpl;
    }

    /**
     * 单击选项卡选择
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(mOnItemClickImpl!=null){
            mOnItemClickImpl.onItemClick(v, (Integer) v.getTag());
        }
    }

    /**
     * 单击Item选项
     */
    public interface OnItemClickImpl{
        void onItemClick(View view,int index);
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        Paint pt = new Paint();
//        pt.setColor(getResources().getColor(android.R.color.holo_blue_dark));
//        canvas.drawCircle(getMeasuredWidth(), getMeasuredHeight(), 360, pt);
//
//        int count = getChildCount();
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//            drawChild(canvas, child, 0);
//        }
//    }
}