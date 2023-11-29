package com.demo.example.emoji.stickers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;

import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.MotionEventCompat;

import com.demo.example.R;


public class StickerView extends AppCompatImageView {
    private static final float BITMAP_SCALE = 0.7f;
    private static final String TAG = "StickerView";
    private Bitmap deleteBitmap;
    private int deleteBitmapHeight;
    private int deleteBitmapWidth;
    private DisplayMetrics dm;
    private Rect dst_delete;
    private Rect dst_flipV;
    private Rect dst_resize;
    private Rect dst_top;
    private int flipVBitmapHeight;
    private int flipVBitmapWidth;
    private double halfDiagonalLength;
    private boolean isEmojiMaker;
    private boolean isInSide;
    private float lastLength;
    private float lastRotateDegree;
    private float lastX;
    private float lastY;
    private Paint localPaint;
    private Bitmap mBitmap;
    private int mScreenHeight;
    private int mScreenwidth;
    private float oldDis;
    private OperationListener operationListener;
    private int resId;
    private Bitmap resizeBitmap;
    private int resizeBitmapHeight;
    private int resizeBitmapWidth;
    private int topBitmapHeight;
    private int topBitmapWidth;
    private PointF mid = new PointF();
    private boolean isPointerDown = false;
    private final float pointerLimitDis = 20.0f;
    private final float pointerZoomCoeff = 0.09f;
    private boolean isInResize = false;
    private Matrix matrix = new Matrix();
    private boolean isInEdit = true;
    private float MIN_SCALE = 0.5f;
    private float MAX_SCALE = 1.2f;
    private float oringinWidth = 0.0f;
    private boolean isHorizonMirror = false;
    private int index = 30;
    private int heightPosition = 0;
    private final long stickerId = 0;

    
    public interface OperationListener {
        void onDeleteClick();

        void onEdit(StickerView stickerView);

        void onTop(StickerView stickerView);
    }

    public void setIndexImage(int i) {
        this.index = i;
    }

    public int getIndexImg() {
        return this.index;
    }

    public StickerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public StickerView(Context context) {
        super(context);
        init();
    }

    public StickerView(Context context, int i, int i2) {
        super(context);
        init();
        this.mScreenwidth = i;
        this.mScreenHeight = i2;
    }

    public StickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.dst_delete = new Rect();
        this.dst_resize = new Rect();
        this.dst_flipV = new Rect();
        this.dst_top = new Rect();
        this.localPaint = new Paint();
        this.localPaint.setAntiAlias(true);
        this.localPaint.setDither(true);
        this.localPaint.setStyle(Paint.Style.STROKE);
        this.localPaint.setStrokeWidth(2.0f);
        this.dm = getResources().getDisplayMetrics();
        this.mScreenwidth = this.dm.widthPixels;
        this.mScreenHeight = this.dm.heightPixels;
    }

    public void setBackgroundTrans() {
        setBackgroundColor(0);
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        if (this.mBitmap != null) {
            float[] fArr = new float[9];
            this.matrix.getValues(fArr);
            float f = fArr[2] + (fArr[0] * 0.0f) + (fArr[1] * 0.0f);
            float f2 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
            float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
            float width2 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
            float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float height2 = (0.0f * fArr[3]) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            float width3 = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float width4 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            canvas.save();
            canvas.drawBitmap(this.mBitmap, this.matrix, null);
            this.dst_delete.left = (int) (width - ((float) (this.deleteBitmapWidth / 2)));
            this.dst_delete.right = (int) (((float) (this.deleteBitmapWidth / 2)) + width);
            this.dst_delete.top = (int) (width2 - ((float) (this.deleteBitmapHeight / 2)));
            this.dst_delete.bottom = (int) (((float) (this.deleteBitmapHeight / 2)) + width2);
            this.dst_resize.left = (int) (width3 - ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.right = (int) (width3 + ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.top = (int) (width4 - ((float) (this.resizeBitmapHeight / 2)));
            this.dst_resize.bottom = (int) (((float) (this.resizeBitmapHeight / 2)) + width4);
            this.dst_top.left = (int) (f - ((float) (this.flipVBitmapWidth / 2)));
            this.dst_top.right = (int) (((float) (this.flipVBitmapWidth / 2)) + f);
            this.dst_top.top = (int) (f2 - ((float) (this.flipVBitmapHeight / 2)));
            this.dst_top.bottom = (int) (((float) (this.flipVBitmapHeight / 2)) + f2);
            this.dst_flipV.left = (int) (height - ((float) (this.topBitmapWidth / 2)));
            this.dst_flipV.right = (int) (((float) (this.topBitmapWidth / 2)) + height);
            this.dst_flipV.top = (int) (height2 - ((float) (this.topBitmapHeight / 2)));
            this.dst_flipV.bottom = (int) (((float) (this.topBitmapHeight / 2)) + height2);
            if (this.index > 20 && this.isInEdit) {
                canvas.drawLine(f, f2, width, width2, this.localPaint);
                canvas.drawLine(width, width2, width3, width4, this.localPaint);
                canvas.drawLine(height, height2, width3, width4, this.localPaint);
                canvas.drawLine(height, height2, f, f2, this.localPaint);
                canvas.drawBitmap(this.deleteBitmap, (Rect) null, this.dst_delete, (Paint) null);
                canvas.drawBitmap(this.resizeBitmap, (Rect) null, this.dst_resize, (Paint) null);
            }
            canvas.restore();
        }
    }

    @Override 
    public void setImageResource(int i) {
        this.matrix.reset();
        this.resId = i;
        setBitmap(BitmapFactory.decodeResource(getResources(), this.resId), this.heightPosition, this.isEmojiMaker);
    }

    public void setBitmap(Bitmap bitmap, int i, boolean z) {
        this.heightPosition = i;
        this.isEmojiMaker = z;
        this.matrix.reset();
        this.mBitmap = bitmap;
        setDiagonalLength();
        initBitmaps();
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        this.oringinWidth = (float) width;
        if (!z) {
            this.matrix.postScale(1.0f, 1.0f, (float) (width / 2), (float) (height / 2));
        } else {
            float f = (this.MIN_SCALE + this.MAX_SCALE) / 2.0f;
            this.matrix.postScale(f, f, (float) (width / 2), (float) (height / 2));
        }
        int i2 = width / 2;
        this.matrix.postTranslate((float) ((this.mScreenwidth / 2) - i2), (float) ((this.heightPosition / 2) - i2));
        invalidate();
    }

    public Bitmap getmBitmap() {
        return this.mBitmap;
    }

    public void setHeight(int i) {
        this.heightPosition = i;
    }

    private void setDiagonalLength() {
        this.halfDiagonalLength = Math.hypot((double) this.mBitmap.getWidth(), (double) this.mBitmap.getHeight()) / 2.0d;
    }

    private void initBitmaps() {
        if (this.mBitmap.getWidth() >= this.mBitmap.getHeight()) {
            float f = (float) (this.mScreenwidth / 8);
            if (((float) this.mBitmap.getWidth()) < f) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (f * 1.0f) / ((float) this.mBitmap.getWidth());
            }
            if (this.mBitmap.getWidth() > this.mScreenwidth) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (1.0f * ((float) this.mScreenwidth)) / ((float) this.mBitmap.getWidth());
            }
        } else {
            float f2 = (float) (this.mScreenwidth / 8);
            if (((float) this.mBitmap.getHeight()) < f2) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (f2 * 1.0f) / ((float) this.mBitmap.getHeight());
            }
            if (this.mBitmap.getHeight() > this.mScreenwidth) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (1.0f * ((float) this.mScreenwidth)) / ((float) this.mBitmap.getHeight());
            }
        }
        this.deleteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_delete);
        this.resizeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_resize);
        this.deleteBitmapWidth = (int) (((float) this.deleteBitmap.getWidth()) * BITMAP_SCALE);
        this.deleteBitmapHeight = (int) (((float) this.deleteBitmap.getHeight()) * BITMAP_SCALE);
        this.resizeBitmapWidth = (int) (((float) this.resizeBitmap.getWidth()) * BITMAP_SCALE);
        this.resizeBitmapHeight = (int) (((float) this.resizeBitmap.getHeight()) * BITMAP_SCALE);
    }

    public void setColor(int i) {
        setColorFilter(i);
        invalidate();
    }

    public void flip() {
        PointF pointF = new PointF();
        midDiagonalPoint(pointF);
        this.matrix.postScale(-1.0f, 1.0f, pointF.x, pointF.y);
        this.isHorizonMirror = !this.isHorizonMirror;
        invalidate();
    }

    public void flipVertical() {
        PointF pointF = new PointF();
        midDiagonalPoint(pointF);
        this.matrix.postScale(1.0f, -1.0f, pointF.x, pointF.y);
        this.isHorizonMirror = !this.isHorizonMirror;
        invalidate();
    }

    public void delete() {
        if (this.operationListener != null) {
            this.operationListener.onDeleteClick();
        }
    }

    @Override 
    public boolean onTouchEvent(final MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        boolean z = true;
        if (actionMasked != 5) {
            switch (actionMasked) {
                case 0:
                    if (!isInButton(motionEvent, this.dst_delete)) {
                        if (!isInResize(motionEvent)) {
                            if (!isInButton(motionEvent, this.dst_flipV)) {
                                if (!isInButton(motionEvent, this.dst_top)) {
                                    if (!isInBitmap(motionEvent)) {
                                        z = false;
                                        break;
                                    } else {
                                        this.isInSide = true;
                                        this.lastX = motionEvent.getX(0);
                                        this.lastY = motionEvent.getY(0);
                                        break;
                                    }
                                } else {
                                    bringToFront();
                                    if (this.operationListener != null) {
                                        this.operationListener.onTop(this);
                                        break;
                                    }
                                }
                            } else {
                                PointF pointF = new PointF();
                                midDiagonalPoint(pointF);
                                this.matrix.postScale(-1.0f, 1.0f, pointF.x, pointF.y);
                                this.isHorizonMirror = !this.isHorizonMirror;
                                invalidate();
                                break;
                            }
                        } else {
                            Log.d("thunt", "ACTION_DOWN");
                            new Handler().postDelayed(new Runnable() { 
                                @Override 
                                public void run() {
                                    StickerView.this.isInResize = true;
                                    StickerView.this.lastRotateDegree = StickerView.this.rotationToStartPoint(motionEvent);
                                    StickerView.this.midPointToStartPoint(motionEvent);
                                    StickerView.this.lastLength = StickerView.this.diagonalLength(motionEvent);
                                }
                            }, 300);
                            break;
                        }
                    } else if (this.operationListener != null) {
                        this.operationListener.onDeleteClick();
                        break;
                    }
                    break;
                case 1:
                case 3:
                    this.isInResize = false;
                    this.isInSide = false;
                    this.isPointerDown = false;
                    break;
                case 2:
                    if (!this.isPointerDown) {
                        if (!this.isInResize) {
                            if (this.isInSide) {
                                float x = motionEvent.getX(0);
                                float y = motionEvent.getY(0);
                                this.matrix.postTranslate(x - this.lastX, y - this.lastY);
                                this.lastX = x;
                                this.lastY = y;
                                invalidate();
                                break;
                            }
                        } else {
                            this.matrix.postRotate((rotationToStartPoint(motionEvent) - this.lastRotateDegree) * 2.0f, this.mid.x, this.mid.y);
                            this.lastRotateDegree = rotationToStartPoint(motionEvent);
                            float diagonalLength = diagonalLength(motionEvent) / this.lastLength;
                            if ((((double) diagonalLength(motionEvent)) / this.halfDiagonalLength > ((double) this.MIN_SCALE) || diagonalLength >= 1.0f) && (((double) diagonalLength(motionEvent)) / this.halfDiagonalLength < ((double) this.MAX_SCALE) || diagonalLength <= 1.0f)) {
                                this.lastLength = diagonalLength(motionEvent);
                            } else {
                                if (!isInResize(motionEvent)) {
                                    this.isInResize = false;
                                }
                                diagonalLength = 1.0f;
                            }
                            this.matrix.postScale(diagonalLength, diagonalLength, this.mid.x, this.mid.y);
                            invalidate();
                            break;
                        }
                    } else {
                        float spacing = spacing(motionEvent);
                        float f = (spacing == 0.0f || spacing < 20.0f) ? 1.0f : (((spacing / this.oldDis) - 1.0f) * 0.09f) + 1.0f;
                        float abs = (((float) Math.abs(this.dst_flipV.left - this.dst_resize.left)) * f) / this.oringinWidth;
                        if ((abs > this.MIN_SCALE || f >= 1.0f) && (abs < this.MAX_SCALE || f <= 1.0f)) {
                            this.lastLength = diagonalLength(motionEvent);
                        } else {
                            f = 1.0f;
                        }
                        this.matrix.postScale(f, f, this.mid.x, this.mid.y);
                        invalidate();
                        break;
                    }
                    break;
            }
        } else {
            Log.d("thunt", "ACTION_POINTER_DOWN");
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    if (StickerView.this.spacing(motionEvent) > 20.0f) {
                        StickerView.this.oldDis = StickerView.this.spacing(motionEvent);
                        StickerView.this.isPointerDown = true;
                        StickerView.this.midPointToStartPoint(motionEvent);
                        return;
                    }
                    StickerView.this.isPointerDown = false;
                }
            }, 400);
            this.isInSide = false;
            this.isInResize = false;
        }
        if (z && this.operationListener != null) {
            this.operationListener.onEdit(this);
        }
        return z;
    }

    public StickerPropertyModel calculate(StickerPropertyModel stickerPropertyModel) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = fArr[2];
        float f2 = fArr[5];
        Log.d(TAG, "tx : " + f + " ty : " + f2);
        float f3 = fArr[0];
        float f4 = fArr[3];
        float sqrt = (float) Math.sqrt((double) ((f3 * f3) + (f4 * f4)));
        Log.d(TAG, "rScale : " + sqrt);
        float round = (float) Math.round(Math.atan2((double) fArr[1], (double) fArr[0]) * 57.29577951308232d);
        Log.d(TAG, "rAngle : " + round);
        PointF pointF = new PointF();
        midDiagonalPoint(pointF);
        Log.d(TAG, " width  : " + (((float) this.mBitmap.getWidth()) * sqrt) + " height " + (((float) this.mBitmap.getHeight()) * sqrt));
        float f5 = pointF.x;
        float f6 = pointF.y;
        Log.d(TAG, "midX : " + f5 + " midY : " + f6);
        stickerPropertyModel.setDegree((float) Math.toRadians((double) round));
        stickerPropertyModel.setScaling((((float) this.mBitmap.getWidth()) * sqrt) / ((float) this.mScreenwidth));
        stickerPropertyModel.setxLocation(f5 / ((float) this.mScreenwidth));
        stickerPropertyModel.setyLocation(f6 / ((float) this.mScreenwidth));
        stickerPropertyModel.setStickerId(this.stickerId);
        if (this.isHorizonMirror) {
            stickerPropertyModel.setHorizonMirror(1);
        } else {
            stickerPropertyModel.setHorizonMirror(2);
        }
        return stickerPropertyModel;
    }

    private boolean isInBitmap(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        float f2 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
        float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
        float width2 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
        float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
        float height2 = (0.0f * fArr[3]) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
        return pointInRect(new float[]{f, width, (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2], height}, new float[]{f2, width2, (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5], height2}, motionEvent.getX(0), motionEvent.getY(0));
    }

    private boolean pointInRect(float[] fArr, float[] fArr2, float f, float f2) {
        double hypot = Math.hypot((double) (fArr[0] - fArr[1]), (double) (fArr2[0] - fArr2[1]));
        double hypot2 = Math.hypot((double) (fArr[1] - fArr[2]), (double) (fArr2[1] - fArr2[2]));
        double hypot3 = Math.hypot((double) (fArr[3] - fArr[2]), (double) (fArr2[3] - fArr2[2]));
        double hypot4 = Math.hypot((double) (fArr[0] - fArr[3]), (double) (fArr2[0] - fArr2[3]));
        double hypot5 = Math.hypot((double) (f - fArr[0]), (double) (f2 - fArr2[0]));
        double hypot6 = Math.hypot((double) (f - fArr[1]), (double) (f2 - fArr2[1]));
        double hypot7 = Math.hypot((double) (f - fArr[2]), (double) (f2 - fArr2[2]));
        double hypot8 = Math.hypot((double) (f - fArr[3]), (double) (f2 - fArr2[3]));
        double d = ((hypot + hypot5) + hypot6) / 2.0d;
        double d2 = ((hypot2 + hypot6) + hypot7) / 2.0d;
        double d3 = ((hypot3 + hypot7) + hypot8) / 2.0d;
        double d4 = ((hypot4 + hypot8) + hypot5) / 2.0d;
        return Math.abs((hypot * hypot2) - (((Math.sqrt((d - hypot6) * (((d - hypot) * d) * (d - hypot5))) + Math.sqrt((((d2 - hypot2) * d2) * (d2 - hypot6)) * (d2 - hypot7))) + Math.sqrt((((d3 - hypot3) * d3) * (d3 - hypot7)) * (d3 - hypot8))) + Math.sqrt((((d4 - hypot4) * d4) * (d4 - hypot8)) * (d4 - hypot5)))) < 0.5d;
    }

    private boolean isInButton(MotionEvent motionEvent, Rect rect) {
        int i = rect.left;
        int i2 = rect.right;
        int i3 = rect.top;
        int i4 = rect.bottom;
        if (motionEvent.getX(0) < ((float) i) || motionEvent.getX(0) > ((float) i2) || motionEvent.getY(0) < ((float) i3) || motionEvent.getY(0) > ((float) i4)) {
            return false;
        }
        return true;
    }

    private boolean isInResize(MotionEvent motionEvent) {
        int i = -20 + this.dst_resize.top;
        int i2 = this.dst_resize.right + 20;
        int i3 = 20 + this.dst_resize.bottom;
        if (motionEvent.getX(0) < ((float) (this.dst_resize.left - 20)) || motionEvent.getX(0) > ((float) i2) || motionEvent.getY(0) < ((float) i) || motionEvent.getY(0) > ((float) i3)) {
            return false;
        }
        return true;
    }

    
    public void midPointToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        this.mid.set(((((fArr[0] * 0.0f) + (fArr[1] * 0.0f)) + fArr[2]) + motionEvent.getX(0)) / 2.0f, ((((fArr[3] * 0.0f) + (0.0f * fArr[4])) + fArr[5]) + motionEvent.getY(0)) / 2.0f);
    }

    private void midDiagonalPoint(PointF pointF) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        float f2 = (fArr[3] * 0.0f) + (0.0f * fArr[4]) + fArr[5];
        pointF.set((f + (((fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight()))) + fArr[2])) / 2.0f, (f2 + (((fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight()))) + fArr[5])) / 2.0f);
    }

    
    public float rotationToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - (((fArr[3] * 0.0f) + (0.0f * fArr[4])) + fArr[5])), (double) (motionEvent.getX(0) - f)));
    }

    
    public float diagonalLength(MotionEvent motionEvent) {
        return (float) Math.hypot((double) (motionEvent.getX(0) - this.mid.x), (double) (motionEvent.getY(0) - this.mid.y));
    }

    
    public float spacing(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() != 2) {
            return 0.0f;
        }
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void setOperationListener(OperationListener operationListener) {
        this.operationListener = operationListener;
    }

    public void setInEdit(boolean z) {
        this.isInEdit = z;
        invalidate();
    }
}
