package com.demo.example.emoji.librate;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class PartialView extends RelativeLayout {
    private ImageView mEmptyView;
    private ImageView mFilledView;

    public PartialView(Context context) {
        super(context);
        init();
    }

    public PartialView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public PartialView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mFilledView = new ImageView(getContext());
        this.mFilledView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.mEmptyView = new ImageView(getContext());
        this.mEmptyView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        addView(this.mFilledView);
        addView(this.mEmptyView);
    }

    public void setFilledDrawable(Drawable drawable) {
        this.mFilledView.setImageDrawable(new ClipDrawable(drawable, 3, 1));
    }

    public void setEmptyDrawable(Drawable drawable) {
        this.mEmptyView.setImageDrawable(new ClipDrawable(drawable, 5, 1));
    }

    public void setFilled() {
        this.mFilledView.setImageLevel(10000);
        this.mEmptyView.setImageLevel(0);
    }

    public void setPartialFilled(float f) {
        int i = (int) (10000.0f * (f % 1.0f));
        if (i == 0) {
            i = 10000;
        }
        this.mFilledView.setImageLevel(i);
        this.mEmptyView.setImageLevel(10000 - i);
    }

    public void setEmpty() {
        this.mFilledView.setImageLevel(0);
        this.mEmptyView.setImageLevel(10000);
    }
}
