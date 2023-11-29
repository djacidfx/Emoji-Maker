package com.demo.example.emoji.ultis;

import android.content.Context;
import android.graphics.Typeface;

import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;


public class TextDialog extends AppCompatTextView {
    public TextDialog(Context context) {
        super(context);
        setTypeFace(context);
    }

    public TextDialog(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeFace(context);
    }

    public TextDialog(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeFace(context);
    }

    private void setTypeFace(Context context) {
        setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/MyriadPro-Semibold.otf"));
    }
}
