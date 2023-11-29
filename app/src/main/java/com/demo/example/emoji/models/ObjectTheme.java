package com.demo.example.emoji.models;

import android.content.Context;
import com.demo.example.emoji.ultis.Constant;


public class ObjectTheme {
    private BackgroundKeyboard backgroundKeyboard;
    private ItemKeyBroad itemKeyBroad;
    private Context mContext;

    public ObjectTheme() {
        this.itemKeyBroad = new ItemKeyBroad(Constant.DefaultConfig.COLOR_TEXT_SMALL_DEFAULT, Constant.DefaultConfig.COLOR_TEXT_MAIN_DEFAULT);
        this.backgroundKeyboard = new BackgroundKeyboard(false, false, Constant.DefaultConfig.COLOR_BG_DEFAULT, "", Constant.THEME_1, false, false);
    }

    public ObjectTheme(ItemKeyBroad itemKeyBroad, BackgroundKeyboard backgroundKeyboard) {
        this.itemKeyBroad = itemKeyBroad;
        this.backgroundKeyboard = backgroundKeyboard;
    }

    public ItemKeyBroad getItemKeyBroad() {
        return this.itemKeyBroad;
    }

    public void setItemKeyBroad(ItemKeyBroad itemKeyBroad) {
        this.itemKeyBroad = itemKeyBroad;
    }

    public BackgroundKeyboard getBackgroundKeyboard() {
        return this.backgroundKeyboard;
    }

    public void setBackgroundKeyboard(BackgroundKeyboard backgroundKeyboard) {
        this.backgroundKeyboard = backgroundKeyboard;
    }
}
