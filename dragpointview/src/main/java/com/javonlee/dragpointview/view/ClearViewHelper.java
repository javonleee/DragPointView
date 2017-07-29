package com.javonlee.dragpointview.view;

import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijinfeng on 2017/7/24.
 */

public class ClearViewHelper {

    private void ClearViewHelper(){}

    public static ClearViewHelper getInstance(){
        return ClearViewHelperHolder.clearViewHelper;
    }

    private SparseArray<Boolean> clearSigning = new SparseArray<>();

    public void clearPointViewBySign(AbsDragPointView dragPointView, String clearSign) {
        List<AbsDragPointView> list = new ArrayList<>();
        list.add(dragPointView);
        getAllPointViewVisible(dragPointView.getRootView(), list, clearSign);
        if (list.contains(dragPointView))
            list.remove(dragPointView);
        list.add(0, dragPointView);
        for (int i = 0; i < list.size() - 1; i++) {
            list.get(i).setNextRemoveView(list.get(i + 1));
        }
        clearSigning.put(clearSign.hashCode(), true);
        list.get(0).startRemove();
    }

    public void clearSignOver(String clearSign) {
        if (TextUtils.isEmpty(clearSign)) return;
        clearSigning.put(clearSign.hashCode(), false);
    }

    public boolean isClearSigning(String clearSign) {
        if (TextUtils.isEmpty(clearSign)) return false;
        Boolean clear = clearSigning.get(clearSign.hashCode());
        return clear == null ? false : clear.booleanValue();
    }

    private void getAllPointViewVisible(View view, List<AbsDragPointView> list, String clearSign) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View child = ((ViewGroup) view).getChildAt(i);
                getAllPointViewVisible(child, list, clearSign);
            }
        } else if (view instanceof AbsDragPointView) {
            AbsDragPointView v = (AbsDragPointView) view;
            if (v.getVisibility() == View.VISIBLE
                    && clearSign.equals(v.getSign())
                    && !list.contains(view))
                list.add((AbsDragPointView) view);
        }
    }

    private static class ClearViewHelperHolder{
        public static ClearViewHelper clearViewHelper = new ClearViewHelper();
    }

}
