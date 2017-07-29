package com.javonlee.sample;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.javonlee.dragpointview.view.AbsDragPointView;
import com.javonlee.dragpointview.view.DragPointView;
import com.javonlee.dragpointview.OnPointDragListener;

import java.util.ArrayList;
import java.util.List;

public class ItemConversationAdapter extends BaseAdapter implements OnPointDragListener {

    private List<ConversationEntity> objects = new ArrayList<>();

    private Context context;
    private LayoutInflater layoutInflater;
    private AnimationDrawable animationDrawable;

    public ItemConversationAdapter(Context context, List<ConversationEntity> objects) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.objects = objects;

        animationDrawable = new AnimationDrawable();
        animationDrawable.addFrame(context.getResources().getDrawable(R.mipmap.explode1), 100);
        animationDrawable.addFrame(context.getResources().getDrawable(R.mipmap.explode2), 100);
        animationDrawable.addFrame(context.getResources().getDrawable(R.mipmap.explode3), 100);
        animationDrawable.addFrame(context.getResources().getDrawable(R.mipmap.explode4), 100);
        animationDrawable.addFrame(context.getResources().getDrawable(R.mipmap.explode5), 100);
        animationDrawable.setOneShot(true);
        animationDrawable.setExitFadeDuration(300);
        animationDrawable.setEnterFadeDuration(100);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public ConversationEntity getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        parent.setClipChildren(false);
        parent.setClipToPadding(false);
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_conversation, null);
            convertView.setTag(new ViewHolder(convertView));
        }
        initializeViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }

    private void initializeViews(ConversationEntity object, ViewHolder holder) {
        Glide.with(context).load(object.getAvatar()).into(holder.avatar);
        holder.message.setText(object.getLastMessage());
        holder.name.setText(object.getNickname());
        holder.time.setText(object.getLastTime());
        holder.pointView.setTag(object);
        holder.pointView.setOnPointDragListener(this);
        holder.pointView.setRemoveAnim(animationDrawable);
        if (object.isRead() || object.getMessageNum() <= 0) {
            holder.pointView.setVisibility(View.GONE);
        } else {
            holder.pointView.setVisibility(View.VISIBLE);
            if (object.getMessageNum() <= 99)
                holder.pointView.setText(object.getMessageNum() + "");
            else
                holder.pointView.setText("99");
        }
    }

    @Override
    public void onRemoveStart(AbsDragPointView view) {

    }

    @Override
    public void onRemoveEnd(AbsDragPointView view) {
        ConversationEntity entity =
                (ConversationEntity) view.getTag();
        entity.setMessageNum(0);
        entity.setRead(true);
        ((SampleActivity)context).updateUnreadCount();
    }

    @Override
    public void onRecovery(AbsDragPointView view) {

    }

    protected class ViewHolder {
        private ImageView avatar;
        private TextView name;
        private TextView time;
        private TextView message;
        private DragPointView pointView;

        public ViewHolder(View view) {
            avatar = (ImageView) view.findViewById(R.id.avatar);
            name = (TextView) view.findViewById(R.id.name);
            time = (TextView) view.findViewById(R.id.time);
            message = (TextView) view.findViewById(R.id.message);
            pointView = (DragPointView) view.findViewById(R.id.drag_point_view);
        }
    }
}
