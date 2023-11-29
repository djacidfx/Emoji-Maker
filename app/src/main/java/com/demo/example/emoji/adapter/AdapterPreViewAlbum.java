package com.demo.example.emoji.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.demo.example.R;
import com.demo.example.emoji.item.ItemPhoto;

import java.util.ArrayList;


public class AdapterPreViewAlbum extends RecyclerView.Adapter<AdapterPreViewAlbum.PreviewHolder> {
    private ArrayList<ItemPhoto> arrPhoto;
    private PreViewCallBack callBack;
    private Context context;

    
    public interface PreViewCallBack {
        void callBack(int i);
    }

    public AdapterPreViewAlbum(PreViewCallBack preViewCallBack, ArrayList<ItemPhoto> arrayList, Context context) {
        this.callBack = preViewCallBack;
        this.arrPhoto = arrayList;
        this.context = context;
    }

    public PreviewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new PreviewHolder(LayoutInflater.from(this.context).inflate(R.layout.item_preview_album, viewGroup, false), this.callBack);
    }

    public void onBindViewHolder(PreviewHolder previewHolder, int i) {
        if (this.arrPhoto.get(i).isChoose()) {
            int dimension = (int) this.context.getResources().getDimension(R.dimen.p50dp);
            previewHolder.imShow.setLayoutParams(new LinearLayout.LayoutParams(dimension, dimension));
        } else {
            int dimension2 = (int) this.context.getResources().getDimension(R.dimen.p45dp);
            previewHolder.imShow.setLayoutParams(new LinearLayout.LayoutParams(dimension2, dimension2));
        }
        Glide.with(this.context).load(this.arrPhoto.get(i).getLink()).into(previewHolder.imShow);
    }

    @Override 
    public int getItemCount() {
        return this.arrPhoto.size();
    }

    
    
    public class PreviewHolder extends RecyclerView.ViewHolder {
        private PreViewCallBack callBack;
        private ImageView imShow;

        PreviewHolder(View view, final PreViewCallBack preViewCallBack) {
            super(view);
            this.callBack = preViewCallBack;
            this.imShow = (ImageView) view.findViewById(R.id.im_preview_album);
            this.imShow.setOnClickListener(new View.OnClickListener() { 
                public void onClick(View view2) {
                    preViewCallBack.callBack(PreviewHolder.this.getPosition());
                }
            });
        }
    }
}
