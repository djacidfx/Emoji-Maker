package com.demo.example.emoji;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.core.app.ActivityCompat;

import com.demo.example.R;
import com.demo.example.emoji.dialog.DialogLoading;
import com.demo.example.emoji.librate.FeedbackDialog;
import com.demo.example.emoji.librate.RatingDialog;
import com.demo.example.emoji.ultis.Constant;
import com.demo.example.emoji.ultis.PermissionManager;
import com.demo.example.emoji.ultis.UltilsMethod;


public class MainActivity extends Activity implements View.OnClickListener {
    public static DialogLoading dialogLoading;
    private FeedbackDialog feedbackDialog;
    private LinearLayout layoutContrainButton;

    private RatingDialog mRatingDialog;
    private SharedPreferences sharedPreferences;
    private UltilsMethod ultilsMethod;

    private void findViews() {
        findViewById(R.id.btnEmojiMacker).setOnClickListener(this);
        findViewById(R.id.btnMyEmoji).setOnClickListener(this);
        findViewById(R.id.imgTextInEmoji).setOnClickListener(this);

        if (!PermissionManager.getIntance().hasReadExternal(MainActivity.this) || !PermissionManager.getIntance().hasWriteExternal(MainActivity.this) || !PermissionManager.getIntance().hasCamera(MainActivity.this)) {
            MainActivity.this.requestPermission();
        }

        this.layoutContrainButton = (LinearLayout) findViewById(R.id.layoutContrainButton);
        this.sharedPreferences = getSharedPreferences(Constant.NAME_SHAREDPREFERENCES, 0);
        this.ultilsMethod = new UltilsMethod(this);
        this.mRatingDialog = new RatingDialog(this);
        this.mRatingDialog.setRatingDialogListener(new RatingDialog.RatingDialogInterFace() {
            @Override
            public void maybe() {
            }

            @Override
            public void onDismiss() {
            }

            @Override
            public void onRatingChanged(float f) {
            }

            @Override
            public void onSubmit(float f, boolean z) {
                if (f <= 3.0f) {
                    MainActivity.this.feedbackDialog.showDialog(false, MainActivity.this);
                    return;
                }
                MainActivity.this.ultilsMethod.rateApp(MainActivity.this);
                SharedPreferences.Editor edit = MainActivity.this.sharedPreferences.edit();
                edit.putBoolean(Constant.RATE_APP, true);
                edit.apply();
            }
        });
        this.feedbackDialog = new FeedbackDialog(this);
        this.feedbackDialog.setFeedbackDialogListener(new FeedbackDialog.FeedbackDialogInterFace() {
            @Override
            public void onDismiss() {
            }

            @Override
            public void onSubmit(String str, boolean z) {
                if (!str.equalsIgnoreCase("")) {
                    MainActivity.this.ultilsMethod.sendFeedback(MainActivity.this, str);
                }
            }
        });
        findViewById(R.id.gifRate).setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_main);
        findViews();
        this.layoutContrainButton.getLayoutParams().height = ((getResources().getDisplayMetrics().widthPixels / 2) * 211) / 316;

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEmojiMacker:
                dialogLoading = new DialogLoading(this, "Loading...");
                dialogLoading.show();
                startActivity(new Intent(this, ActivityEmojiMaker.class));
                return;
            case R.id.btnMyEmoji:
                startActivity(new Intent(this, ActivityAlbum.class));
                return;
            case R.id.gifRate:
                this.mRatingDialog.showDialog(false, this);
                return;

            case R.id.imgTextInEmoji:
                startActivity(new Intent(this, EmojiShopActivity.class));
                return;
            default:
                return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putBoolean(Constant.FROM_SMILEYS, false);
        edit.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!PermissionManager.getIntance().hasCamera(this) && ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 1);
            }
            if (!PermissionManager.getIntance().hasWriteExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
            }
            if (!PermissionManager.getIntance().hasReadExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 3);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (iArr.length == 1 && iArr[0] != 0) {
            super.onRequestPermissionsResult(i, strArr, iArr);
        }
    }

}
