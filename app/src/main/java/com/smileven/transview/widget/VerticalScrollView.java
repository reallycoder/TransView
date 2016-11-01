package com.smileven.transview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import java.util.NoSuchElementException;

/**
 * author：armyjust on 2016/11/1 22:13
 * email：armyjust@126.com
 */
public class VerticalScrollView extends LinearLayout implements View.OnTouchListener {
    private View mHeaderLayout, mContentLayout;
    private int mTouchSlop;
    private int mTransBoundary;

    private VelocityTracker vTracker;

    private ScrollUpdateTitleListener updateTitleListener;

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setLongClickable(true);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            initData();
        }
    }

    public void initData() {
        int headerId= getResources().getIdentifier("trans_header_layout", "id", getContext().getPackageName());
        int contentId = getResources().getIdentifier("trans_content_layout", "id", getContext().getPackageName());
        if (headerId != 0 && contentId != 0) {
            mHeaderLayout = findViewById(headerId);
            mContentLayout = findViewById(contentId);
            int headerHeight = mHeaderLayout.getMeasuredHeight();
            int contentHeight = mContentLayout.getMeasuredHeight();

            mTransBoundary = headerHeight;
            mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

            contentHeight += headerHeight;
            mContentLayout.getLayoutParams().height = contentHeight;
            mContentLayout.requestLayout();

            contentHeight += headerHeight;
            getLayoutParams().height = contentHeight;
            requestLayout();
        } else {
            throw new NoSuchElementException("Did your view with id \"trans_header_layout\" or \"trans_content_layout\" exists?");
        }
    }


    private float downX = 0, downY = 0;
    private float mLastXIntercept = 0, mLastYIntercept = 0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                mLastXIntercept = downX;
                mLastYIntercept = downY;
                return false;
            case MotionEvent.ACTION_MOVE:
                float deltaX = ev.getX() - mLastXIntercept;
                float deltaY = ev.getY() - mLastYIntercept;
                mLastXIntercept = ev.getX();
                mLastYIntercept = ev.getY();
                if (Math.abs(deltaX) > Math.abs(deltaY) || Math.abs(deltaY) <= mTouchSlop){
                    return false;
                }
                float currentTransY = mHeaderLayout.getTranslationY();
                if (deltaY < 0){ //向上
                    return Math.abs(currentTransY) >= mTransBoundary ? false : true;
                } else { //向下
                    return currentTransY >= 0 ? false : true;
                }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        return false;
    }

    private int mLastY = 0;
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        int eventY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = eventY;
                if (vTracker == null) {
                    vTracker = VelocityTracker.obtain();
                } else {
                    vTracker.clear();
                }
                break;
            case MotionEvent.ACTION_MOVE: {
                if (mLastY != 0) {
                    vTracker.addMovement(event);
                    vTracker.computeCurrentVelocity(1000);
                    int deltaY = eventY - mLastY;
                    mLastY = eventY;
                    setHeaderHeight(deltaY);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mLastY = 0;
                float yVelocity = vTracker.getYVelocity();
                yVelocityUpdate(yVelocity);
                return false;
            }
        }
        return true;
    }

    private void yVelocityUpdate(final float yVelocity){
        Log.e("yVelocity", "yCelocity is ------------------ " + yVelocity);
        ValueAnimator animator = ValueAnimator.ofFloat(yVelocity, 0);
        animator.setInterpolator(new DecelerateInterpolator(3f));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.e("yVelocity", "yCelocity is ------------------ " + animation.getAnimatedValue());
                float animValue = (float) animation.getAnimatedValue();
                float offset = animValue * 16 / Math.abs(yVelocity);
                setHeaderHeight((int) offset);
            }
        });
        animator.setDuration(1000);
        animator.start();
    }

    private void setHeaderHeight(int deltaY) {
        float currentTransY = mHeaderLayout.getTranslationY() + deltaY;
        if (currentTransY < -mTransBoundary){
            currentTransY = -mTransBoundary;
        }
        if (currentTransY > 0){
            currentTransY = 0;
        }
        mHeaderLayout.setTranslationY(currentTransY);
        mContentLayout.setTranslationY(currentTransY);
    }

    public void setScrollUpdateTitleListener(ScrollUpdateTitleListener listener){
        this.updateTitleListener = listener;
    }

    public interface ScrollUpdateTitleListener{
        void updateTitleAlpah(float alpha);
    }

}
