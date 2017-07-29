package com.javonlee.dragpointview.view;

import android.animation.Animator;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.TextView;

import com.javonlee.dragpointview.OnPointDragListener;
import com.javonlee.dragpointview.PointViewAnimObject;

/**
 * Created by lijinfeng on 2017/7/29.
 */

public abstract class AbsDragPointView extends TextView{

    protected float mCenterRadius;
    protected float mDragRadius;
    protected float mCenterMinRatio;
    protected float mRecoveryAnimBounce;
    protected int mMaxDragLength;
    protected int colorStretching;
    protected int mRecoveryAnimDuration;
    protected String sign;
    protected String clearSign;
    protected boolean canDrag;

    protected PointViewAnimObject mRemoveAnim;
    protected Interpolator mRecoveryAnimInterpolator;
    protected OnPointDragListener mOnPointDragListener;
    protected AbsDragPointView mNextRemoveView;

    public AbsDragPointView(Context context) {
        super(context);
    }

    public AbsDragPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbsDragPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PointViewAnimObject getRemoveAnim(){
        return mRemoveAnim;
    }

    public AbsDragPointView setRemoveAnim(PointViewAnimObject removeAnim){
        this.mRemoveAnim = removeAnim;
        return this;
    }

    public AbsDragPointView setRemoveAnim(Animator mRemoveAnim) {
        this.mRemoveAnim = new PointViewAnimObject(mRemoveAnim,this);
        return this;
    }

    public AbsDragPointView setRemoveAnim(AnimationDrawable mRemoveAnim) {
        this.mRemoveAnim = new PointViewAnimObject(mRemoveAnim,this);
        return this;
    }

    public OnPointDragListener getOnPointDragListener() {
        return mOnPointDragListener;
    }

    public String getClearSign() {
        return clearSign;
    }

    public AbsDragPointView setClearSign(String clearSign) {
        this.clearSign = clearSign;
        return this;
    }

    public float getCenterRadius() {
        return mCenterRadius;
    }

    public AbsDragPointView setCenterRadius(float mCenterRadius) {
        this.mCenterRadius = mCenterRadius;
        postInvalidate();
        return this;
    }

    public float getDragRadius() {
        return mDragRadius;
    }

    public AbsDragPointView setDragRadius(float mDragRadius) {
        this.mDragRadius = mDragRadius;
        postInvalidate();
        return this;
    }

    public int getMaxDragLength() {
        return mMaxDragLength;
    }

    public AbsDragPointView setMaxDragLength(int mMaxDragLength) {
        this.mMaxDragLength = mMaxDragLength;
        return this;
    }

    public float getCenterMinRatio() {
        return mCenterMinRatio;
    }

    public AbsDragPointView setCenterMinRatio(float mCenterMinRatio) {
        this.mCenterMinRatio = mCenterMinRatio;
        postInvalidate();
        return this;
    }

    public int getRecoveryAnimDuration() {
        return mRecoveryAnimDuration;
    }

    public AbsDragPointView setRecoveryAnimDuration(int mRecoveryAnimDuration) {
        this.mRecoveryAnimDuration = mRecoveryAnimDuration;
        return this;
    }

    public float getRecoveryAnimBounce() {
        return mRecoveryAnimBounce;
    }

    public AbsDragPointView setRecoveryAnimBounce(float mRecoveryAnimBounce) {
        this.mRecoveryAnimBounce = mRecoveryAnimBounce;
        return this;
    }

    public int getColorStretching() {
        return colorStretching;
    }

    public AbsDragPointView setColorStretching(int colorStretching) {
        this.colorStretching = colorStretching;
        postInvalidate();
        return this;
    }

    public String getSign() {
        return sign;
    }

    public AbsDragPointView setSign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setRecoveryAnimInterpolator(Interpolator mRecoveryAnimInterpolator) {
        this.mRecoveryAnimInterpolator = mRecoveryAnimInterpolator;
    }

    public Interpolator getRecoveryAnimInterpolator() {
        return mRecoveryAnimInterpolator;
    }

    public void clearRemoveAnim() {
        this.mRemoveAnim = null;
    }

    public AbsDragPointView setOnPointDragListener(OnPointDragListener onDragListener) {
        this.mOnPointDragListener = onDragListener;
        return this;
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    public AbsDragPointView setCanDrag(boolean canDrag) {
        this.canDrag = canDrag;
        return this;
    }

    public AbsDragPointView getNextRemoveView() {
        return mNextRemoveView;
    }

    public void setNextRemoveView(AbsDragPointView mNextRemoveView) {
        this.mNextRemoveView = mNextRemoveView;
    }

    public abstract void reset();
    public abstract void startRemove();


}
