package com.demo.example.emoji.librate;

import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;


interface SimpleRatingBar {
    int getNumStars();

    float getRating();

    int getStarPadding();

    void setEmptyDrawable(Drawable drawable);

    void setEmptyDrawableRes(@DrawableRes int i);

    void setFilledDrawable(Drawable drawable);

    void setFilledDrawableRes(@DrawableRes int i);

    void setNumStars(int i);

    void setRating(float f);

    void setStarPadding(int i);
}
