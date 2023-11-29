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


public class EmojiAdapter extends BaseAdapter {
    private List<EmojiObject> listEmoji;
    private Context mContext;
    private String nameShape;
    private int positionSelect;
    private int positionTab;
    private SharedPreferences sharedPreferences;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public EmojiAdapter(List<EmojiObject> list, Context context, String str, int i, int i2, SharedPreferences sharedPreferences) {
        this.listEmoji = list;
        this.mContext = context;
        this.nameShape = str;
        this.positionTab = i;
        this.positionSelect = i2;
        this.sharedPreferences = sharedPreferences;
    }

    @Override 
    public int getCount() {
        return this.listEmoji.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.listEmoji.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_gr_emoji, viewGroup, false);
            viewHolder.imgRoot = (ImageView) view.findViewById(R.id.imgRoot);
            viewHolder.imgComponent = (ImageView) view.findViewById(R.id.imgComponent);
            viewHolder.shapeSelect = (ImageView) view.findViewById(R.id.shapeSelect);
            viewHolder.rltEmoji = (RelativeLayout) view.findViewById(R.id.rltEmoji);
            viewHolder.imgLock = (ImageView) view.findViewById(R.id.imgLock);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.positionTab == 0 || this.positionTab == 1 || this.positionTab == 2) {
            viewHolder.imgRoot.setVisibility(View.GONE);
        } else {
            viewHolder.imgRoot.setVisibility(View.VISIBLE);
            Glide.with(this.mContext).load(this.nameShape).into(viewHolder.imgRoot);
        }
        if (i == this.positionSelect) {
            viewHolder.shapeSelect.setVisibility(View.VISIBLE);
        } else {
            viewHolder.shapeSelect.setVisibility(View.INVISIBLE);
        }
        if (this.sharedPreferences.getBoolean(Constant.RATE_APP, false)) {
            viewHolder.imgLock.setVisibility(View.GONE);
        } else if (this.listEmoji.get(i).isLock()) {
            viewHolder.imgLock.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgLock.setVisibility(View.GONE);
        }
        Glide.with(this.mContext).load(this.listEmoji.get(i).getLinkEmojiNomal()).into(viewHolder.imgComponent);
        return view;
    }

    
    private class ViewHolder {
        ImageView imgComponent;
        ImageView imgLock;
        ImageView imgRoot;
        RelativeLayout rltEmoji;
        ImageView shapeSelect;

        private ViewHolder() {
        }
    }

    public void dataChanges(List<EmojiObject> list, int i, String str, int i2) {
        this.listEmoji = list;
        this.positionTab = i;
        this.nameShape = str;
        this.positionSelect = i2;
        notifyDataSetChanged();
    }

    public void positionChange(int i) {
        this.positionSelect = i;
        notifyDataSetChanged();
    }

    public void rateChanges() {
        notifyDataSetChanged();
    }
}
