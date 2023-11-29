package com.demo.example.emoji.stickers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.RelativeLayout;
import com.demo.example.emoji.models.StickerListModel;
import com.demo.example.emoji.ultis.UltilsMethod;
import java.io.IOException;
import java.util.ArrayList;

public class StickerUtils {
    public static StickerView mCurrentView;
    private int index;
    private RelativeLayout mContentRootView;
    private Context mContext;
    private BubbleTextView mCurrentEditTextView;
    private ArrayList<View> mViews;
    private UltilsMethod ultilsMethod;

    public StickerUtils(Context context) {
        this.mContext = context;
        this.ultilsMethod = new UltilsMethod(this.mContext);
    }

    public void addStickerView(Context context, String str, RelativeLayout relativeLayout, int i, int i2, ArrayList<StickerListModel> arrayList) {
        this.mContentRootView = relativeLayout;
        this.mViews = new ArrayList<>();
        final StickerView stickerView = new StickerView(context);
        if (i != 2) {
            try {
                stickerView.setBitmap(BitmapFactory.decodeStream(context.getAssets().open(str.substring(22, str.length()))), i2, true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            stickerView.setBitmap(BitmapFactory.decodeFile(str), i2, true);
        }
        stickerView.setIndexImage(i);
        stickerView.setOperationListener(new StickerView.OperationListener() { 
            @Override 
            public void onDeleteClick() {
                StickerUtils.this.mViews.remove(stickerView);
                StickerUtils.this.mContentRootView.removeView(stickerView);
            }

            @Override 
            public void onEdit(StickerView stickerView2) {
                if (StickerUtils.this.mCurrentEditTextView != null) {
                    StickerUtils.this.mCurrentEditTextView.setInEdit(false);
                }
                StickerUtils.mCurrentView.setInEdit(false);
                StickerUtils.mCurrentView = stickerView2;
                StickerUtils.mCurrentView.setInEdit(true);
            }

            @Override 
            public void onTop(StickerView stickerView2) {
                int indexOf = StickerUtils.this.mViews.indexOf(stickerView2);
                if (indexOf != StickerUtils.this.mViews.size() - 1) {
                    StickerUtils.this.mViews.add(StickerUtils.this.mViews.size(), (StickerView) StickerUtils.this.mViews.remove(indexOf));
                }
            }
        });
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        int typeImage = getTypeImage(i);
        if (typeImage != -1) {
            this.mContentRootView.removeViewAt(typeImage);
            this.mContentRootView.addView(stickerView, typeImage, layoutParams);
            setCurrentEdit(stickerView);
            arrayList.remove(typeImage);
            arrayList.add(typeImage, new StickerListModel(stickerView, typeImage, i));
            return;
        }
        this.mViews.add(stickerView);
        this.mContentRootView.addView(stickerView, layoutParams);
        setCurrentEdit(stickerView);
        arrayList.add(new StickerListModel(stickerView, arrayList.size(), i));
    }

    private int getTypeImage(int i) {
        for (int i2 = 0; i2 < this.mContentRootView.getChildCount(); i2++) {
            int indexImg = ((StickerView) this.mContentRootView.getChildAt(i2)).getIndexImg();
            if (indexImg == i) {
                return i2;
            }
            if ((i == 0 && indexImg == 1) || ((i == 1 && indexImg == 0) || ((i == 2 && indexImg == 0) || ((i == 2 && indexImg == 1) || ((i == 1 && indexImg == 2) || ((i == 0 && indexImg == 2) || ((i == 3 && indexImg == 4) || ((i == 4 && indexImg == 3) || ((i == 5 && indexImg == 6) || ((indexImg == 5 && i == 6) || ((i == 9 && indexImg == 10) || (i == 10 && indexImg == 9)))))))))))) {
                return i2;
            }
        }
        return -1;
    }

    private void setCurrentEdit(StickerView stickerView) {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
        if (this.mCurrentEditTextView != null) {
            this.mCurrentEditTextView.setInEdit(false);
        }
        mCurrentView = stickerView;
    }

    public static void setInEdit() {
        if (mCurrentView != null) {
            mCurrentView.setInEdit(false);
        }
    }
}
