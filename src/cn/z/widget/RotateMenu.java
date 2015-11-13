package cn.z.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by byhook on 15-11-13.
 * Mail : byhook@163.com
 */
public class RotateMenu extends ViewGroup{

    private static final boolean DEBUG = true;
    private static final String TAG = "RotateMenu";

    private int mWidth;
    private int mHeight;

    private TextView mPageMiddle;
    private TextView mPageRight;
    private TextView mPageDown;

    public RotateMenu(Context context) {
        this(context,null);
    }

    public RotateMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView(){
        mPageMiddle = new TextView(getContext());
        mPageMiddle.setText("123");
        mPageMiddle.setBackgroundColor(Color.BLUE);
        addView(mPageMiddle);

        mPageRight = new TextView(getContext());
        mPageRight.setText("456");
        mPageRight.setBackgroundColor(Color.YELLOW);
        addView(mPageRight);

        mPageDown = new TextView(getContext());
        mPageDown.setText("789");
        mPageDown.setBackgroundColor(Color.GRAY);
        addView(mPageDown);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if(DEBUG) Log.d(TAG,"onLayout=" + changed + "/" + l + "/" + t + "/" + r + "/" + b);

        mPageMiddle.layout(l, t, l + 360, t + 360);
        mPageRight.layout(l + 360, t, l + 720, t + 360);
        mPageDown.layout(l, t + 360, l + 360, t + 720);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);

        //measureChildren(widthMeasureSpec,heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 初始化页面的位置
     */
    private void initPagePosition(){
        mPageMiddle.setPivotX(360);
        mPageMiddle.setPivotY(360);
        mPageRight.setPivotX(0);
        mPageRight.setPivotY(360);
        mPageDown.setPivotX(360);
        mPageDown.setPivotY(0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //initMenu();
    }

    private void initMenu(){
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator pageRight = ObjectAnimator.ofFloat(mPageRight, "rotation", 0.0F, 90F);
        ObjectAnimator pageDown = ObjectAnimator.ofFloat(mPageDown, "rotation", 0.0F, -90F);

        animSet.playTogether(pageRight, pageDown);
        animSet.start();
    }


    AnimatorSet animSet = new AnimatorSet();
    public void rotateMenu(){
        initPagePosition();

        ObjectAnimator pageMiddle = ObjectAnimator.ofFloat(mPageMiddle, "rotation", 0.0F, -90F);
        ObjectAnimator pageRight = ObjectAnimator.ofFloat(mPageRight, "rotation", 0.0F, -90F);
        ObjectAnimator pageDown = ObjectAnimator.ofFloat(mPageDown, "rotation", 0.0F, -180F);


//        Keyframe kf0 = Keyframe.ofFloat(0.0F, 90.0F);
//        Keyframe kf1 = Keyframe.ofFloat(90F, 0.0F);
//        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1);
//
//        ObjectAnimator pageMiddle = ObjectAnimator.ofPropertyValuesHolder(mPageMiddle, pvhRotation);

        animSet.setDuration(3000);
        animSet.playTogether(pageMiddle,pageRight,pageDown);   //, pageRight, pageDown
        animSet.start();
    }

}
