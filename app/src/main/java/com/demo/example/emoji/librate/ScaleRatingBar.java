package com.demo.example.emoji.librate;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;

import com.demo.example.R;


public class ScaleRatingBar extends BaseRatingBar {
    private static Handler sUiHandler = new Handler();

    public ScaleRatingBar(Context context) {
        super(context);
    }

    public ScaleRatingBar(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ScaleRatingBar(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override 
    protected void emptyRatingBar() {
        sUiHandler.removeCallbacksAndMessages(null);
        int i = 0;
        for (final PartialView partialView : this.mPartialViews) {
            i += 5;
            sUiHandler.postDelayed(new Runnable() { 
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
                            Animation loadAnimation = AnimationUtils.loadAnimation(ScaleRatingBar.this.getContext(), R.anim.scale_up);
                            Animation loadAnimation2 = AnimationUtils.loadAnimation(ScaleRatingBar.this.getContext(), R.anim.scale_down);
                            partialView.startAnimation(loadAnimation);
                            partialView.startAnimation(loadAnimation2);
                        }
                    }
                }, (long) i);
            }
        }
    }
}
