package com.demo.example.emoji;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.example.R;
import com.demo.example.emoji.adapter.AlbumAdapter;
import com.demo.example.emoji.item.ItemPhoto;
import com.demo.example.emoji.librate.FeedbackDialog;
import com.demo.example.emoji.librate.RatingDialog;
import com.demo.example.emoji.ultis.Constant;
import com.demo.example.emoji.ultis.UltilsMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


public class ActivityAlbum extends AppCompatActivity implements View.OnClickListener {
    private AlbumAdapter adapterEmoji;
    private ArrayList<ItemPhoto> arrEmoji;
    private FeedbackDialog feedbackDialog;
    private GridView gvEmoji;
    private ImageView imgDelete;
    boolean isDelete = false;
    private RatingDialog mRatingDialog;
    private SharedPreferences sharedPreferences;
    private TextView tvEntryEmoji;
    private TextView tvOk;
    private TextView tvWallpaper;
    private UltilsMethod ultilsMethod;


    @Override

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_album);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.ad_holder), this);
        adAdmob.FullscreenAd(this);


        getData();
        initView();
    }

    private void getData() {
        if (this.arrEmoji == null) {
            this.arrEmoji = new ArrayList<>();
        } else {
            this.arrEmoji.clear();
        }
        try {
            for (File file : new File((Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + Constant.MYFOLDER + "/") + Constant.FOLDER_EMOJI).listFiles()) {
                this.arrEmoji.add(new ItemPhoto(file.getPath(), false));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.reverse(this.arrEmoji);
    }

    private void initView() {
        this.ultilsMethod = new UltilsMethod(this);
        this.sharedPreferences = getSharedPreferences(Constant.NAME_SHAREDPREFERENCES, 0);
        this.tvEntryEmoji = (TextView) findViewById(R.id.tv_entry_emoji);
        this.tvWallpaper = (TextView) findViewById(R.id.tv_set_wallpaper);
        this.imgDelete = (ImageView) findViewById(R.id.imgDelete);
        this.tvOk = (TextView) findViewById(R.id.tv_ok);
        this.tvWallpaper.setOnClickListener(this);
        this.tvOk.setOnClickListener(this);
        modeEmoji();
        findViewById(R.id.imgBack).setOnClickListener(this);
        this.imgDelete.setOnClickListener(this);
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
                    ActivityAlbum.this.feedbackDialog.showDialog(false, ActivityAlbum.this);
                    return;
                }
                ActivityAlbum.this.ultilsMethod.rateApp(ActivityAlbum.this);
                SharedPreferences.Editor edit = ActivityAlbum.this.sharedPreferences.edit();
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
                    ActivityAlbum.this.ultilsMethod.sendFeedback(ActivityAlbum.this, str);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imgBack) {
            onBackPressed();
            finish();
        } else if (id == R.id.imgDelete) {
            buttonDeleteClicked();
        }
    }

    private void modeEmoji() {
        this.adapterEmoji = new AlbumAdapter(this, this.arrEmoji);
        this.gvEmoji = (GridView) findViewById(R.id.gv_emoji);
        this.gvEmoji.setAdapter((ListAdapter) this.adapterEmoji);
        this.gvEmoji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (ActivityAlbum.this.isDelete) {
                    ActivityAlbum.this.showConfirmRemoveEmoji(i);
                } else {
                    ActivityAlbum.this.showActivity(ActivityAlbum.this.arrEmoji, i);
                }
            }
        });
        if (this.arrEmoji.size() == 0) {
            this.tvEntryEmoji.setVisibility(View.VISIBLE);
            this.gvEmoji.setVisibility(View.GONE);
            this.imgDelete.setVisibility(View.GONE);
            return;
        }
        this.tvEntryEmoji.setVisibility(View.GONE);
    }


    public void showActivity(ArrayList<ItemPhoto> arrayList, int i) {
        Intent intent = new Intent(this, ActivityShowAlbum.class);
        intent.putExtra(Constant.POS, i);
        intent.putExtra(Constant.ARR, arrayList);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        this.feedbackDialog.dismissDialog();
        if (this.tvOk.getVisibility() == View.VISIBLE) {
            this.tvOk.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    void buttonDeleteClicked() {
        this.isDelete = !this.isDelete;
        this.imgDelete.setImageResource(this.isDelete ? R.drawable.icon_complete : R.drawable.btn_delete);
        this.adapterEmoji.setDelete(this.isDelete);
        this.adapterEmoji.notifyDataSetChanged();
    }

    void showConfirmRemoveEmoji(final int i) {
        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.app_name)).setMessage(R.string.do_you_want_delete).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (new File(((ItemPhoto) ActivityAlbum.this.arrEmoji.get(i)).getLink()).delete()) {
                    ActivityAlbum.this.arrEmoji.remove(i);
                    ActivityAlbum.this.adapterEmoji.notifyDataSetChanged();
                    Toast.makeText(ActivityAlbum.this, "Emoji removed", Toast.LENGTH_SHORT).show();
                    if (ActivityAlbum.this.arrEmoji.size() == 0) {
                        ActivityAlbum.this.gvEmoji.setVisibility(View.GONE);
                        ActivityAlbum.this.tvEntryEmoji.setVisibility(View.VISIBLE);
                    }
                }
                dialogInterface.dismiss();
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i2) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
        if (this.adapterEmoji != null) {
            this.adapterEmoji.notifyDataSetChanged();
        }
        if (this.arrEmoji.size() == 0) {
            this.tvEntryEmoji.setVisibility(View.VISIBLE);
            this.gvEmoji.setVisibility(View.GONE);
            this.imgDelete.setVisibility(View.GONE);
            return;
        }
        this.tvEntryEmoji.setVisibility(View.GONE);
    }
}
