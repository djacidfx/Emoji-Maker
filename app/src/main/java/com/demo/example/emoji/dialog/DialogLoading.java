package com.demo.example.emoji.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.demo.example.R;
import com.demo.example.emoji.customview.RobotoRegularTextView;


public class DialogLoading extends Dialog {
    private String text;

    public DialogLoading(@NonNull Context context, String str) {
        super(context);
        this.text = str;
    }

    @Override 
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        setContentView(R.layout.dialog_loading);
        setCancelable(false);
        initView();
    }

    private void initView() {
        RotateAnimation rotateAnimation = new RotateAnimation(0.0f, 360.0f, 1, 0.5f, 1, 0.5f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatCount(-1);
        ((ImageView) findViewById(R.id.im_dialog_loading)).startAnimation(rotateAnimation);
        ((RobotoRegularTextView) findViewById(R.id.tv_dialog_loading)).setText(this.text);
    }
}
