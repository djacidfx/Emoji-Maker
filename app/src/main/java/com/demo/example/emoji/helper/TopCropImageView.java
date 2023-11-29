package com.demo.example.emoji.helper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;


public class TopCropImageView extends ImageView {
    public TopCropImageView(Context context) {
        super(context);
        init();
    }

    public TopCropImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TopCropImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @TargetApi(21)
    public TopCropImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    @Override 
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        recomputeImgMatrix();
    }

    @Override 
    protected boolean setFrame(int i, int i2, int i3, int i4) {
        recomputeImgMatrix();
        return super.setFrame(i, i2, i3, i4);
    }

    private void init() {
        setScaleType(ScaleType.MATRIX);
    }

    private void recomputeImgMatrix() {
        Drawable drawable = getDrawable();
        if (drawable != null) {
            Matrix imageMatrix = getImageMatrix();
            int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
            int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            int intrinsicHeight = drawable.getIntrinsicHeight();
            float f = intrinsicWidth * height > intrinsicHeight * width ? ((float) height) / ((float) intrinsicHeight) : ((float) width) / ((float) intrinsicWidth);
            imageMatrix.setScale(f, f);
            setImageMatrix(imageMatrix);
        }
    }
}
