package com.demo.example.emoji;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.demo.example.R;
import com.demo.example.emoji.adapter.AlbumAdapter;
import com.demo.example.emoji.customview.RobotoRegularTextView;
import com.demo.example.emoji.item.ItemPhoto;
import com.demo.example.emoji.ultis.UltilsMethod;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class ActivityMySmileys extends Activity implements View.OnClickListener {
    private AlbumAdapter albumAdapter;
    private ArrayList<ItemPhoto> arrPhoto;
    private GridView gvSmileys;
    private boolean isDelete;
    private RobotoRegularTextView tvNoPhoto;
    private UltilsMethod ultilsMethod;

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_layout_my_smileys);




        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);


        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.imgDelete).setOnClickListener(this);
        this.tvNoPhoto = (RobotoRegularTextView) findViewById(R.id.tvNoPhoto);
        this.gvSmileys = (GridView) findViewById(R.id.gvSmileys);
        this.arrPhoto = new ArrayList<>();
        this.ultilsMethod = new UltilsMethod(this);
        this.arrPhoto = this.ultilsMethod.getExternalCacheDir2(this, 19);
        if (this.arrPhoto.size() == 0 || this.arrPhoto == null) {
            this.tvNoPhoto.setVisibility(View.VISIBLE);
            this.gvSmileys.setVisibility(View.GONE);
            return;
        }
        this.tvNoPhoto.setVisibility(View.GONE);
        this.gvSmileys.setVisibility(View.VISIBLE);
        this.albumAdapter = new AlbumAdapter(this, this.arrPhoto);
        this.gvSmileys.setAdapter((ListAdapter) this.albumAdapter);
        this.gvSmileys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (ActivityMySmileys.this.isDelete) {
                    ((ItemPhoto) ActivityMySmileys.this.arrPhoto.get(i)).setChoose(!((ItemPhoto) ActivityMySmileys.this.arrPhoto.get(i)).isChoose());
                    ActivityMySmileys.this.albumAdapter.notifyDataSetChanged();
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
        } else if (id == R.id.imgDelete && this.albumAdapter != null) {
            if (!this.isDelete) {
                this.isDelete = true;
                for (int i = 0; i < this.arrPhoto.size(); i++) {
                    this.arrPhoto.get(i).setChoose(false);
                }
                this.albumAdapter.setWallpaper(true);
                this.albumAdapter.notifyDataSetChanged();
                return;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<ItemPhoto> it2 = this.arrPhoto.iterator();
            while (it2.hasNext()) {
                ItemPhoto next = it2.next();
                if (next.isChoose()) {
                    arrayList.add(next.getLink());
                }
            }
            new ArrayList();
            ArrayList<ItemPhoto> arrayList2 = (ArrayList) this.arrPhoto.clone();
            Iterator<ItemPhoto> it3 = this.arrPhoto.iterator();
            while (it3.hasNext()) {
                ItemPhoto next2 = it3.next();
                if (next2.isChoose()) {
                    arrayList2.remove(next2);
                }
            }
            this.arrPhoto = arrayList2;
            if (arrayList.size() == 0) {
                Toast.makeText(this, "Please choose image to delete", Toast.LENGTH_SHORT).show();
                return;
            }
            this.isDelete = false;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                File file = new File((String) arrayList.get(i2));
                file.delete();
                sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
            }
            Toast.makeText(this, getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
            this.albumAdapter.setWallpaper(false);
            this.albumAdapter.setDataChanges(this.arrPhoto);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.isDelete) {
            this.isDelete = false;
            this.albumAdapter.setWallpaper(false);
            this.albumAdapter.notifyDataSetChanged();
            return;
        }
        super.onBackPressed();
    }
}
