package com.demo.example.emoji.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;


public class CurvedView extends View {
    private Path myArc;
    private Paint paint;
    private RectF rectF;
    private String s = "LOVE";
    private int shawdownColor = -16777216;

    public CurvedView(Context context) {
        super(context);
        setUp();
    }

    public CurvedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setUp();
    }

    public CurvedView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setUp();
    }

    private void setUp() {
        setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
        this.paint = new Paint(1);
        this.paint.setColor(-16777216);
        this.paint.setTextSize(100.0f);
    }

    public void setColorForShadown(int i) {
        this.shawdownColor = i;
        invalidate();
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.myArc != null) {
            canvas.drawTextOnPath(this.s, this.myArc, 0.0f, 0.0f, this.paint);
            return;
        }
        this.paint.setShadowLayer(8.0f, -5.0f, -5.0f, this.shawdownColor);
        canvas.drawText(this.s, (float) ((getWidth() - getWidthText()) / 2), (float) ((getHeight() + getHeightText()) / 2), this.paint);
    }

    public void setAngle(int i) {
        if (i == 0) {
            this.myArc = null;
            invalidate();
            return;
        }
        int widthText = getWidthText();
        this.myArc = new Path();
        if (i > 0) {
            int ceil = ((int) Math.ceil(((double) (widthText * 180)) / (3.141592653589793d * ((double) i)))) * 2;
            int width = (getWidth() - ceil) / 2;
            int height = getHeight() / 2;
            int i2 = width + ceil;
            int i3 = height + ceil;
            this.rectF = new RectF((float) width, (float) height, (float) i2, (float) i3);
            this.myArc.addArc(this.rectF, (float) (-90 - (i / 2)), (float) i);
            Log.e("path of set", width + "/" + height + "/" + i2 + "/" + i3 + ceil + "/" + getHeight());
        } else {
            int i4 = 0 - i;
            int ceil2 = ((int) Math.ceil(((double) (widthText * 180)) / (3.141592653589793d * ((double) i4)))) * 2;
            int width2 = (getWidth() - ceil2) / 2;
            this.rectF = new RectF((float) width2, (float) ((getHeight() / 2) - ceil2), (float) (ceil2 + width2), (float) (getHeight() / 2));
            this.myArc.addArc(this.rectF, (float) (-270 + (i4 / 2)), (float) (-i4));
        }
        invalidate();
    }

    private int getWidthText() {
        Rect rect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(this.paint.getTextSize());
        paint.getTextBounds(this.s, 0, this.s.length(), rect);
        return rect.width();
    }

    private int getHeightText() {
        Rect rect = new Rect();
        Paint paint = new Paint();
        paint.setTextSize(this.paint.getTextSize());
        paint.getTextBounds(this.s, 0, this.s.length(), rect);
        return rect.height();
    }

    public void setText(String str) {
        this.s = str;
        invalidate();
    }

    public void setTextSize(float f) {
        this.paint.setTextSize(f);
        invalidate();
    }

    public void setTextColor(int i) {
        this.paint.setColor(i);
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        this.paint.setTypeface(typeface);
        invalidate();
    }

    public void reset() {
        this.s = "Text preview";
        this.myArc = null;
        this.paint.setTextSize(40.0f);
        this.paint.setColor(-16777216);
        invalidate();
    }
}
