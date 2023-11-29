package com.demo.example.emoji.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.emoji.models.EmojiObject;
import com.demo.example.emoji.ultis.Constant;
import java.util.List;


public class BackgroundAdapter extends BaseAdapter {
    private List<EmojiObject> listBackgrounds;
    private Context mContext;
    private int positionSelect;
    private SharedPreferences sharedPreferences;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public BackgroundAdapter(List<EmojiObject> list, Context context, int i, SharedPreferences sharedPreferences) {
        this.listBackgrounds = list;
        this.mContext = context;
        this.positionSelect = i;
        this.sharedPreferences = sharedPreferences;
    }

    @Override 
    public int getCount() {
        return this.listBackgrounds.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.listBackgrounds.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_background_adapter, viewGroup, false);
            viewHolder.imgComponent = (ImageView) view.findViewById(R.id.imgComponent);
            viewHolder.shapeSelect = (ImageView) view.findViewById(R.id.shapeSelect);
            viewHolder.rltEmoji = (RelativeLayout) view.findViewById(R.id.rltEmoji);
            viewHolder.imgLock = (ImageView) view.findViewById(R.id.imgLock);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.sharedPreferences.getBoolean(Constant.RATE_APP, false)) {
            viewHolder.imgLock.setVisibility(View.GONE);
        } else if (this.listBackgrounds.get(i).isLock()) {
            viewHolder.imgLock.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgLock.setVisibility(View.GONE);
        }
        if (i == this.positionSelect) {
            viewHolder.shapeSelect.setVisibility(View.VISIBLE);
        } else {
            viewHolder.shapeSelect.setVisibility(View.INVISIBLE);
        }
        Glide.with(this.mContext).load(this.listBackgrounds.get(i).getLinkEmojiNomal()).into(viewHolder.imgComponent);
        return view;
    }

    
    private class ViewHolder {
        ImageView imgComponent;
        ImageView imgLock;
        RelativeLayout rltEmoji;
        ImageView shapeSelect;

        private ViewHolder() {
        }
    }

    public void positionChange(int i) {
        this.positionSelect = i;
        notifyDataSetChanged();
    }
}
