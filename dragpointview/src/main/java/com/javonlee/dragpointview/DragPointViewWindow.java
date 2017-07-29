package com.javonlee.dragpointview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.javonlee.dragpointview.util.ClearViewHelper;
import com.javonlee.dragpointview.util.MathUtils;
import com.javonlee.dragpointview.view.AbsDragPointView;
import com.javonlee.dragpointview.view.DragPointView;

/**
 * Created by lijinfeng on 2017/7/15.
 */
class DragPointViewWindow extends AbsDragPointView implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener {

    private DragPointView origView;
    private Bitmap origBitmap;
    private Paint mPaint;
    private Path mPath;
    protected int mWidthHalf, mHeightHalf;
    private float mRatioRadius;
    private int mMaxRadiusTrebling;
    private boolean isInCircle;
    private float downX, downY;
    private PointF[] mDragTangentPoint;
    private PointF[] mCenterTangentPoint;
    private PointF mCenterCircle;
    private PointF mCenterCircleCopy;
    private PointF mDragCircle;
    private PointF mDragCircleCopy;
    private double mDistanceCircles;
    private PointF mControlPoint;
    private boolean mIsDragOut;
    private ValueAnimator mRecoveryAnim;
    private float origX, origY, upX, upY;

    public void setOrigBitmap(Bitmap origBitmap) {
        this.origBitmap = origBitmap;
    }

    public String getClearSign() {
        return clearSign;
    }

    public DragPointViewWindow setClearSign(String clearSign) {
        this.clearSign = clearSign;
        return this;
    }

    public DragPointViewWindow setCenterRadius(float mCenterRadius) {
        this.mCenterRadius = mCenterRadius;
        return this;
    }

    public DragPointViewWindow setDragRadius(float mDragRadius) {
        this.mDragRadius = mDragRadius;
        return this;
    }

    public DragPointViewWindow(Context context) {
        super(context);
        init();
    }

    public DragPointViewWindow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragPointViewWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterCircle.x = mDragCircle.x = mWidthHalf = getMeasuredWidth() / 2;
        mCenterCircle.y = mDragCircle.y = mHeightHalf = getMeasuredHeight() / 2;
        int flowMaxRadius = Math.min(mWidthHalf, mHeightHalf);
        mMaxRadiusTrebling = flowMaxRadius * 3;
        origX = getX();
        origY = getY();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(18f);
        mPaint.setColor(colorStretching);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mDragTangentPoint = new PointF[2];
        mCenterTangentPoint = new PointF[2];
        mControlPoint = new PointF();
        mCenterCircle = new PointF();
        mCenterCircleCopy = new PointF();
        mDragCircle = new PointF();
        mDragCircleCopy = new PointF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getBackground() != null)
            return;
        drawCenterCircle(canvas);
        if (isInCircle) {
            drawBezierLine(canvas);
            drawOriginBitmap(canvas);
        }
    }

    private void drawOriginBitmap(Canvas canvas) {
        if (origBitmap != null && !origBitmap.isRecycled())
            canvas.drawBitmap(origBitmap, 0, 0, mPaint);
    }

    private void drawCenterCircle(Canvas canvas) {
        if (mIsDragOut || !isInCircle) return;
        mPaint.setColor(colorStretching);
        mRatioRadius = Math.min(mCenterRadius, Math.min(mWidthHalf, mHeightHalf));
        if (isInCircle && Math.abs(mCenterMinRatio) < 1.f) {
            mRatioRadius = (float) (Math.max((mMaxDragLength - mDistanceCircles) * 1.f / mMaxDragLength, Math.abs(mCenterMinRatio)) * mCenterRadius);
            mRatioRadius = Math.min(mRatioRadius, Math.min(mWidthHalf, mHeightHalf));
        }
        canvas.drawCircle(mCenterCircle.x, mCenterCircle.y, mRatioRadius, mPaint);
    }

    public void setOrigView(DragPointView origView) {
        this.origView = origView;
    }

    private void drawBezierLine(Canvas canvas) {
        if (mIsDragOut) return;
        mPaint.setColor(colorStretching);
        float dx = mDragCircle.x - mCenterCircle.x;
        float dy = mDragCircle.y - mCenterCircle.y;
        // 控制点
        mControlPoint.set((mDragCircle.x + mCenterCircle.x) / 2,
                (mDragCircle.y + mCenterCircle.y) / 2);
        // 四个切点
        if (dx != 0) {
            float k1 = dy / dx;
            float k2 = -1 / k1;
            mDragTangentPoint = MathUtils.getIntersectionPoints(
                    mDragCircle.x, mDragCircle.y, mDragRadius, (double) k2);
            mCenterTangentPoint = MathUtils.getIntersectionPoints(
                    mCenterCircle.x, mCenterCircle.y, mRatioRadius, (double) k2);
        } else {
            mDragTangentPoint = MathUtils.getIntersectionPoints(
                    mDragCircle.x, mDragCircle.y, mDragRadius, (double) 0);
            mCenterTangentPoint = MathUtils.getIntersectionPoints(
                    mCenterCircle.x, mCenterCircle.y, mRatioRadius, (double) 0);
        }
        // 路径构建
        mPath.reset();
        mPath.moveTo(mCenterTangentPoint[0].x, mCenterTangentPoint[0].y);
        mPath.quadTo(mControlPoint.x, mControlPoint.y, mDragTangentPoint[0].x, mDragTangentPoint[0].y);
        mPath.lineTo(mDragTangentPoint[1].x, mDragTangentPoint[1].y);
        mPath.quadTo(mControlPoint.x, mControlPoint.y,
                mCenterTangentPoint[1].x, mCenterTangentPoint[1].y);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!canDrag || ClearViewHelper.getInstance().isClearSigning(sign)
                || (mRecoveryAnim != null && mRecoveryAnim.isRunning())
                || (mRemoveAnim != null && mRemoveAnim.isRunning())) {
            return super.onTouchEvent(event);
        }
        if (mRecoveryAnim == null || !mRecoveryAnim.isRunning()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if(getParent() != null)
                    getParent().requestDisallowInterceptTouchEvent(true);
                    downX = event.getRawX();
                    downY = event.getRawY();
                    isInCircle = true;
                    postInvalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float dx = (int) event.getRawX() - downX;
                    float dy = (int) event.getRawY() - downY;
                    mCenterCircle.x = mWidthHalf - dx;
                    mCenterCircle.y = mHeightHalf - dy;
                    mDistanceCircles = MathUtils.getDistance(mCenterCircle, mDragCircle);
                    mIsDragOut = mIsDragOut ? mIsDragOut : mDistanceCircles > mMaxDragLength;
                    setX(origX + dx);
                    setY(origY + dy);
                    postInvalidate();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    getParent().requestDisallowInterceptTouchEvent(false);
                    upX = getX();
                    upY = getY();
                    upAndCancelEvent();
                    break;
            }
        }
        return true;
    }

    private void upAndCancelEvent() {
        if (isInCircle && mDistanceCircles == 0) {
            reset();
            if (mOnPointDragListener != null) {
                mOnPointDragListener.onRecovery(this);
            }
        } else if (!mIsDragOut) {
            mCenterCircleCopy.set(mCenterCircle.x, mCenterCircle.y);
            mDragCircleCopy.set(mDragCircle.x, mDragCircle.y);
            if (mRecoveryAnim == null) {
                mRecoveryAnim = ValueAnimator.ofFloat(1.f, -Math.abs(mRecoveryAnimBounce));
                mRecoveryAnim.setDuration(mRecoveryAnimDuration);
                mRecoveryAnim.addUpdateListener(this);
                mRecoveryAnim.addListener(this);
            }
            if (mRecoveryAnimInterpolator != null)
                mRecoveryAnim.setInterpolator(mRecoveryAnimInterpolator);
            mRecoveryAnim.start();
        } else {
            if (mDistanceCircles <= mMaxRadiusTrebling) {
                reset();
                if (mOnPointDragListener != null) {
                    mOnPointDragListener.onRecovery(this);
                }
            } else if (!TextUtils.isEmpty(clearSign)) {
                ClearViewHelper.getInstance().clearPointViewBySign(origView, clearSign);
            } else {
                startRemove();
            }
        }
    }

    @Override
    public void startRemove() {
        if (mRemoveAnim == null) {
            setVisibility(GONE);
            if (mNextRemoveView != null)
                mNextRemoveView.startRemove();
            if (mOnPointDragListener != null) {
                mOnPointDragListener.onRemoveStart(this);
                mOnPointDragListener.onRemoveEnd(this);
            }
        } else {
            mRemoveAnim.start(mOnPointDragListener);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mRecoveryAnim != null && mRecoveryAnim.isRunning()) {
            mRecoveryAnim.cancel();
        }
        if (mRemoveAnim != null) {
            mRemoveAnim.cancel();
        }
    }

    @Override
    public void reset() {
        mIsDragOut = false;
        isInCircle = false;
        mDragCircle.x = mCenterCircle.x = mWidthHalf;
        mDragCircle.y = mCenterCircle.y = mHeightHalf;
        mDistanceCircles = 0;
        setTranslationX(0);
        setTranslationY(0);
        origX = getX();
        origY = getY();
        postInvalidate();
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float value = (float) valueAnimator.getAnimatedValue();
        float dx = (origX - upX);
        float dy = (origY - upY);
        mCenterCircle.x = dx * value + mWidthHalf;
        mCenterCircle.y = dy * value + mHeightHalf;
        setX(upX + dx * (1 - value));
        setY(upY + dy * (1 - value));
        postInvalidate();
    }

    @Override
    public void onAnimationStart(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {
        reset();
        if (mOnPointDragListener != null) {
            mOnPointDragListener.onRecovery(this);
        }
    }

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }
}
