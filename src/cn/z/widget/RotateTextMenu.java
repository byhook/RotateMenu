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

import sample.byhook.rotatemenu.R;

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

        int top = b-t;
        int left = r-l;

        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            final int childWidth = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();

            child.setPivotX(mWidth);
            child.setPivotY(child.getMeasuredHeight() / 2);

            if(child.getTag()!=null){
                //普通选项
                //设置旋转方向
                child.setRotation(temp * (i + 1));
            }else{
                //移动游标
                child.setMinimumWidth(mTitles[0].getMeasuredWidth());
                child.setMinimumHeight(mTitles[0].getMeasuredHeight());
                child.setRotation(temp);
            }

            System.out.println("#Z===" + child.getTag());
            child.layout(0, top-childHeight/2, childWidth,top+childHeight/2);

        }
    }

    /**
     * 设置文本适配器
     * @param adapter
     */
    float temp = 0;
    int itemCount = 0;
    public void setTextAdapter(final RotateTextAdapter adapter){  //String[] titles  //RotateTextAdapter adapter
        if(null==adapter)
            return ;

        itemCount = adapter.getCount();

        mTitles = new View[adapter.getCount()];
        for(int i=0;i<mTitles.length;i++){
            mTitles[i] = adapter.getView(i);
            mTitles[i].setOnClickListener(this);
            mTitles[i].setTag(i);
            addView(mTitles[i]);
        }

        temp = (float) 90 / (itemCount+1);

        mCursor = adapter.getCursor();
        mCursor.setBackgroundResource(R.drawable.text_cursor_shape);
        addView(mCursor);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测量子控件
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int diameter = Math.min(widthMeasureSpec, heightMeasureSpec);

        //测量父容器
        this.mWidth = DimenUtils.getDis(getContext()).widthPixels/12*5;
        this.mHeight = mWidth;
        setMeasuredDimension(mWidth, mWidth);
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
    public void rotateCursor(boolean direction,int index){
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