package com.demo.example.emoji.customview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;


public class RobotoRegularTextView extends AppCompatTextView {
    public RobotoRegularTextView(Context context) {
        super(context);
        setTypeFace(context);
    }

    public RobotoRegularTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeFace(context);
    }

    public RobotoRegularTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeFace(context);
    }

    private void setTypeFace(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Regular.ttf"));
    }
}
