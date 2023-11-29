package com.demo.example.emoji.item;

import java.io.Serializable;


public class ItemPhoto implements Serializable {
    private boolean choose;
    private String link;

    public ItemPhoto(String str, boolean z) {
        this.link = str;
        this.choose = z;
    }

    public void setChoose(boolean z) {
        this.choose = z;
    }

    public String getLink() {
        return this.link;
    }

    public boolean isChoose() {
        return this.choose;
    }
}
