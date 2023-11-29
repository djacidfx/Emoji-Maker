package com.demo.example.emoji.stickers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;

import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.MotionEventCompat;

import com.demo.example.R;


public class BubbleTextView extends AppCompatImageView {
    private static final float BITMAP_SCALE = 0.7f;
    private static final String TAG = "BubbleTextView";
    private float MAX_SCALE;
    private float MIN_SCALE;
    private float baseline;
    private final long bubbleId;
    private Canvas canvasText;
    private final String defaultStr;
    private Bitmap deleteBitmap;
    private int deleteBitmapHeight;
    private int deleteBitmapWidth;
    private DisplayMetrics dm;
    private final long doubleClickTimeLimit;
    private Rect dst_delete;
    private Rect dst_flipV;
    private Rect dst_resize;
    private Rect dst_top;
    private Bitmap flipVBitmap;
    private int flipVBitmapHeight;
    private int flipVBitmapWidth;
    private Paint.FontMetrics fm;
    private final int fontColor;
    private double halfDiagonalLength;
    private boolean isDown;
    private boolean isInBitmap;
    private boolean isInEdit;
    private boolean isInResize;
    private boolean isInSide;
    boolean isInit;
    private boolean isMove;
    private boolean isPointerDown;
    private boolean isTop;
    private boolean isUp;
    private float lastLength;
    private float lastRotateDegree;
    private float lastX;
    private float lastY;
    private Paint localPaint;
    private Bitmap mBitmap;
    private final float mDefaultMargin;
    private final float mDefultSize;
    private TextPaint mFontPaint;
    private float mFontSize;
    private float mMargin;
    private final float mMaxFontSize;
    private final float mMinFontSize;
    private int mScreenHeight;
    private int mScreenwidth;
    private String mStr;
    private Matrix matrix;
    private PointF mid;
    private final float moveLimitDis;
    private float oldDis;
    private OperationListener operationListener;
    private Bitmap originBitmap;
    private float oringinWidth;
    private final float pointerLimitDis;
    private final float pointerZoomCoeff;
    private long preClicktime;
    private Bitmap resizeBitmap;
    private int resizeBitmapHeight;
    private int resizeBitmapWidth;
    private int shawdownColor;
    private Bitmap topBitmap;
    private int topBitmapHeight;
    private int topBitmapWidth;

    
    public interface OperationListener {
        void onClick(BubbleTextView bubbleTextView);

        void onDeleteClick();

        void onEdit(BubbleTextView bubbleTextView);

        void onTop(BubbleTextView bubbleTextView);
    }

    public BubbleTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mid = new PointF();
        this.isPointerDown = false;
        this.pointerLimitDis = 20.0f;
        this.pointerZoomCoeff = 0.09f;
        this.moveLimitDis = 0.5f;
        this.isInResize = false;
        this.matrix = new Matrix();
        this.isInEdit = true;
        this.MIN_SCALE = 0.5f;
        this.MAX_SCALE = 1.5f;
        this.oringinWidth = 0.0f;
        this.mStr = "";
        this.mDefultSize = 16.0f;
        this.mFontSize = 30.0f;
        this.mMaxFontSize = 50.0f;
        this.mMinFontSize = 20.0f;
        this.mDefaultMargin = 20.0f;
        this.mMargin = 20.0f;
        this.isInit = true;
        this.isDown = false;
        this.isMove = false;
        this.isUp = false;
        this.isTop = true;
        this.shawdownColor = -16777216;
        this.doubleClickTimeLimit = 200;
        this.defaultStr = getContext().getString(R.string.app_name);
        this.fontColor = -16777216;
        this.bubbleId = 0;
        init();
    }

    public BubbleTextView(Context context) {
        super(context);
        this.mid = new PointF();
        this.isPointerDown = false;
        this.pointerLimitDis = 20.0f;
        this.pointerZoomCoeff = 0.09f;
        this.moveLimitDis = 0.5f;
        this.isInResize = false;
        this.matrix = new Matrix();
        this.isInEdit = true;
        this.MIN_SCALE = 0.5f;
        this.MAX_SCALE = 1.5f;
        this.oringinWidth = 0.0f;
        this.mStr = "";
        this.mDefultSize = 16.0f;
        this.mFontSize = 30.0f;
        this.mMaxFontSize = 50.0f;
        this.mMinFontSize = 20.0f;
        this.mDefaultMargin = 20.0f;
        this.mMargin = 20.0f;
        this.isInit = true;
        this.isDown = false;
        this.isMove = false;
        this.isUp = false;
        this.isTop = true;
        this.shawdownColor = -16777216;
        this.doubleClickTimeLimit = 200;
        this.defaultStr = getContext().getString(R.string.app_name);
        this.fontColor = -16777216;
        this.bubbleId = 0;
        init();
    }

    public BubbleTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mid = new PointF();
        this.isPointerDown = false;
        this.pointerLimitDis = 20.0f;
        this.pointerZoomCoeff = 0.09f;
        this.moveLimitDis = 0.5f;
        this.isInResize = false;
        this.matrix = new Matrix();
        this.isInEdit = true;
        this.MIN_SCALE = 0.5f;
        this.MAX_SCALE = 1.5f;
        this.oringinWidth = 0.0f;
        this.mStr = "";
        this.mDefultSize = 16.0f;
        this.mFontSize = 30.0f;
        this.mMaxFontSize = 50.0f;
        this.mMinFontSize = 20.0f;
        this.mDefaultMargin = 20.0f;
        this.mMargin = 20.0f;
        this.isInit = true;
        this.isDown = false;
        this.isMove = false;
        this.isUp = false;
        this.isTop = true;
        this.shawdownColor = -16777216;
        this.doubleClickTimeLimit = 200;
        this.defaultStr = getContext().getString(R.string.app_name);
        this.fontColor = -16777216;
        this.bubbleId = 0;
        init();
    }

    public BubbleTextView(Context context, int i, long j) {
        super(context);
        this.mid = new PointF();
        this.isPointerDown = false;
        this.pointerLimitDis = 20.0f;
        this.pointerZoomCoeff = 0.09f;
        this.moveLimitDis = 0.5f;
        this.isInResize = false;
        this.matrix = new Matrix();
        this.isInEdit = true;
        this.MIN_SCALE = 0.5f;
        this.MAX_SCALE = 1.5f;
        this.oringinWidth = 0.0f;
        this.mStr = "";
        this.mDefultSize = 16.0f;
        this.mFontSize = 30.0f;
        this.mMaxFontSize = 50.0f;
        this.mMinFontSize = 20.0f;
        this.mDefaultMargin = 20.0f;
        this.mMargin = 20.0f;
        this.isInit = true;
        this.isDown = false;
        this.isMove = false;
        this.isUp = false;
        this.isTop = true;
        this.shawdownColor = -16777216;
        this.doubleClickTimeLimit = 200;
        this.defaultStr = getContext().getString(R.string.app_name);
        this.fontColor = i;
        this.bubbleId = j;
        init();
    }

    public void setSize(int i) {
        this.mFontSize = (float) i;
        invalidate();
    }

    public void setColorForShadown(int i) {
        this.shawdownColor = i;
        invalidate();
    }

    private void init() {
        this.dm = getResources().getDisplayMetrics();
        this.dst_delete = new Rect();
        this.dst_resize = new Rect();
        this.dst_flipV = new Rect();
        this.dst_top = new Rect();
        this.localPaint = new Paint();
        this.localPaint.setColor(getResources().getColor(R.color.colorPrimary));
        this.localPaint.setAntiAlias(true);
        this.localPaint.setDither(true);
        this.localPaint.setStyle(Paint.Style.STROKE);
        this.localPaint.setStrokeWidth(2.0f);
        this.mScreenwidth = this.dm.widthPixels;
        this.mScreenHeight = this.dm.heightPixels;
        this.mFontSize = 16.0f;
        this.mFontPaint = new TextPaint();
        this.mFontPaint.setTextSize(TypedValue.applyDimension(2, this.mFontSize, this.dm));
        this.mFontPaint.setColor(this.fontColor);
        this.mFontPaint.setTextAlign(Paint.Align.CENTER);
        this.mFontPaint.setAntiAlias(true);
        this.fm = this.mFontPaint.getFontMetrics();
        this.baseline = this.fm.descent - this.fm.ascent;
        this.isInit = true;
        this.mStr = this.defaultStr;
    }

    @Override 
    protected void onDraw(Canvas canvas) {
        String[] strArr;
        if (this.mBitmap != null) {
            float[] fArr = new float[9];
            this.matrix.getValues(fArr);
            int i = 2;
            float f = fArr[2] + (fArr[0] * 0.0f) + (fArr[1] * 0.0f);
            float f2 = (fArr[3] * 0.0f) + (fArr[4] * 0.0f) + fArr[5];
            float width = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * 0.0f) + fArr[2];
            float width2 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * 0.0f) + fArr[5];
            float height = (fArr[0] * 0.0f) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float height2 = (0.0f * fArr[3]) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            float width3 = (fArr[0] * ((float) this.mBitmap.getWidth())) + (fArr[1] * ((float) this.mBitmap.getHeight())) + fArr[2];
            float width4 = (fArr[3] * ((float) this.mBitmap.getWidth())) + (fArr[4] * ((float) this.mBitmap.getHeight())) + fArr[5];
            canvas.save();
            this.mBitmap = this.originBitmap.copy(Bitmap.Config.ARGB_8888, true);
            this.canvasText.setBitmap(this.mBitmap);
            this.canvasText.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            canvas.setDrawFilter(new PaintFlagsDrawFilter(0, 3));
            float applyDimension = TypedValue.applyDimension(1, 15.0f, this.dm);
            float f3 = fArr[0];
            float f4 = fArr[3];
            float sqrt = ((float) Math.sqrt((double) ((f3 * f3) + (f4 * f4)))) * 0.75f * 16.0f;
            if (sqrt > 50.0f) {
                this.mFontSize = 50.0f;
            } else if (sqrt < 20.0f) {
                this.mFontSize = 20.0f;
            } else {
                this.mFontSize = sqrt;
            }
            this.mFontPaint.setShadowLayer(4.0f, -5.0f, -5.0f, this.shawdownColor);
            this.mFontPaint.setTextSize(TypedValue.applyDimension(2, this.mFontSize, this.dm));
            String[] autoSplit = autoSplit(this.mStr, this.mFontPaint, ((float) this.mBitmap.getWidth()) - (applyDimension * 3.0f));
            float height3 = ((((float) this.mBitmap.getHeight()) - ((((float) autoSplit.length) * (this.baseline + this.fm.leading)) + this.baseline)) / 2.0f) + this.baseline;
            int length = autoSplit.length;
            float f5 = height3;
            int i2 = 0;
            while (i2 < length) {
                String str = autoSplit[i2];
                if (TextUtils.isEmpty(str)) {
                    strArr = autoSplit;
                } else {
                    strArr = autoSplit;
                    this.canvasText.drawText(str, (float) (this.mBitmap.getWidth() / i), f5, this.mFontPaint);
                    f5 += this.baseline + this.fm.leading;
                }
                i2++;
                autoSplit = strArr;
                i = 2;
            }
            canvas.drawBitmap(this.mBitmap, this.matrix, null);
            this.dst_delete.left = (int) (width - ((float) (this.deleteBitmapWidth / 2)));
            this.dst_delete.right = (int) (((float) (this.deleteBitmapWidth / 2)) + width);
            this.dst_delete.top = (int) (width2 - ((float) (this.deleteBitmapHeight / 2)));
            this.dst_delete.bottom = (int) (((float) (this.deleteBitmapHeight / 2)) + width2);
            this.dst_resize.left = (int) (width3 - ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.right = (int) (width3 + ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.top = (int) (width4 - ((float) (this.resizeBitmapHeight / 2)));
            this.dst_resize.bottom = (int) (((float) (this.resizeBitmapHeight / 2)) + width4);
            this.dst_top.left = (int) (f - ((float) (this.topBitmapWidth / 2)));
            this.dst_top.right = (int) (((float) (this.topBitmapWidth / 2)) + f);
            this.dst_top.top = (int) (f2 - ((float) (this.topBitmapHeight / 2)));
            this.dst_top.bottom = (int) (((float) (this.topBitmapHeight / 2)) + f2);
            if (this.isInEdit) {
                canvas.drawLine(f, f2, width, width2, this.localPaint);
                canvas.drawLine(width, width2, width3, width4, this.localPaint);
                canvas.drawLine(height, height2, width3, width4, this.localPaint);
                canvas.drawLine(height, height2, f, f2, this.localPaint);
            }
            canvas.restore();
        }
    }

    public void setText(String str) {
        this.mStr = str;
        invalidate();
    }

    public void setFont(Typeface typeface) {
        this.mFontPaint.setTypeface(typeface);
        invalidate();
    }

    public void setColor(int i) {
        this.mFontPaint.setColor(i);
        invalidate();
    }

    public void setAlphaText(int i) {
        this.mFontPaint.setAlpha(i);
        invalidate();
    }

    @Override 
    public void setImageResource(int i) {
        this.matrix.reset();
        setBitmap(BitmapFactory.decodeResource(getResources(), i));
    }

    public void setImageResource(int i, BubblePropertyModel bubblePropertyModel) {
        this.matrix.reset();
        setBitmap(BitmapFactory.decodeResource(getResources(), i), bubblePropertyModel);
    }

    public void setBitmap(Bitmap bitmap, BubblePropertyModel bubblePropertyModel) {
        this.mFontSize = 16.0f;
        this.originBitmap = bitmap;
        this.mBitmap = this.originBitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.canvasText = new Canvas(this.mBitmap);
        setDiagonalLength();
        initBitmaps();
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        float f = (float) width;
        this.oringinWidth = f;
        this.mStr = bubblePropertyModel.getText();
        float scaling = (bubblePropertyModel.getScaling() * ((float) this.mScreenwidth)) / ((float) this.mBitmap.getWidth());
        if (scaling > this.MAX_SCALE) {
            scaling = this.MAX_SCALE;
        } else if (scaling < this.MIN_SCALE) {
            scaling = this.MIN_SCALE;
        }
        float f2 = (float) (width >> 1);
        float f3 = (float) (height >> 1);
        this.matrix.postRotate(-((float) Math.toDegrees((double) bubblePropertyModel.getDegree())), f2, f3);
        this.matrix.postScale(scaling, scaling, f2, f3);
        float f4 = bubblePropertyModel.getxLocation() * ((float) this.mScreenwidth);
        float applyDimension = TypedValue.applyDimension(1, 22.0f, this.dm);
        this.matrix.postTranslate((f4 - ((f * scaling) / 2.0f)) - applyDimension, ((bubblePropertyModel.getyLocation() * ((float) this.mScreenwidth)) - ((((float) height) * scaling) / 2.0f)) - applyDimension);
        invalidate();
    }

    public void setBitmap(Bitmap bitmap) {
        this.mFontSize = 16.0f;
        this.originBitmap = bitmap;
        this.mBitmap = this.originBitmap.copy(Bitmap.Config.ARGB_8888, true);
        this.canvasText = new Canvas(this.mBitmap);
        setDiagonalLength();
        initBitmaps();
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        this.oringinWidth = (float) width;
        this.matrix.postTranslate((float) ((this.mScreenwidth / 3) - (width / 3)), (float) ((this.mScreenwidth / 3) - (height / 3)));
        invalidate();
    }

    private void setDiagonalLength() {
        this.halfDiagonalLength = Math.hypot((double) this.mBitmap.getWidth(), (double) this.mBitmap.getHeight()) / 2.0d;
    }

    private void initBitmaps() {
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
        this.topBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_top_enable);
        this.deleteBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_delete_red);
        this.flipVBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_flip);
        this.resizeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_resize);
        this.deleteBitmapWidth = (int) (((float) this.deleteBitmap.getWidth()) * BITMAP_SCALE);
        this.deleteBitmapHeight = (int) (((float) this.deleteBitmap.getHeight()) * BITMAP_SCALE);
        this.resizeBitmapWidth = (int) (((float) this.resizeBitmap.getWidth()) * BITMAP_SCALE);
        this.resizeBitmapHeight = (int) (((float) this.resizeBitmap.getHeight()) * BITMAP_SCALE);
        this.flipVBitmapWidth = (int) (((float) this.flipVBitmap.getWidth()) * BITMAP_SCALE);
        this.flipVBitmapHeight = (int) (((float) this.flipVBitmap.getHeight()) * BITMAP_SCALE);
        this.topBitmapWidth = (int) (((float) this.topBitmap.getWidth()) * BITMAP_SCALE);
        this.topBitmapHeight = (int) (((float) this.topBitmap.getHeight()) * BITMAP_SCALE);
    }

    
    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        boolean z = false;
        this.isInBitmap = false;
        if (actionMasked != 5) {
            switch (actionMasked) {
                case 0:
                    if (!isInButton(motionEvent, this.dst_delete)) {
                        if (!isInResize(motionEvent)) {
                            if (!isInButton(motionEvent, this.dst_flipV)) {
                                if (!isInButton(motionEvent, this.dst_top)) {
                                    if (isInBitmap(motionEvent)) {
                                        this.isInSide = true;
                                        this.lastX = motionEvent.getX(0);
                                        this.lastY = motionEvent.getY(0);
                                        this.isDown = true;
                                        this.isMove = false;
                                        this.isPointerDown = false;
                                        this.isUp = false;
                                        this.isInBitmap = true;
                                        long currentTimeMillis = System.currentTimeMillis();
                                        Log.d(TAG, (currentTimeMillis - this.preClicktime) + "");
                                        if (currentTimeMillis - this.preClicktime <= 200) {
                                            if (this.isInEdit && this.operationListener != null) {
                                                this.operationListener.onClick(this);
                                                break;
                                            }
                                        } else {
                                            this.preClicktime = currentTimeMillis;
                                            break;
                                        }
                                    }
                                } else {
                                    bringToFront();
                                    if (this.operationListener != null) {
                                        this.operationListener.onTop(this);
                                    }
                                    this.isDown = false;
                                    break;
                                }
                            } else {
                                PointF pointF = new PointF();
                                midDiagonalPoint(pointF);
                                this.matrix.postScale(-1.0f, 1.0f, pointF.x, pointF.y);
                                this.isDown = false;
                                invalidate();
                                break;
                            }
                        } else {
                            this.isInResize = true;
                            this.lastRotateDegree = rotationToStartPoint(motionEvent);
                            midPointToStartPoint(motionEvent);
                            this.lastLength = diagonalLength(motionEvent);
                            this.isDown = false;
                            break;
                        }
                    } else {
                        if (this.operationListener != null) {
                            this.operationListener.onDeleteClick();
                        }
                        this.isDown = false;
                        break;
                    }
                    break;
                case 1:
                case 3:
                    this.isInResize = false;
                    this.isInSide = false;
                    this.isPointerDown = false;
                    this.isUp = true;
                    break;
                case 2:
                    if (!this.isPointerDown) {
                        if (!this.isInResize) {
                            if (this.isInSide) {
                                float x = motionEvent.getX(0);
                                float y = motionEvent.getY(0);
                                if (this.isMove || Math.abs(x - this.lastX) >= 0.5f || Math.abs(y - this.lastY) >= 0.5f) {
                                    this.isMove = true;
                                } else {
                                    this.isMove = false;
                                }
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
            if (z && this.operationListener != null) {
                this.operationListener.onEdit(this);
            }
            return z;
        }
        if (spacing(motionEvent) > 20.0f) {
            this.oldDis = spacing(motionEvent);
            this.isPointerDown = true;
            midPointToStartPoint(motionEvent);
        } else {
            this.isPointerDown = false;
        }
        this.isInSide = false;
        this.isInResize = false;
        z = true;
        if (z) {
            this.operationListener.onEdit(this);
        }
        return z;
    }

    public BubblePropertyModel calculate(BubblePropertyModel bubblePropertyModel) {
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
        float centerX = (float) ((this.dst_top.centerX() + this.dst_resize.centerX()) / 2);
        float centerY = (float) ((this.dst_top.centerY() + this.dst_resize.centerY()) / 2);
        Log.d(TAG, "midX : " + centerX + " midY : " + centerY);
        bubblePropertyModel.setDegree((float) Math.toRadians((double) round));
        bubblePropertyModel.setBubbleId(this.bubbleId);
        bubblePropertyModel.setScaling((((float) this.mBitmap.getWidth()) * sqrt) / ((float) this.mScreenwidth));
        Log.d(TAG, " x " + (centerX / ((float) this.mScreenwidth)) + " y " + (centerY / ((float) this.mScreenwidth)));
        bubblePropertyModel.setxLocation(centerX / ((float) this.mScreenwidth));
        bubblePropertyModel.setyLocation(centerY / ((float) this.mScreenwidth));
        bubblePropertyModel.setText(this.mStr);
        return bubblePropertyModel;
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

    private void midPointToStartPoint(MotionEvent motionEvent) {
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

    private float rotationToStartPoint(MotionEvent motionEvent) {
        float[] fArr = new float[9];
        this.matrix.getValues(fArr);
        float f = (fArr[0] * 0.0f) + (fArr[1] * 0.0f) + fArr[2];
        return (float) Math.toDegrees(Math.atan2((double) (motionEvent.getY(0) - (((fArr[3] * 0.0f) + (0.0f * fArr[4])) + fArr[5])), (double) (motionEvent.getX(0) - f)));
    }

    private float diagonalLength(MotionEvent motionEvent) {
        return (float) Math.hypot((double) (motionEvent.getX(0) - this.mid.x), (double) (motionEvent.getY(0) - this.mid.y));
    }

    private float spacing(MotionEvent motionEvent) {
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

    private String[] autoSplit(String str, Paint paint, float f) {
        int length = str.length();
        float measureText = paint.measureText(str);
        int i = 0;
        int i2 = 1;
        if (measureText <= f) {
            return new String[]{str};
        }
        String[] strArr = new String[(int) Math.ceil((double) (measureText / f))];
        int i3 = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            if (paint.measureText(str, i, i2) > f) {
                strArr[i3] = (String) str.subSequence(i, i2);
                i = i2;
                i3++;
            }
            if (i2 == length) {
                strArr[i3] = (String) str.subSequence(i, i2);
                break;
            }
            i2++;
        }
        return strArr;
    }

    public String getmStr() {
        return this.mStr;
    }
}
