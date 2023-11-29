package com.demo.example.emoji.librate;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.demo.example.R;

import java.util.ArrayList;
import java.util.List;


public class BaseRatingBar extends LinearLayout implements SimpleRatingBar {
    private static final int MAX_CLICK_DISTANCE = 5;
    private static final int MAX_CLICK_DURATION = 200;
    public static final String TAG = "SimpleRatingBar";
    private boolean mClearRatingEnabled;
    private Drawable mEmptyDrawable;
    private Drawable mFilledDrawable;
    private boolean mIsTouchable;
    private int mNumStars;
    private OnRatingChangeListener mOnRatingChangeListener;
    private int mPadding;
    protected List<PartialView> mPartialViews;
    private float mPreviousRating;
    private float mRating;
    private int mStarHeight;
    private int mStarWidth;
    private float mStartX;
    private float mStartY;

    
    public interface OnRatingChangeListener {
        void onRatingChange(BaseRatingBar baseRatingBar, float f);
    }

    @Override 
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return true;
    }

    public BaseRatingBar(Context context) {
        this(context, null);
    }

    public BaseRatingBar(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseRatingBar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mPadding = 0;
        this.mRating = -1.0f;
        this.mPreviousRating = 0.0f;
        this.mIsTouchable = true;
        this.mClearRatingEnabled = true;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BaseRatingBar);
        float f = obtainStyledAttributes.getFloat( R.styleable.BaseRatingBar_srb_rating, this.mRating);
        this.mNumStars = obtainStyledAttributes.getInt( R.styleable.BaseRatingBar_srb_numStars, this.mNumStars);
        this.mPadding = obtainStyledAttributes.getInt( R.styleable.BaseRatingBar_srb_starPadding, this.mPadding);
        this.mStarWidth = obtainStyledAttributes.getDimensionPixelSize( R.styleable.BaseRatingBar_srb_starWidth, 0);
        this.mStarHeight = obtainStyledAttributes.getDimensionPixelSize( R.styleable.BaseRatingBar_srb_starHeight, 0);
        this.mEmptyDrawable = obtainStyledAttributes.getDrawable( R.styleable.BaseRatingBar_srb_drawableEmpty);
        this.mFilledDrawable = obtainStyledAttributes.getDrawable( R.styleable.BaseRatingBar_srb_drawableFilled);
        this.mIsTouchable = obtainStyledAttributes.getBoolean( R.styleable.BaseRatingBar_srb_clickable, this.mIsTouchable);
        this.mClearRatingEnabled = obtainStyledAttributes.getBoolean( R.styleable.BaseRatingBar_srb_clearRatingEnabled, this.mClearRatingEnabled);
        obtainStyledAttributes.recycle();
        verifyParamsValue();
        initRatingView();
        setRating(f);
    }

    private void verifyParamsValue() {
        if (this.mNumStars <= 0) {
            this.mNumStars = 5;
        }
        if (this.mPadding < 0) {
            this.mPadding = 10;
        }
        if (this.mEmptyDrawable == null) {
            this.mEmptyDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_star);
        }
        if (this.mFilledDrawable == null) {
            this.mFilledDrawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_star_active);
        }
    }

    private void initRatingView() {
        this.mPartialViews = new ArrayList();
        int i = -2;
        int i2 = this.mStarWidth == 0 ? -2 : this.mStarWidth;
        if (this.mStarHeight != 0) {
            i = this.mStarHeight;
        }
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(i2, i);
        for (int i3 = 1; i3 <= this.mNumStars; i3++) {
            PartialView partialView = getPartialView(i3, this.mFilledDrawable, this.mEmptyDrawable);
            this.mPartialViews.add(partialView);
            addView(partialView, layoutParams);
        }
    }

    private PartialView getPartialView(int i, Drawable drawable, Drawable drawable2) {
        PartialView partialView = new PartialView(getContext());
        partialView.setId(i);
        partialView.setPadding(this.mPadding, this.mPadding, this.mPadding, this.mPadding);
        partialView.setFilledDrawable(drawable);
        partialView.setEmptyDrawable(drawable2);
        return partialView;
    }

    protected void emptyRatingBar() {
        fillRatingBar(0.0f);
    }

    protected void fillRatingBar(float f) {
        for (PartialView partialView : this.mPartialViews) {
            int id = partialView.getId();
            double ceil = Math.ceil((double) f);
            double d = (double) id;
            if (d > ceil) {
                partialView.setEmpty();
            } else if (d == ceil) {
                partialView.setPartialFilled(f);
            } else {
                partialView.setFilled();
            }
        }
    }

    @Override 
    public void setNumStars(int i) {
        if (i > 0) {
            this.mPartialViews.clear();
            removeAllViews();
            this.mNumStars = i;
            initRatingView();
        }
    }

    @Override 
    public int getNumStars() {
        return this.mNumStars;
    }

    @Override 
    public void setRating(float f) {
        if (f > ((float) this.mNumStars)) {
            f = (float) this.mNumStars;
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        if (this.mRating != f) {
            this.mRating = f;
            if (this.mOnRatingChangeListener != null) {
                this.mOnRatingChangeListener.onRatingChange(this, this.mRating);
            }
            fillRatingBar(f);
        }
    }

    @Override 
    public float getRating() {
        return this.mRating;
    }

    @Override 
    public void setStarPadding(int i) {
        if (i >= 0) {
            this.mPadding = i;
            for (PartialView partialView : this.mPartialViews) {
                partialView.setPadding(this.mPadding, this.mPadding, this.mPadding, this.mPadding);
            }
        }
    }

    @Override 
    public int getStarPadding() {
        return this.mPadding;
    }

    @Override 
    public void setEmptyDrawableRes(@DrawableRes int i) {
        setEmptyDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    @Override 
    public void setFilledDrawableRes(@DrawableRes int i) {
        setFilledDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    @Override 
    public void setEmptyDrawable(Drawable drawable) {
        this.mEmptyDrawable = drawable;
        for (PartialView partialView : this.mPartialViews) {
            partialView.setEmptyDrawable(drawable);
        }
    }

    @Override 
    public void setFilledDrawable(Drawable drawable) {
        this.mFilledDrawable = drawable;
        for (PartialView partialView : this.mPartialViews) {
            partialView.setFilledDrawable(drawable);
        }
    }

    @Override 
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isTouchable()) {
            return false;
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        switch (motionEvent.getAction()) {
            case 0:
                this.mStartX = x;
                this.mStartY = y;
                this.mPreviousRating = this.mRating;
                handleMoveEvent(x);
                return true;
            case 1:
                if (!isClickEvent(this.mStartX, this.mStartY, motionEvent)) {
                    return false;
                }
                handleClickEvent(x);
                return true;
            case 2:
                handleMoveEvent(x);
                return true;
            default:
                return true;
        }
    }

    private void handleMoveEvent(float f) {
        for (PartialView partialView : this.mPartialViews) {
            if (f < ((float) partialView.getWidth()) / 2.0f) {
                setRating(0.0f);
                return;
            } else if (isPositionInRatingView(f, partialView)) {
                float id = (float) partialView.getId();
                if (this.mRating != id) {
                    setRating(id);
                }
            }
        }
    }

    private void handleClickEvent(float f) {
        for (PartialView partialView : this.mPartialViews) {
            if (isPositionInRatingView(f, partialView)) {
                float id = (float) partialView.getId();
                if (this.mPreviousRating != id || !isClearRatingEnabled()) {
                    setRating(id);
                    return;
                } else {
                    setRating(0.0f);
                    return;
                }
            }
        }
    }

    private boolean isPositionInRatingView(float f, View view) {
        return f > ((float) view.getLeft()) && f < ((float) view.getRight());
    }

    private boolean isClickEvent(float f, float f2, MotionEvent motionEvent) {
        if (((float) (motionEvent.getEventTime() - motionEvent.getDownTime())) > 200.0f) {
            return false;
        }
        float abs = Math.abs(f - motionEvent.getX());
        float abs2 = Math.abs(f2 - motionEvent.getY());
        if (abs > 5.0f || abs2 > 5.0f) {
            return false;
        }
        return true;
    }

    public void setOnRatingChangeListener(OnRatingChangeListener onRatingChangeListener) {
        this.mOnRatingChangeListener = onRatingChangeListener;
    }

    public boolean isTouchable() {
        return this.mIsTouchable;
    }

    public void setTouchable(boolean z) {
        this.mIsTouchable = z;
    }

    public void setClearRatingEnabled(boolean z) {
        this.mClearRatingEnabled = z;
    }

    public boolean isClearRatingEnabled() {
        return this.mClearRatingEnabled;
    }
}
