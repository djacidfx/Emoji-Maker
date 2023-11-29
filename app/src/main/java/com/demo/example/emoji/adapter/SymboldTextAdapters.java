package com.demo.example.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.demo.example.R;


public class SymboldTextAdapters extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private String nameShape;
    private String[] strSymbol;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public SymboldTextAdapters(String[] strArr, Context context, String str) {
        this.strSymbol = strArr;
        this.mContext = context;
        this.nameShape = str;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override 
    public int getCount() {
        return this.strSymbol.length;
    }

    @Override 
    public Object getItem(int i) {
        return this.strSymbol[i];
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = this.layoutInflater.inflate(R.layout.item_symbol, viewGroup, false);
            viewHolder.tvSymbol = (TextView) view.findViewById(R.id.tvSymbol);
            viewHolder.imgEmoji = (ImageView) view.findViewById(R.id.imgEmoji);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(this.mContext).load(this.nameShape).into(viewHolder.imgEmoji);
        viewHolder.tvSymbol.setText(this.strSymbol[i]);
        return view;
    }

    public void setEmoji(String str) {
        this.nameShape = str;
        notifyDataSetChanged();
    }

    
    private class ViewHolder {
        ImageView imgEmoji;
        TextView tvSymbol;

        private ViewHolder() {
        }
    }

    public void setData(String[] strArr) {
        this.strSymbol = strArr;
        notifyDataSetChanged();
    }
}
