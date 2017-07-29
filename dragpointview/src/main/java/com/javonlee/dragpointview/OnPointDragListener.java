package com.javonlee.dragpointview;

import com.javonlee.dragpointview.view.AbsDragPointView;

/**
 * Created by lijinfeng on 2017/7/29.
 */

public interface OnPointDragListener {
    void onRemoveStart(AbsDragPointView view);

    void onRemoveEnd(AbsDragPointView view);

    void onRecovery(AbsDragPointView view);
}
