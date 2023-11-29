package com.demo.example.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.emoji.item.ItemPhoto;

import java.util.List;


public class AlbumAdapter extends BaseAdapter {
    private Context context;
    boolean isDelete;
    private boolean isWallpaper;
    private List<ItemPhoto> objects;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public AlbumAdapter(Context context, List<ItemPhoto> list) {
        this.context = context;
        this.objects = list;
    }

    public void setWallpaper(boolean z) {
        this.isWallpaper = z;
    }

    public boolean isWallpaper() {
        return this.isWallpaper;
    }

    @Override 
    public int getCount() {
        return this.objects.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.objects.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        ItemPhoto itemPhoto = this.objects.get(i);
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.item_album, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.setItemPhoto(itemPhoto);
        if (this.isDelete) {
            view.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.shake));
            viewHolder.imChoose.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imChoose.setVisibility(View.GONE);
            view.clearAnimation();
        }
        return view;
    }

    
    private class ViewHolder {
        ImageView imChoose;
        ImageView imShow;
        ItemPhoto itemPhoto;
        RelativeLayout rlPhoto;

        public ViewHolder(View view) {
            this.imShow = (ImageView) view.findViewById(R.id.im_show);
            this.imChoose = (ImageView) view.findViewById(R.id.im_choose);
            this.rlPhoto = (RelativeLayout) view.findViewById(R.id.rl_item_album);
            int dimension = (int) ((((float) AlbumAdapter.this.context.getResources().getDisplayMetrics().widthPixels) - (AlbumAdapter.this.context.getResources().getDimension(R.dimen.p05dp) * 5.0f)) / 4.0f);
            this.rlPhoto.setLayoutParams(new LinearLayout.LayoutParams(dimension, dimension));
        }

        void setItemPhoto(ItemPhoto itemPhoto) {
            this.itemPhoto = itemPhoto;
            this.imChoose.setVisibility(itemPhoto.isChoose() ? View.VISIBLE : View.GONE);
            Glide.with(AlbumAdapter.this.context).load(itemPhoto.getLink()).into(this.imShow);
        }
    }

    public void setDataChanges(List<ItemPhoto> list) {
        this.objects = list;
        notifyDataSetChanged();
    }

    public boolean isDelete() {
        return this.isDelete;
    }

    public void setDelete(boolean z) {
        this.isDelete = z;
    }
}
