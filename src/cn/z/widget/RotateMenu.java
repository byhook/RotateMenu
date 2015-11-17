package cn.z.widget;

import android.animation.Animator;
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
import android.widget.Button;
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

    private ViewGroup mPageMiddle;
    private ViewGroup mPageRight;
    private ViewGroup mPageDown;

    private ViewGroup mPages[] = new ViewGroup[3];

    /**
     * 页面下标
     */
    private int pageIndex = 2;

    private RotateTextMenu mRotateTextMenu;

    private Button topMenu;

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

        if(DEBUG) Log.e(TAG, "" + changed + "/" + l + "/" + t + "/" + r + "/" + b + "//");

        mPageMiddle.layout(l, mHeight-mWidth, l + mWidth, mHeight);
        mPageRight.layout(l + mWidth, mHeight-mWidth, l + mWidth*2, mHeight);
        mPageDown.layout(l, mHeight, l + mWidth, mHeight+mWidth);

        //mRotateTextMenu.layout(l, mHeight-mWidth, l + mWidth, mHeight);
        mRotateTextMenu.layout(r-320, b-320, r-320 + mWidth, b-320 + mWidth);
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
        canvas.drawCircle(mWidth, mHeight, 360, pt);

        int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            drawChild(canvas, child, 0);
        }
//        drawChild(canvas, mPageMiddle, 0);
//        drawChild(canvas, mPageRight,0);
//        drawChild(canvas, mPageDown,0);
//        drawChild(canvas, mRotateTextMenu,0);
    }

    /**
     * 设置页面适配器
     * @param adapter
     */
    public boolean setPageAdapter(BasePageAdapter adapter){
        if(null==adapter)
            return false;
//        int count = adapter.getCount();
//        for(int i=0;i<count;i++){
//            mPages[i] = adapter.getItem(i);
//            addView(mPages[i]);
//        }


      mPageMiddle = adapter.getItem(0);
      addView(mPageMiddle);
      mPages[0] = mPageMiddle;

      mPageRight = adapter.getItem(1);
      addView(mPageRight);
      mPages[1] = mPageRight;

      mPageDown = adapter.getItem(2);
      addView(mPageDown);
      mPages[2] = mPageDown;


        String[] titles = {"常用","打开","工具"};
        mRotateTextMenu = new RotateTextMenu(getContext());
        mRotateTextMenu.setTextAdapter(titles);
        addView(mRotateTextMenu);
        mRotateTextMenu.setOnItemClickListener(this);
        return true;
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

        System.out.println("Pivot=" + mPageMiddle.getPivotX() + "/" + mPageMiddle.getPivotY());
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


    /**
     * 转轮旋转
     * @param index
     * @return
     */
    public AnimatorSet getRotateMenu(int index){
        for(int i=0;i<mPages.length;i++){
            ViewGroup page = mPages[i];
            //90度或者180度
            final float temp = i==(index%3)?180.0F:90.F;
            ObjectAnimator pageMiddle = ObjectAnimator.ofFloat(page, "rotation", page.getRotation(), page.getRotation()-temp);
            animSet.play(pageMiddle);
        }
        return animSet;
    }

    /**
     * 设置当前页面
     * @param index
     */
    public void setCurrentPage(int index){

    }

    AnimatorSet animSet = new AnimatorSet();
    private int index = 2;
    public void rotateMenu(){
        initPagePosition();

        if(!animSet.isStarted()){
            animSet = getRotateMenu(pageIndex++);

            animSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (mPageMiddle.getRotation() % 360 == 0) {
                        for (ViewGroup layout : mPages) {
                            layout.setRotation(0);
                            if (DEBUG) Log.d(TAG, "PAGE RESET");
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
            mRotateTextMenu.rotateCursor(index++);
        }
    }

    @Override
    public void onItemClick(View view, int index) {
        Toast.makeText(getContext(), "Index="+index, Toast.LENGTH_SHORT).show();
    }
}
