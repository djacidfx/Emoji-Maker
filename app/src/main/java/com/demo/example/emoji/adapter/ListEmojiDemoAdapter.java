package com.demo.example.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.demo.example.R;
import com.demo.example.emoji.models.EmojiObject;
import com.demo.example.emoji.ultis.UltilsMethod;
import java.util.List;


public class ListEmojiDemoAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<EmojiObject> listEmoji;
    private Context mContext;
    private int positionSelected;
    private UltilsMethod ultilsMethod;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public ListEmojiDemoAdapter(List<EmojiObject> list, Context context, int i) {
        this.listEmoji = list;
        this.mContext = context;
        this.positionSelected = i;
        this.layoutInflater = LayoutInflater.from(context);
        this.ultilsMethod = new UltilsMethod(context);
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
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_list_emoji_demo, viewGroup, false);
            viewHolder.imgEmojiDemo = (ImageView) view.findViewById(R.id.imgEmoji);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.positionSelected == i) {
            viewHolder.imgEmojiDemo.setImageBitmap(this.ultilsMethod.getBitmapFromAsset(this.mContext, this.listEmoji.get(i).getLinkEmojiClicked()));
        } else {
            viewHolder.imgEmojiDemo.setImageBitmap(this.ultilsMethod.getBitmapFromAsset(this.mContext, this.listEmoji.get(i).getLinkEmojiNomal()));
        }
        return view;
    }

    
    private class ViewHolder {
        ImageView imgEmojiDemo;

        private ViewHolder() {
        }
    }

    public void setPositionChanges(int i) {
        this.positionSelected = i;
        notifyDataSetChanged();
    }
}
