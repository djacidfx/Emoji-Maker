package com.demo.example.emoji.models;


public class EmojiObject {
    private int index;
    private boolean isLock;
    private String linkEmojiClicked;
    private String linkEmojiNomal;

    public EmojiObject(String str, String str2, boolean z) {
        this.linkEmojiNomal = str;
        this.linkEmojiClicked = str2;
        this.isLock = z;
    }

    public EmojiObject(String str, String str2, int i, boolean z) {
        this.linkEmojiNomal = str;
        this.linkEmojiClicked = str2;
        this.index = i;
        this.isLock = z;
    }

    public EmojiObject(String str, int i, boolean z) {
        this.linkEmojiNomal = str;
        this.index = i;
        this.isLock = z;
    }

    public EmojiObject(String str, int i) {
        this.linkEmojiNomal = str;
        this.index = i;
    }

    public boolean isLock() {
        return this.isLock;
    }

    public void setLock(boolean z) {
        this.isLock = z;
    }

    public EmojiObject(String str) {
        this.linkEmojiNomal = str;
    }

    public String getLinkEmojiNomal() {
        return this.linkEmojiNomal;
    }

    public void setLinkEmojiNomal(String str) {
        this.linkEmojiNomal = str;
    }

    public String getLinkEmojiClicked() {
        return this.linkEmojiClicked;
    }

    public void setLinkEmojiClicked(String str) {
        this.linkEmojiClicked = str;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int i) {
        this.index = i;
    }
}
