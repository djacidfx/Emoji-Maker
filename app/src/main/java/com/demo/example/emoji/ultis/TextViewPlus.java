package com.demo.example.emoji.ultis;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import com.demo.example.R;


public class TextViewPlus extends TextView {
    public TextViewPlus(Context context) {
        super(context);
    }

    public TextViewPlus(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setCustomFont(context, attributeSet);
    }

    public TextViewPlus(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setCustomFont(context, attributeSet);
    }

    private void setCustomFont(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.TextViewPlus);
        setCustomFont(context, obtainStyledAttributes.getString(R.styleable.TextViewPlus_txt_font));
        obtainStyledAttributes.recycle();
    }

    public boolean setCustomFont(Context context, String str) {
        try {
            AssetManager assets = context.getAssets();
            setTypeface(Typeface.createFromAsset(assets, "fonts/" + str));
            return true;
        } catch (Exception e) {
            Log.e("AAAA", "Unable to load typeface: " + e.getMessage());
            return false;
        }
    }
}
