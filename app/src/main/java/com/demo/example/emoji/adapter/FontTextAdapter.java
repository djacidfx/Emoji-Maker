package com.demo.example.emoji.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.example.R;

import java.util.ArrayList;


public class FontTextAdapter extends BaseAdapter {
    private ArrayList<String> arrFonts;
    private Context context;
    private LayoutInflater layoutInflater;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public FontTextAdapter(ArrayList<String> arrayList, Context context) {
        this.arrFonts = arrayList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override 
    public int getCount() {
        return this.arrFonts.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.arrFonts.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.item_fonts, viewGroup, false);
            viewHolder.tvFonts = (TextView) view.findViewById(R.id.tvFonts);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvFonts.setTypeface(Typeface.createFromAsset(this.context.getAssets(), this.arrFonts.get(i)));
        return view;
    }

    
    private class ViewHolder {
        TextView tvFonts;

        private ViewHolder() {
        }
    }
}
