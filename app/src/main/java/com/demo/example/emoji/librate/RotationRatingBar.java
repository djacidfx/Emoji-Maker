package com.demo.example.emoji.librate;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;

import com.demo.example.R;


public class RotationRatingBar extends BaseRatingBar {
    private static Handler sUiHandler = new Handler();

    public RotationRatingBar(Context context) {
        super(context);
    }

    public RotationRatingBar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RotationRatingBar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override 
    protected void emptyRatingBar() {
        sUiHandler.removeCallbacksAndMessages(null);
        int i = 0;
        for (final PartialView partialView : this.mPartialViews) {
            i += 5;
            new Handler().postDelayed(new Runnable() { 
                @Override 
                public void run() {
                    partialView.setEmpty();
                }
            }, (long) i);
        }
    }

    @Override 
    protected void fillRatingBar(final float f) {
        sUiHandler.removeCallbacksAndMessages(null);
        int i = 0;
        for (final PartialView partialView : this.mPartialViews) {
            final int id = partialView.getId();
            final double ceil = Math.ceil((double) f);
            if (((double) id) > ceil) {
                partialView.setEmpty();
            } else {
                i += 15;
                sUiHandler.postDelayed(new Runnable() { 
                    @Override 
                    public void run() {
                        if (((double) id) == ceil) {
                            partialView.setPartialFilled(f);
                        } else {
                            partialView.setFilled();
                        }
                        if (((float) id) == f) {
                            partialView.startAnimation(AnimationUtils.loadAnimation(RotationRatingBar.this.getContext(), R.anim.rotation));
                        }
                    }
                }, (long) i);
            }
        }
    }
}
