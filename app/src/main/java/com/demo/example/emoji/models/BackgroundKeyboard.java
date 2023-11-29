package com.demo.example.emoji.models;

import android.graphics.Typeface;


public class BackgroundKeyboard {
    private boolean isAssets;
    private boolean isBackground;
    private boolean isDrawable;
    private boolean isSetFont;
    private String mColor_bg;
    private String mPath_bg;
    private String mStyleKeyboard;
    private Typeface typeface;

    public BackgroundKeyboard(boolean z, boolean z2, String str, String str2, String str3, boolean z3, boolean z4) {
        this.isBackground = z2;
        this.mColor_bg = str;
        this.mPath_bg = str2;
        this.isAssets = z;
        this.mStyleKeyboard = str3;
        this.isDrawable = z3;
        this.isSetFont = z4;
    }

    public boolean isSetFont() {
        return this.isSetFont;
    }

    public void setIsSetFont(boolean z) {
        this.isSetFont = z;
    }

    public Typeface getTypeface() {
        return this.typeface;
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public boolean isDrawable() {
        return this.isDrawable;
    }

    public void setIsDrawable(boolean z) {
        this.isDrawable = z;
    }

    public boolean isAssets() {
        return this.isAssets;
    }

    public void setIsAssets(boolean z) {
        this.isAssets = z;
    }

    public boolean isBackground() {
        return this.isBackground;
    }

    public void setBackground(boolean z) {
        this.isBackground = z;
    }

    public String getmColor_bg() {
        return this.mColor_bg;
    }

    public void setmColor_bg(String str) {
        this.mColor_bg = str;
    }

    public String getmPath_bg() {
        return this.mPath_bg;
    }

    public void setmPath_bg(String str) {
        this.mPath_bg = str;
    }

    public void setIsBackground(boolean z) {
        this.isBackground = z;
    }

    public String getmStyleKeyboard() {
        return this.mStyleKeyboard;
    }

    public void setmStyleKeyboard(String str) {
        this.mStyleKeyboard = str;
    }
}
