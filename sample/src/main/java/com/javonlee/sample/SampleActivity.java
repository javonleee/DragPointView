package com.javonlee.sample;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.alibaba.fastjson.JSONArray;
import com.javonlee.dragpointview.OnPointDragListener;
import com.javonlee.dragpointview.view.AbsDragPointView;
import com.javonlee.dragpointview.view.DragPointView;

import java.util.List;

/**
 * Created by lijinfeng on 2017/7/25.
 */

public class SampleActivity extends AppCompatActivity implements OnPointDragListener {

    public List<ConversationEntity> conversationEntities;

    private DragPointView pointView1;
    private DragPointView pointView2;
    private DragPointView pointView3;
    private ListView listView;

    private AnimationDrawable animationDrawable;
    private AnimatorSet animatorSet;
    private ObjectAnimator objectAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        init();
    }

    private void init() {
        initView();
        initAnim();
        initSetting();
        initList();
    }

    private void initView() {
        pointView1 = (DragPointView) findViewById(R.id.drag_point_view1);
        pointView2 = (DragPointView) findViewById(R.id.drag_point_view2);
        pointView3 = (DragPointView) findViewById(R.id.drag_point_view3);
        listView = (ListView) findViewById(R.id.list);
    }

    private void initAnim() {

        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.explode1), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.explode2), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.explode3), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.explode4), 100);
        animationDrawable.addFrame(getResources().getDrawable(R.mipmap.explode5), 100);
        animationDrawable.setOneShot(true);
        animationDrawable.setExitFadeDuration(300);
        animationDrawable.setEnterFadeDuration(100);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(null, "scaleX", 1.f, 0.f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(null, "scaleY", 1.f, 0.f);
        animatorSet = new AnimatorSet();
        animatorSet.setDuration(300l);
        animatorSet.playTogether(objectAnimator1, objectAnimator2);

        objectAnimator = ObjectAnimator.ofFloat(null, "alpha", 1.f, 0.f);
        objectAnimator.setDuration(2000l);
    }

    private void initSetting() {
        pointView1.setRemoveAnim(animationDrawable);
        pointView2.setRemoveAnim(objectAnimator);
        pointView3.setRemoveAnim(animatorSet);
        pointView1.setOnPointDragListener(this);
    }

    private void initList() {
        conversationEntities = JSONArray.parseArray(ConversationEntity.TEST_JSON, ConversationEntity.class);
        listView.setAdapter(new ItemConversationAdapter(this, conversationEntities));
        updateUnreadCount();
    }

    public void updateUnreadCount() {
        int totalUnreadCount = 0;
        for (ConversationEntity entity : conversationEntities) {
            totalUnreadCount += entity.getMessageNum();
        }
        if (totalUnreadCount > 0) {
            pointView1.setVisibility(View.VISIBLE);
            if (totalUnreadCount <= 99) {
                pointView1.setText(totalUnreadCount + "");
            } else {
                pointView1.setText("99+");
            }
        } else {
            pointView1.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRemoveStart(AbsDragPointView view) {
        for (ConversationEntity entity : conversationEntities) {
            entity.setRead(true);
            entity.setMessageNum(0);
        }
    }

    @Override
    public void onRemoveEnd(AbsDragPointView view) {

    }

    @Override
    public void onRecovery(AbsDragPointView view) {

    }
}
