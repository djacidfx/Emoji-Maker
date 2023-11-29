package com.demo.example.emoji.stickers;

import java.io.Serializable;


public class BubblePropertyModel implements Serializable {
    private static final long serialVersionUID = 6339777989485920188L;
    private long bubbleId;
    private float degree;
    private int order;
    private float scaling;
    private String text;
    private float xLocation;
    private float yLocation;

    public long getBubbleId() {
        return this.bubbleId;
    }

    public void setBubbleId(long j) {
        this.bubbleId = j;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public float getxLocation() {
        return this.xLocation;
    }

    public void setxLocation(float f) {
        this.xLocation = f;
    }

    public float getyLocation() {
        return this.yLocation;
    }

    public void setyLocation(float f) {
        this.yLocation = f;
    }

    public float getDegree() {
        return this.degree;
    }

    public void setDegree(float f) {
        this.degree = f;
    }

    public float getScaling() {
        return this.scaling;
    }

    public void setScaling(float f) {
        this.scaling = f;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int i) {
        this.order = i;
    }
}
