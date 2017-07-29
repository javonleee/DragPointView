package com.javonlee.dragpointview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.javonlee.dragpointview.OnPointDragListener;

/**
 * 原始View的ouTouch事件与自定义View关联
 * Created by lijinfeng on 2017/07/26.
 */
public class DragViewHelper implements View.OnTouchListener, OnPointDragListener {

    private Context context;
    private FrameLayout container;
    private DragPointView originView;
    private DragPointViewWindow windowView;
    private OnPointDragListener onPointDragListener;
    private Runnable animRunnable;

    private WindowManager windowManager;
    private WindowManager.LayoutParams windowParams;
    private FrameLayout.LayoutParams layoutParams;

    public void startRemove() {
        if (container == null
                || container.getParent() == null)
            addViewToWindow();
        windowView.setNextRemoveView(originView.getNextRemoveView());
        windowView.post(animRunnable);
    }

    public DragViewHelper(Context context, final DragPointView originView) {
        this.context = context;
        this.originView = originView;
        this.originView.setOnTouchListener(this);
        animRunnable = new Runnable() {
            @Override
            public void run() {
                windowView.startRemove();
            }
        };
    }

    private void initParams() {
        windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.LEFT | Gravity.TOP;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        windowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        windowParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams = new FrameLayout.LayoutParams(originView.getWidth(), originView.getHeight());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction() & MotionEvent.ACTION_MASK;
        if (action == MotionEvent.ACTION_DOWN) {
            ViewParent parent = v.getParent();
            if (parent == null) {
                return false;
            }
            parent.requestDisallowInterceptTouchEvent(true);
            addViewToWindow();
        }
        return windowView.onTouchEvent(event);
    }

    public void addViewToWindow() {
        if (windowManager == null) {
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        if (windowView == null) {
            createWindowView();
        }
        if (windowParams == null ||
                layoutParams == null) {
            initParams();
        }
        if (container == null) {
            container = new FrameLayout(context);
            container.setClipChildren(false);
            container.setClipToPadding(false);
            windowView.setLayoutParams(layoutParams);
            container.addView(windowView, layoutParams);
        }
        int[] ps = new int[2];
        originView.getLocationInWindow(ps);
        layoutParams.setMargins(ps[0], ps[1], 0, 0);
        layoutParams.width = originView.getWidth();
        layoutParams.height = originView.getHeight();
        windowView.setOrigView(originView);
        originView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(originView.getDrawingCache());
        originView.setDrawingCacheEnabled(false);
        windowView.setOrigBitmap(bitmap);
        onPointDragListener = originView.getOnPointDragListener();
        windowView.setVisibility(View.VISIBLE);
        windowManager.addView(container, windowParams);
        originView.setVisibility(View.INVISIBLE);
    }

    private void createWindowView() {
        windowView = new DragPointViewWindow(context);
        windowView.setCanDrag(originView.isCanDrag());
        windowView.setCenterMinRatio(originView.getCenterMinRatio());
        windowView.setCenterRadius(originView.getCenterRadius());
        windowView.setColorStretching(originView.getColorStretching());
        windowView.setDragRadius(originView.getDragRadius());
        windowView.setClearSign(originView.getClearSign());
        windowView.setSign(originView.getSign());
        windowView.setMaxDragLength(originView.getMaxDragLength());
        windowView.setRecoveryAnimBounce(originView.getRecoveryAnimBounce());
        windowView.setRecoveryAnimDuration(originView.getRecoveryAnimDuration());
        windowView.setRecoveryAnimInterpolator(originView.getRecoveryAnimInterpolator());
        if (originView.getRemoveAnim() != null)
            windowView.setRemoveAnim(originView.getRemoveAnim().setView(windowView));
        windowView.setOnPointDragListener(this);
    }

    @Override
    public void onRemoveStart(AbsDragPointView view) {
        if (onPointDragListener != null) {
            onPointDragListener.onRemoveStart(originView);
        }
    }

    @Override
    public void onRemoveEnd(AbsDragPointView view) {
        if (windowManager != null && container != null) {
            windowManager.removeView(container);
        }
        if (onPointDragListener != null) {
            onPointDragListener.onRemoveEnd(originView);
        }
        if (originView != null) {
            originView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRecovery(AbsDragPointView view) {
        if (windowManager != null && container != null) {
            windowManager.removeView(container);
        }
        if (originView != null) {
            originView.setVisibility(View.VISIBLE);
        }
        if (onPointDragListener != null) {
            onPointDragListener.onRecovery(originView);
        }
    }
}
