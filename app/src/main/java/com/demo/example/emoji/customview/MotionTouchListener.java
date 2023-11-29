package com.demo.example.emoji.customview;

import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.demo.example.emoji.ultis.Constant;


public class MotionTouchListener implements View.OnTouchListener {
    View[] currentView;
    PointF downP;
    float dx;
    float dy;
    float oldDis;
    float rota;
    PointF startP;

    public MotionTouchListener(View[] viewArr) {
        this.oldDis = 1.0f;
        this.rota = 0.0f;
        this.currentView = viewArr;
        this.downP = new PointF();
        this.startP = new PointF();
    }

    public MotionTouchListener() {
        this.oldDis = 1.0f;
        this.rota = 0.0f;
        this.downP = new PointF();
        this.startP = new PointF();
    }

    public MotionTouchListener(View view) {
        this.oldDis = 1.0f;
        this.rota = 0.0f;
        this.currentView = new View[1];
        this.currentView[0] = view;
        this.downP = new PointF();
        this.startP = new PointF();
    }

    @Override 
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action != 5) {
            switch (action) {
                case 0:
                    this.downP.x = motionEvent.getX();
                    this.downP.y = motionEvent.getY();
                    this.dx = this.currentView[0].getX() - motionEvent.getRawX();
                    this.dx = this.currentView[0].getY() - motionEvent.getRawY();
                    this.startP = new PointF(view.getX(), view.getY());
                    Constant.mode = Constant.MODE_DRAG;
                    return true;
                case 1:
                    Constant.mode = -1;
                    return true;
                case 2:
                    switch (Constant.mode) {
                        case 0:
                            if (motionEvent.getPointerCount() != 2) {
                                return true;
                            }
                            float scale = getScale(motionEvent);
                            if (scale > 10.0f) {
                                float scaleX = (scale / this.oldDis) * this.currentView[0].getScaleX();
                                double d = (double) scaleX;
                                if (d > 0.3d && d < 2.5d) {
                                    view.setScaleX(scaleX);
                                    view.setScaleY(scaleX);
                                }
                            }
                            view.animate().rotationBy(getRota(motionEvent) - this.rota).setDuration(0).setInterpolator(new LinearInterpolator()).start();
                            view.clearAnimation();
                            return true;
                        case 1:
                            PointF pointF = new PointF(motionEvent.getX() - this.downP.x, motionEvent.getY() - this.downP.y);
                            view.setX(this.startP.x + pointF.x);
                            view.setY(this.startP.y + pointF.y);
                            this.startP = new PointF(view.getX(), view.getY());
                            return true;
                        default:
                            return true;
                    }
                default:
                    return true;
            }
        } else {
            this.oldDis = getScale(motionEvent);
            if (this.oldDis > 10.0f) {
                Constant.mode = Constant.MODE_SCALE;
            }
            this.rota = getRota(motionEvent);
            return true;
        }
    }

    private Rect getRectView(View view) {
        int x = (int) view.getX();
        int y = (int) view.getY();
        return new Rect(x, y, view.getWidth() + x, view.getHeight() + y);
    }

    private float getScale(MotionEvent motionEvent) {
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    private float getRota(MotionEvent motionEvent) {
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - motionEvent.getY(1)), (double) (motionEvent.getX(0) - motionEvent.getX(1))));
    }
}
