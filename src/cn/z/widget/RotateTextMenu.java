package cn.z.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;

/**
 * Created by byhook on 15-11-16.
 * Mail : byhook@163.com
 */
public class RotateTextMenu extends ViewGroup {

    private static final boolean DEBUG = true;
    private static final String TAG = "RotateTextMenu";

    /**
     * 游标
     */
    private Button mCursor;
    private int mWidth;
    private int mHeight;

    private Button topMenu;
    private Button middleMenu;
    private Button bottomMenu;


    private Button[] mTitles;

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

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();

        if (DEBUG) Log.d(TAG, "onLayout=" + changed + "/" + l + "/" + t + "/" + r + "/" + b + "/" + count);

//        mCursor.layout(0,t,mCursor.getMeasuredWidth(),t+mCursor.getMeasuredHeight());
//
//        topMenu.layout(0,t,mCursor.getMeasuredWidth(),t+mCursor.getMeasuredHeight());
//        middleMenu.layout(0,t,middleMenu.getMeasuredWidth(),t+middleMenu.getMeasuredHeight());
//        bottomMenu.layout(0,t,bottomMenu.getMeasuredWidth(),t+bottomMenu.getMeasuredHeight());

        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            child.layout(0, t, child.getMeasuredWidth(), t + child.getMeasuredHeight());
        }

        //button.layout(l,t,r,b);
    }

    public void setTextAdapter(String[] titles){
        if(titles==null)
            return ;

        topMenu = new Button(getContext());
        middleMenu = new Button(getContext());
        bottomMenu = new Button(getContext());

        mTitles = new Button[titles.length];
        for(int i=0;i<mTitles.length;i++){
            mTitles[i] = new Button(getContext());
            mTitles[i].setText(titles[i]);
            addView(mTitles[i]);
        }
//
//        addView(topMenu);
//        addView(middleMenu);
//        addView(bottomMenu);



        mCursor = new Button(getContext());
        addView(mCursor);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);

                final float temp = 90 / (mTitles.length + 1);
                int index = 0;
                for(Button title:mTitles){
                    title.setPivotX(mWidth / 2);
                    title.setPivotY(title.getMeasuredHeight() / 2);
                    title.setRotation(temp * (1 + index++));
                }
//                mCursor.setPivotX(mWidth / 2);
//                mCursor.setPivotY(mCursor.getMeasuredHeight() / 2);
//                mCursor.setRotation(22.5F);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int diameter = Math.min(widthMeasureSpec, heightMeasureSpec);

        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(diameter, diameter);
    }


    private AnimatorSet getRotate(View child,float rotation){
        AnimatorSet animatorSet = new AnimatorSet();

        //计算坐标中点
        child.setPivotX(mWidth/2);
        child.setPivotY(child.getMeasuredHeight()/2);
        ObjectAnimator pageMiddle = ObjectAnimator.ofFloat(child, "rotation", child.getRotation(), child.getRotation()+rotation);

        animatorSet.play(pageMiddle);
        return animatorSet;
    }

    private int index = 2;
    public void rotateCursor(){
        final float rotation = mTitles[(index++)%3].getRotation();
        final float result = -(mCursor.getRotation()-rotation)%360;
        AnimatorSet anim = getRotate(mCursor,result);
        anim.start();
    }

//    @Override
//    protected void dispatchDraw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);
//        paint.setStrokeJoin(Paint.Join.ROUND);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.parseColor("#A0123456"));
//        canvas.drawCircle(360, 360, 320, paint);
//
//        drawChild(canvas, button, 0);
//    }
}