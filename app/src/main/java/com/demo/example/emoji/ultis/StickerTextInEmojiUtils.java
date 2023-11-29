package com.demo.example.emoji.ultis;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.RelativeLayout;

import com.demo.example.R;
import com.demo.example.emoji.stickers.BubbleTextView;
import com.demo.example.emoji.stickers.StickerView;

import java.io.IOException;
import java.util.ArrayList;


public class StickerTextInEmojiUtils {
    public static BubbleTextView mCurrentEditTextView;
    public static StickerView mCurrentView;
    private int index;
    private RelativeLayout mContentRootView;
    private Context mContext;
    private ArrayList<View> mViews;
    private UltilsMethod ultilsMethod;

    public StickerTextInEmojiUtils(Context context) {
        this.mContext = context;
        this.ultilsMethod = new UltilsMethod(this.mContext);
    }

    public void addBubbleText(Context context, String str, RelativeLayout relativeLayout, int i) {
        this.mContentRootView = relativeLayout;
        this.mViews = new ArrayList<>();
        final BubbleTextView bubbleTextView = new BubbleTextView(context, -16777216, 0);
        bubbleTextView.setImageResource(R.drawable.bubble_7_rb);
        bubbleTextView.setText(str);
        bubbleTextView.setColor(-16777216);
        bubbleTextView.setOperationListener(new BubbleTextView.OperationListener() { 
            @Override 
            public void onClick(BubbleTextView bubbleTextView2) {
            }

            @Override 
            public void onTop(BubbleTextView bubbleTextView2) {
            }

            @Override 
            public void onDeleteClick() {
                StickerTextInEmojiUtils.this.mViews.remove(bubbleTextView);
                StickerTextInEmojiUtils.this.mContentRootView.removeView(bubbleTextView);
            }

            @Override 
            public void onEdit(BubbleTextView bubbleTextView2) {
                if (StickerTextInEmojiUtils.mCurrentView != null) {
                    StickerTextInEmojiUtils.mCurrentView.setInEdit(false);
                }
                StickerTextInEmojiUtils.mCurrentEditTextView.setInEdit(false);
                StickerTextInEmojiUtils.mCurrentEditTextView = bubbleTextView2;
                StickerTextInEmojiUtils.mCurrentEditTextView.setInEdit(true);
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (this.mContentRootView.getChildCount() != 1) {
            this.mContentRootView.removeViewAt(1);
            this.mContentRootView.addView(bubbleTextView, 1, layoutParams);
            setCurrentEditText(bubbleTextView);
            return;
        }
        this.mViews.add(bubbleTextView);
        this.mContentRootView.addView(bubbleTextView, layoutParams);
        setCurrentEditText(bubbleTextView);
    }

    public void addStickerView(Context context, String str, RelativeLayout relativeLayout, int i, int i2) {
        this.mContentRootView = relativeLayout;
        this.mViews = new ArrayList<>();
        final StickerView stickerView = new StickerView(context);
        try {
            stickerView.setBitmap(BitmapFactory.decodeStream(context.getAssets().open(str.substring(22, str.length()))), i2, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stickerView.setIndexImage(i);
        stickerView.setOperationListener(new StickerView.OperationListener() { 
            @Override 
            public void onDeleteClick() {
                StickerTextInEmojiUtils.this.mViews.remove(stickerView);
                StickerTextInEmojiUtils.this.mContentRootView.removeView(stickerView);
            }

            @Override 
            public void onEdit(StickerView stickerView2) {
                if (StickerTextInEmojiUtils.mCurrentEditTextView != null) {
                    StickerTextInEmojiUtils.mCurrentEditTextView.setInEdit(false);
                }
                StickerTextInEmojiUtils.mCurrentView.setInEdit(false);
                StickerTextInEmojiUtils.mCurrentView = stickerView2;
                StickerTextInEmojiUtils.mCurrentView.setInEdit(true);
            }

            @Override 
            public void onTop(StickerView stickerView2) {
                int indexOf = StickerTextInEmojiUtils.this.mViews.indexOf(stickerView2);
                if (indexOf != StickerTextInEmojiUtils.this.mViews.size() - 1) {
                    StickerTextInEmojiUtils.this.mViews.add(StickerTextInEmojiUtils.this.mViews.size(), (StickerView) StickerTextInEmojiUtils.this.mViews.remove(indexOf));
                }
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (this.mContentRootView.getChildCount() != 0) {
            this.mContentRootView.removeViewAt(0);
            this.mContentRootView.addView(stickerView, 0, layoutParams);
            setCurrentEdit(stickerView);
            return;
        }
        this.mViews.add(stickerView);
        this.mContentRootView.addView(stickerView, layoutParams);
        setCurrentEdit(stickerView);
    }

    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentView = stickerView;
    }

    public static void setInEdit() {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
    }

    private void setCurrentEditText(BubbleTextView bubbleTextView) {
        if (mCurrentEditTextView != null) {
            mCurrentEditTextView.setInEdit(false);
        }
        mCurrentEditTextView = bubbleTextView;
        bubbleTextView.setInEdit(true);
    }
}
