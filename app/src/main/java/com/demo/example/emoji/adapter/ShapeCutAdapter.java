package com.demo.example.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.demo.example.R;
import com.demo.example.emoji.ultis.UltilsMethod;
import java.util.List;


public class ShapeCutAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<String> listShape;
    private Context mContext;
    private int positionSelected;
    private UltilsMethod ultilsMethod;

    @Override 
    public long getItemId(int i) {
        return (long) i;
    }

    public ShapeCutAdapter(List<String> list, Context context, int i) {
        this.listShape = list;
        this.mContext = context;
        this.positionSelected = i;
        this.layoutInflater = LayoutInflater.from(context);
        this.ultilsMethod = new UltilsMethod(context);
    }

    @Override 
    public int getCount() {
        return this.listShape.size();
    }

    @Override 
    public Object getItem(int i) {
        return this.listShape.get(i);
    }

    @Override 
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (view == null) {
            view = LayoutInflater.from(this.mContext).inflate(R.layout.item_shape_cut_lv, viewGroup, false);
            viewHolder.imgShapeCut = (ImageView) view.findViewById(R.id.imgShapeCut);
            viewHolder.lnlShapeCut = (LinearLayout) view.findViewById(R.id.lnlShapeCut);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        if (this.positionSelected == i) {
            viewHolder.lnlShapeCut.setBackgroundColor(-1);
        } else {
            viewHolder.lnlShapeCut.setBackgroundColor(-16777216);
        }
        viewHolder.imgShapeCut.setImageBitmap(this.ultilsMethod.getBitmapFromAsset(this.mContext, this.listShape.get(i)));
        return view;
    }

    
    private class ViewHolder {
        ImageView imgShapeCut;
        LinearLayout lnlShapeCut;

        private ViewHolder() {
        }
    }

    public void setPositionChanges(int i) {
        this.positionSelected = i;
        notifyDataSetChanged();
    }
}
