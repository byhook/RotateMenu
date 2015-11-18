package cn.z.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by byhook on 15-11-13.
 * Mail : byhook@163.com
 * 旋转菜单主界面
 * 包含旋转ITEM
 * 子菜单选项
 */
public class RotateMenu extends ViewGroup implements RotateTextMenu.OnItemClickImpl {

    private static final boolean DEBUG = true;
    private static final String TAG = "RotateMenu";

    private int mWidth;
    private int mHeight;

    /**
     * 页面集合
     */
    private ViewGroup mPages[] = new ViewGroup[3];

    /**
     * 文本菜单项
     */
    private RotateTextMenu mRotateTextMenu;

    /**
     * 当前页面
     */
    private int nowPage = 0;

    public RotateMenu(Context context) {
        this(context, null);
    }

    public RotateMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView(){
        mRotateTextMenu = new RotateTextMenu(getContext());
        addView(mRotateTextMenu);
        mRotateTextMenu.setOnItemClickListener(RotateMenu.this);
        //mRotateTextMenu.setBackgroundColor(Color.parseColor("#50909090"));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (DEBUG) Log.e(TAG, "" + changed + "/" + l + "/" + t + "/" + r + "/" + b + "//"+getMeasuredHeight());
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            if(child instanceof RotateGroup){
                //常规页面
                child.setPivotX(getMeasuredWidth());
                child.setPivotY(getMeasuredWidth());
                child.layout(l, mHeight - mWidth, l + mWidth, mHeight);
            }else{
                //右下角的文本选项卡
                child.layout(r - child.getMeasuredWidth(), b - child.getMeasuredHeight(), r , b);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStrokeWidth(360);
        paint.setAntiAlias(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#50848095"));
        canvas.drawCircle(mWidth, mHeight, 520, paint);

        Paint pt = new Paint();
        pt.setColor(getResources().getColor(android.R.color.holo_blue_dark));
        canvas.drawCircle(getMeasuredWidth(), getMeasuredHeight(), 360, pt);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            drawChild(canvas, child, 0);
        }
    }



    /**
     * 设置页面适配器
     * @param adapter
     * 适配页面数据
     */
    public boolean setPageAdapter(final RotatePagerAdapter adapter) {
        if (null == adapter)
            return false;
        int count = adapter.getCount();
        for(int i=0;i<count;i++){
            mPages[i] = adapter.getPage(i);
            addView(mPages[i]);
        }
        //偏向右边
        mPages[1] .setRotation(90);
        //偏向左边
        mPages[2] .setRotation(-90);
        return true;
    }

    /**
     * 设置文本适配
     * @param adapter
     */
    public void setTextAdapter(RotateTextAdapter adapter){
        mRotateTextMenu.setTextAdapter(adapter);
    }


    /**
     * 旋转菜单
     * @param direction 方向true 逆时针 false 顺时针
     * @param next 待显示页面
     */
    AnimatorSet animSet = null;
    public void nextMenu(boolean direction, int next){
        if(null==animSet || !animSet.isStarted()){
            final float temp = direction?-90.0F:90.0F;
            ObjectAnimator pageRight = ObjectAnimator.ofFloat(mPages[nowPage], "rotation", 0.0F, temp);
            ObjectAnimator pageNext = ObjectAnimator.ofFloat(mPages[next], "rotation", -temp, 0.0F);

            animSet = new AnimatorSet();
            animSet.playTogether(pageRight, pageNext);
            animSet.start();

            mRotateTextMenu.rotateCursor(next);
            nowPage = next;
        }
    }

    /**
     * 下一个菜单
     * 默认逆时针旋转
     */
    public void nextMenu() {
        nextMenu(true, (nowPage + 1) % 3);
    }

    public void prevMenu(){
        nextMenu(false, (nowPage + 1) % 3);
    }

    private float mPointX = 0;
    private boolean rotated;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        System.out.println("MOVE...onTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPointX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if(!rotated){
                    if(ev.getX()-mPointX<-10){
                         rotated = true;
                         nextMenu();
                    }else if(ev.getX()-mPointX>10){
                         rotated = true;
                         prevMenu();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                rotated = false;
                break;
        }
        return true;//super.onTouchEvent(ev);
    }

    @Override
    public void onItemClick(View view, int index) {
        nextMenu(index>nowPage, (index) % 3);
        Toast.makeText(getContext(), "Index=" + index, Toast.LENGTH_SHORT).show();
    }
}
