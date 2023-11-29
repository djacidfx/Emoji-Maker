package com.demo.example.emoji.models;

import com.demo.example.emoji.stickers.StickerView;


public class StickerListModel {
    private int positionIndex;
    private int positionTab;
    private StickerView stickerView;

    public StickerListModel(StickerView stickerView, int i, int i2) {
        this.stickerView = stickerView;
        this.positionIndex = i;
        this.positionTab = i2;
    }

    public StickerView getStickerView() {
        return this.stickerView;
    }

    public void setStickerView(StickerView stickerView) {
        this.stickerView = stickerView;
    }

    public int getPositionTab() {
        return this.positionTab;
    }

    public int getPositionIndex() {
        return this.positionIndex;
    }

    public void setPositionIndex(int i) {
        this.positionIndex = i;
    }

    public void setPositionTab(int i) {
        this.positionTab = i;
    }
}
