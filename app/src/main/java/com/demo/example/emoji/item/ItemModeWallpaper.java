package com.demo.example.emoji.item;

import java.util.ArrayList;


public class ItemModeWallpaper {
    private ArrayList<String> arrBrush = new ArrayList<>();
    private boolean isChoose;
    private String link;
    private String linkImage;

    public ItemModeWallpaper(String str, boolean z) {
        this.link = str;
        this.isChoose = z;
    }

    public String getLink() {
        return this.link;
    }

    public boolean isChoose() {
        return this.isChoose;
    }

    public void setChoose(boolean z) {
        this.isChoose = z;
    }

    public String getLinkImage() {
        return this.linkImage;
    }

    public void setLinkImage(String str) {
        this.linkImage = str;
    }

    public ArrayList<String> getArrBrush() {
        return this.arrBrush;
    }

    public void setArrBrush(ArrayList<String> arrayList) {
        this.arrBrush.addAll(arrayList);
    }
}
