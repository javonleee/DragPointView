package com.javonlee.dragpointview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.javonlee.dragpointview.demo.R;
import com.javonlee.dragpointview.util.MathUtils;

/**
 * Created by Administrator on 2017/7/15.
 */
public class DragPointView extends AbsDragPointView {

    public static final float DEFAULT_CENTER_MIN_RATIO = 0.5f;
    public static final int DEFAULT_RECOVERY_ANIM_DURATION = 200;

    private DragViewHelper dragViewHelper;

    public DragPointView(Context context) {
        super(context);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int flowMaxRadius = Math.min(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mCenterRadius = mCenterRadius == 0 ? flowMaxRadius : Math.min(mCenterRadius, flowMaxRadius);
        mDragRadius = mDragRadius == 0 ? flowMaxRadius : Math.min(mDragRadius, flowMaxRadius);
        mMaxDragLength = mMaxDragLength == 0 ? flowMaxRadius * 10 : mMaxDragLength;
    }

    public DragPointView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragPointView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DragPointView, defStyleAttr, 0);
        mMaxDragLength = array.getDimensionPixelSize(R.styleable.
                DragPointView_maxDragLength, MathUtils.dip2px(context, 0));
        mCenterRadius = array.getDimensionPixelSize(R.styleable.DragPointView_centerCircleRadius, 0);
        mDragRadius = array.getDimensionPixelSize(R.styleable.DragPointView_centerCircleRadius, 0);
        mCenterMinRatio = array.getFloat(R.styleable.DragPointView_centerMinRatio, DEFAULT_CENTER_MIN_RATIO);
        mRecoveryAnimDuration = array.getInt(R.styleable.
                DragPointView_recoveryAnimDuration, DEFAULT_RECOVERY_ANIM_DURATION);
        colorStretching = array.getColor(R.styleable.DragPointView_colorStretching, 0);
        mRecoveryAnimBounce = array.getFloat(R.styleable.DragPointView_recoveryAnimBounce, 0f);
        sign = array.getString(R.styleable.DragPointView_sign);
        clearSign = array.getString(R.styleable.DragPointView_clearSign);
        canDrag = array.getBoolean(R.styleable.DragPointView_canDrag, true);
        init();
    }

    @Override
    public void startRemove() {
        dragViewHelper.startRemove();
    }

    private void init() {
        dragViewHelper = new DragViewHelper(getContext(),this);
    }

    @Override
    public void reset() {

    }

}
