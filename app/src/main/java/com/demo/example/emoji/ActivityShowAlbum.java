package com.demo.example.emoji;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.demo.example.R;
import com.demo.example.emoji.adapter.AdapterPreViewAlbum;
import com.demo.example.emoji.adapter.PhotoShowPager;
import com.demo.example.emoji.item.ItemPhoto;
import com.demo.example.emoji.ultis.Constant;

import java.io.File;
import java.util.ArrayList;


public class ActivityShowAlbum extends AppCompatActivity implements View.OnClickListener {
    private AdapterPreViewAlbum adapterPreViewAlbum;
    private ArrayList<ItemPhoto> arrPhoto;
    PhotoShowPager photoShowPager;
    private int pos;
    private RecyclerView rvPreview;
    private TextView textViewTitle;
    private ViewPager viewPager;


    @Override

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_show_album);


        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.BannerAd((RelativeLayout) findViewById(R.id.bannerAd), this);
        adAdmob.FullscreenAd(this);

        Intent intent = getIntent();
        if (intent != null) {
            this.arrPhoto = (ArrayList) intent.getSerializableExtra(Constant.ARR);
            this.pos = intent.getIntExtra(Constant.POS, 0);
            if (this.arrPhoto == null) {
                finish();
                return;
            }
            this.arrPhoto.get(this.pos).setChoose(true);
            initView();
            return;
        }
        finish();
    }

    private void initView() {
        findViewById(R.id.imgDelete).setOnClickListener(this);
        findViewById(R.id.imgShare).setOnClickListener(this);
        this.textViewTitle = (TextView) findViewById(R.id.textTitle);
        this.viewPager = (ViewPager) findViewById(R.id.homepage_card_view_pager);
        this.photoShowPager = new PhotoShowPager(this, this.arrPhoto);
        this.viewPager.setAdapter(this.photoShowPager);
        this.viewPager.setCurrentItem(this.pos);
        this.viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityShowAlbum.this.rvPreview.getVisibility() == View.GONE) {
                    ActivityShowAlbum.this.rvPreview.setVisibility(View.VISIBLE);
                } else {
                    ActivityShowAlbum.this.rvPreview.setVisibility(View.GONE);
                }
            }
        });
        this.viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                ActivityShowAlbum.this.changeImageChoose(i);
            }
        });
        this.rvPreview = (RecyclerView) findViewById(R.id.rv_show_album);
        this.rvPreview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.adapterPreViewAlbum = new AdapterPreViewAlbum(new AdapterPreViewAlbum.PreViewCallBack() {
            @Override
            public void callBack(int i) {
                ActivityShowAlbum.this.changeImageChoose(i);
            }
        }, this.arrPhoto, this);
        this.rvPreview.setAdapter(this.adapterPreViewAlbum);
        TextView textView = this.textViewTitle;
        textView.setText((this.viewPager.getCurrentItem() + 1) + " / " + this.arrPhoto.size());
    }


    public void changeImageChoose(int i) {
        int i2 = 0;
        while (true) {
            if (i2 >= this.arrPhoto.size()) {
                break;
            } else if (this.arrPhoto.get(i2).isChoose()) {
                this.arrPhoto.get(i2).setChoose(false);
                break;
            } else {
                i2++;
            }
        }
        this.arrPhoto.get(i).setChoose(true);
        this.viewPager.setCurrentItem(i);
        this.adapterPreViewAlbum.notifyDataSetChanged();
        this.textViewTitle.setText((this.viewPager.getCurrentItem() + 1) + " / " + this.arrPhoto.size());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imgDelete) {
            showConfirmRemoveEmoji(this.viewPager.getCurrentItem());
        } else if (id == R.id.imgShare) {
            sharePhoto(this.viewPager.getCurrentItem());
        }
    }

    void showConfirmRemoveEmoji(final int i) {
        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.app_name)).setMessage(R.string.do_you_want_delete).setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i2) {
                if (new File(((ItemPhoto) ActivityShowAlbum.this.arrPhoto.get(i)).getLink()).delete()) {
                    ActivityShowAlbum.this.arrPhoto.remove(i);
                    ActivityShowAlbum.this.photoShowPager.notifyDataSetChanged();
                    ActivityShowAlbum.this.adapterPreViewAlbum.notifyDataSetChanged();
                    Toast.makeText(ActivityShowAlbum.this, "Emoji removed", Toast.LENGTH_SHORT).show();
                    if (ActivityShowAlbum.this.arrPhoto.size() == 0) {
                        ActivityShowAlbum.this.finish();
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

    void sharePhoto(int i) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.SUBJECT", "Emoji maker for phone");
        intent.putExtra("android.intent.extra.TEXT", "My emoji created");
        Uri uriForFile = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", new File(this.arrPhoto.get(i).getLink()));
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        startActivity(Intent.createChooser(intent, "Share this photo"));
        intent.putExtra("android.intent.extra.STREAM", uriForFile);
        startActivity(Intent.createChooser(intent, "Share"));
    }
}
