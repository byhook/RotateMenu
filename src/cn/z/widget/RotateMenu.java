package cn.z.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
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

    private ViewGroup mPageMiddle;
    private ViewGroup mPageRight;
    private ViewGroup mPageDown;

    private ViewGroup mPages[] = new ViewGroup[3];

    /**
     * 页面下标
     */
    private int pageIndex = 0;

    public RotateMenu(Context context) {
        this(context,null);
    }

    public RotateMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RotateMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (DEBUG) Log.d(TAG, "onLayout=" + changed + "/" + l + "/" + t + "/" + r + "/" + b+"//"+mWidth+"//"+mHeight);

        mPageMiddle.layout(l, b-r, l + 720, b-r + 720);
        mPageRight.layout(l + 720, b-r, l + 720*2, b-r + 720);
        mPageDown.layout(l, b-r + 720, l + 720, b-r + 720*2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 设置页面适配器
     * @param adapter
     */
    public void setPageAdapter(BasePageAdapter adapter){

        mPageMiddle = adapter.getItem(0);
        addView(mPageMiddle);
        mPages[0] = mPageMiddle;

        mPageRight = adapter.getItem(1);
        addView(mPageRight);
        mPages[1] = mPageRight;

        mPageDown = adapter.getItem(2);
        addView(mPageDown);
        mPages[2] = mPageDown;

    }

    /**
     * 初始化页面的位置
     */
    private void initPagePosition(){
        mPageMiddle.setPivotX(720);
        mPageMiddle.setPivotY(720);
        mPageRight.setPivotX(0);
        mPageRight.setPivotY(720);
        mPageDown.setPivotX(720);
        mPageDown.setPivotY(0);

//        mPageMiddle.setPivotX(360);
//        mPageMiddle.setPivotY(360);
//        mPageRight.setPivotX(360);
//        mPageRight.setPivotY(0);
//        mPageDown.setPivotX(0);
//        mPageDown.setPivotY(360);

        System.out.println("Pivot="+mPageMiddle.getPivotX()+"/"+mPageMiddle.getPivotY());
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

    private float middleStartAngle = 0.0F;
    private float middleEndAngle = -90.F;

    private float rightStartAngle = 0.0F;
    private float rightEndAngle = -90.F;

    private float leftStartAngle = 0.0F;
    private float leftEndAngle = -90.F;

    private LinearLayout mRotationMiddle;
    private LinearLayout mRotationRight;
    private LinearLayout mRotationDown;


    public AnimatorSet getRotateMenu(int index){

        ObjectAnimator pageMiddle = ObjectAnimator.ofFloat(mPages[index%3], "rotation", mPages[index%3].getRotation(), mPages[index%3].getRotation()-90.0F);
        ObjectAnimator pageRight = ObjectAnimator.ofFloat(mPages[(index+1)%3], "rotation", mPages[(index+1)%3].getRotation(), mPages[(index+1)%3].getRotation()-90F);
        ObjectAnimator pageDown = ObjectAnimator.ofFloat(mPages[(index+2)%3], "rotation", mPages[(index+2)%3].getRotation(), mPages[(index+2)%3].getRotation()-180F);




//        Keyframe kf0 = Keyframe.ofFloat(0.0F, 90.0F);
//        Keyframe kf1 = Keyframe.ofFloat(90F, 0.0F);
//        PropertyValuesHolder pvhRotation = PropertyValuesHolder.ofKeyframe("rotation", kf0, kf1);
//
//        ObjectAnimator pageMiddle = ObjectAnimator.ofPropertyValuesHolder(mPageMiddle, pvhRotation);

        animSet.setDuration(1000);
        animSet.playTogether(pageMiddle, pageRight, pageDown);
        return animSet;
    }

    AnimatorSet animSet = new AnimatorSet();

    public void rotateMenu(){
        initPagePosition();

        animSet = getRotateMenu(pageIndex++);

        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                 if(mPageMiddle.getRotation()%360==0){
                      for(ViewGroup layout:mPages){
                          layout.setRotation(0);
                          if (DEBUG) Log.d(TAG,"PAGE RESET");
                      }
                 }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animSet.start();
    }
}
