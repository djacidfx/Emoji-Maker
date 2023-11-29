package com.demo.example.emoji;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.christophesmet.android.views.maskableframelayout.MaskableFrameLayout;
import com.demo.example.R;
import com.demo.example.emoji.adapter.ShapeCutAdapter;
import com.demo.example.emoji.ultis.Constant;
import com.demo.example.emoji.ultis.PermissionManager;
import com.demo.example.emoji.ultis.TouchImageView;
import com.demo.example.emoji.ultis.UltilsMethod;

import it.sephiroth.android.library.widget.AdapterView;
import it.sephiroth.android.library.widget.HListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ActivityCustomizeSmileys extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    int CAMERA_PIC_REQUEST = 589;
    Uri file;
    private HListView hlvShapeDemo;
    private TouchImageView imgPhoto;
    private ImageView imgTrain;
    private List<String> listEmojiCut;
    private List<String> listShapeCutLarge;
    private List<String> listShapeCutThum;
    String mCurrentPhotoPath;
    private MaskableFrameLayout maskLayout;
    private ShapeCutAdapter shapeCutAdapter;
    private SharedPreferences sharedPreferences;
    private UltilsMethod ultilsMethod;

    private void findViews() {
        findViewById(R.id.imgBack).setOnClickListener(this);
        findViewById(R.id.imgSaveEmoji).setOnClickListener(this);
        findViewById(R.id.imgOpenAll).setOnClickListener(this);
        findViewById(R.id.imgOpenGallery).setOnClickListener(this);
        findViewById(R.id.imgOpenCamera).setOnClickListener(this);
        this.hlvShapeDemo = (HListView) findViewById(R.id.hlvShapeDemo);
        this.imgTrain = (ImageView) findViewById(R.id.imgTrain);
        this.imgPhoto = (TouchImageView) findViewById(R.id.imgPhoto);
        this.maskLayout = (MaskableFrameLayout) findViewById(R.id.maskLayout);
        this.sharedPreferences = getSharedPreferences(Constant.NAME_SHAREDPREFERENCES, 0);
        this.listShapeCutThum = new ArrayList();
        this.listShapeCutLarge = new ArrayList();
        this.ultilsMethod = new UltilsMethod(this);
        this.listShapeCutThum = this.ultilsMethod.getAllNameImageByType(this, "shape_cut/thum");
        this.listShapeCutLarge = this.ultilsMethod.getAllNameImageByType(this, "shape_cut/large");
        this.shapeCutAdapter = new ShapeCutAdapter(this.listShapeCutThum, this, 0);
        this.hlvShapeDemo.setAdapter((ListAdapter) this.shapeCutAdapter);
        this.hlvShapeDemo.setOnItemClickListener(this);
        this.maskLayout.setMask(new BitmapDrawable(getResources(), this.ultilsMethod.getBitmapFromAsset(this, this.listShapeCutLarge.get(0))));
    }

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_layout_customize_smileys);

        AdAdmob adAdmob = new AdAdmob(this);
        adAdmob.FullscreenAd(this);



        findViews();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgBack) {
            onBackPressed();
            finish();
        } else if (view.getId() == R.id.imgOpenGallery) {
            if (Build.VERSION.SDK_INT < 23) {
                openGallery();
            } else if (PermissionManager.getIntance().hasReadExternal(this)) {
                openGallery();
            } else if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 13);
            }
        } else if (view.getId() == R.id.imgSaveEmoji) {
            if (Build.VERSION.SDK_INT < 23) {
                this.ultilsMethod.saveImage(this, this.maskLayout, this.sharedPreferences);
            } else if (PermissionManager.getIntance().hasWriteExternal(this) && PermissionManager.getIntance().hasReadExternal(this)) {
                this.ultilsMethod.saveImage(this, this.maskLayout, this.sharedPreferences);
            } else if (Build.VERSION.SDK_INT >= 23) {
                if (!PermissionManager.getIntance().hasWriteExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                    requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
                }
                if (!PermissionManager.getIntance().hasReadExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                    requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 3);
                }
            }
        } else if (view.getId() == R.id.imgOpenAll) {
            startActivity(new Intent(this, ActivityMySmileys.class));
        }
        if (view.getId() != R.id.imgOpenCamera) {
            return;
        }
        if (Build.VERSION.SDK_INT < 23) {
            openCamera();
        } else if (PermissionManager.getIntance().hasCamera(this)) {
            openCamera();
        } else if (ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
            requestPermissions(new String[]{"android.permission.CAMERA"}, 12);
        }
    }

    private void openGallery() {
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, 1306);
    }

    private void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                this.file = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", createImageFile());
                intent.putExtra("output", this.file);
                startActivityForResult(intent, this.CAMERA_PIC_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != 589) {
            if (i == 1306) {
                if (i2 == -1) {
                    try {
                        this.imgPhoto.setVisibility(View.VISIBLE);
                        this.imgPhoto.setImageBitmap(modifyOrientation(BitmapFactory.decodeFile(this.ultilsMethod.getRealPathFromURI(intent.getData())), this.ultilsMethod.getRealPathFromURI(intent.getData())));
                        this.imgTrain.setVisibility(View.GONE);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                } else {
                    finish();
                    return;
                }
            }
        } else if (i2 == -1) {
            try {
                this.imgPhoto.setVisibility(View.VISIBLE);
                this.imgTrain.setVisibility(View.GONE);
                Glide.with((Activity) this).load(this.file).asBitmap().into(new SimpleTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        ActivityCustomizeSmileys.this.imgPhoto.setImageBitmap(bitmap);
                    }
                });

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            finish();
        }
        super.onActivityResult(i, i2, intent);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.shapeCutAdapter.setPositionChanges(i);
        this.maskLayout.setMask(new BitmapDrawable(getResources(), this.ultilsMethod.getBitmapFromAsset(this, this.listShapeCutLarge.get(i))));
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String str) throws IOException {
        int attributeInt = new ExifInterface(str).getAttributeInt("Orientation", 1);
        if (attributeInt == 6) {
            return rotate(bitmap, 90.0f);
        }
        if (attributeInt == 8) {
            return rotate(bitmap, 270.0f);
        }
        switch (attributeInt) {
            case 2:
                return flip(bitmap, true, false);
            case 3:
                return rotate(bitmap, 180.0f);
            case 4:
                return flip(bitmap, false, true);
            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float f) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean z, boolean z2) {
        Matrix matrix = new Matrix();
        float f = 1.0f;
        float f2 = z ? -1.0f : 1.0f;
        if (z2) {
            f = -1.0f;
        }
        matrix.preScale(f2, f);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    private File createImageFile() throws IOException {
        File createTempFile = File.createTempFile("JPEG_smiley_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.mCurrentPhotoPath = createTempFile.getAbsolutePath();
        return createTempFile;
    }
}
