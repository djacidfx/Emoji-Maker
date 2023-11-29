package com.demo.example.emoji.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.emoji.item.ItemModeWallpaper;

import java.util.List;


public class AdapterCreateEmoji extends ArrayAdapter<ItemModeWallpaper> {
    public AdapterCreateEmoji(@NonNull Context context, @LayoutRes int i, @NonNull List<ItemModeWallpaper> list) {
        super(context, i, list);
    }

    @Override 
    @NonNull
    public View getView(int i, @Nullable View view, @NonNull ViewGroup viewGroup) {
        View view2;
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view2 = LayoutInflater.from(getContext()).inflate(R.layout.item_grid_emoji, viewGroup, false);
            viewHolder.imEmoji = (ImageView) view2.findViewById(R.id.imgGridEmoji);
            int i2 = getContext().getResources().getDisplayMetrics().widthPixels / 6;
            viewHolder.imEmoji.setLayoutParams(new LinearLayout.LayoutParams(i2, i2));
            view2.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
            view2 = view;
        }
        ItemModeWallpaper item = getItem(i);
        if (item != null) {
            Glide.with(getContext()).load(item.getLink()).into(viewHolder.imEmoji);
        }
        return view2;
    }

    
    private class ViewHolder {
        ImageView imEmoji;

        private ViewHolder() {
        }
    }
}
