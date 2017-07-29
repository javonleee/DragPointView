package com.javonlee.dragpointview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.javonlee.dragpointview.view.ClearViewHelper;
import com.javonlee.dragpointview.view.AbsDragPointView;

/**
 * Created by lijinfeng on 2017/7/24.
 */

public class PointViewAnimObject {

    private Object object;
    private AbsDragPointView view;
    private Drawable background;

    public PointViewAnimObject setView(AbsDragPointView view) {
        this.view = view;
        return this;
    }

    public PointViewAnimObject(Object object, AbsDragPointView view) {
        this.object = object;
        this.view = view;
    }

    public void start(OnPointDragListener removeListener) {
        if (object == null)
            throw new RuntimeException("remove anim is null.");
        if (removeListener != null)
            removeListener.onRemoveStart(view);
        view.setPivotX(view.getWidth() / 2);
        view.setPivotY(view.getHeight() / 2);
        if (object instanceof AnimationDrawable) {
            background = view.getBackground();
            start((AnimationDrawable) object, removeListener);
        } else if (object instanceof Animator) {
            start((Animator) object, removeListener);
        } else if (object instanceof Animation) {
            start((Animation) object, removeListener);
        }
    }

    private void start(AnimationDrawable object, final OnPointDragListener removeListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            int duration = 0;
            for (int i = 0; i < object.getNumberOfFrames(); i++) {
                duration += object.getDuration(i);
            }
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        view.setBackground(background);
                    }
                    end(removeListener);
                }
            }, duration + 5);
            view.setText("");
            int drawableL = (view.getWidth() + view.getHeight()) / 2;
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.height = lp.width = drawableL;
            view.setLayoutParams(lp);
            view.setBackground(object);
            if (object.isRunning())
                object.stop();
            object.start();
        } else {
            end(removeListener);
        }
    }

    private void start(Animator object, final OnPointDragListener removeListener) {
        view.setVisibility(View.VISIBLE);
        Animator copy = object.clone();
        copy.setTarget(view);
        copy.removeAllListeners();
        copy.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                animation.removeListener(this);
                end(removeListener);
            }
        });
        copy.start();
    }

    private void start(Animation object, final OnPointDragListener removeListener) {
        long duration = object.getDuration();
        object.cancel();
        view.startAnimation(object);
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.clearAnimation();
                end(removeListener);
            }
        }, duration);
    }

    private void end(OnPointDragListener listener) {
        view.setVisibility(View.INVISIBLE);
        view.reset();
        if (listener != null)
            listener.onRemoveEnd(view);
        AbsDragPointView nextRemoveView = view.getNextRemoveView();
        if (nextRemoveView != null) {
            view.setNextRemoveView(null);
            nextRemoveView.startRemove();
        } else {
            ClearViewHelper.getInstance().clearSignOver(view.getSign());
        }
    }

    public void cancel() {
        if (object == null)
            throw new RuntimeException("remove anim is null.");
        if (object instanceof AnimationDrawable) {
            ((AnimationDrawable) object).stop();
        } else if (object instanceof Animator) {
            ((Animator) object).cancel();
        } else if (object instanceof Animation) {
            ((Animation) object).cancel();
        }
    }

    public boolean isRunning() {
        if (object == null)
            return false;
        if (object instanceof AnimationDrawable) {
            return ((AnimationDrawable) object).isRunning();
        } else if (object instanceof Animator) {
            return ((Animator) object).isRunning();
        } else if (object instanceof Animation) {
            if (((Animation) object).hasStarted()) {
                return !((Animation) object).hasEnded();
            } else {
                return false;
            }
        }
        return false;
    }
}
