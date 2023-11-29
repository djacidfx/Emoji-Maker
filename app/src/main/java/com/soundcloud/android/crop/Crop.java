package com.soundcloud.android.crop;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.demo.example.R;

public class Crop {

    public static final int REQUEST_CROP = 6709;
    public static final int REQUEST_PICK = 9162;
    public static final int RESULT_ERROR = 404;

    interface Extra {
        String ASPECT_X = "aspect_x";
        String ASPECT_Y = "aspect_y";
        String MAX_X = "max_x";
        String MAX_Y = "max_y";
        String AS_PNG = "as_png";
        String ERROR = "error";
    }

    private Intent cropIntent;

    
    public static Crop of(Uri source, Uri destination) {
        return new Crop(source, destination);
    }

    private Crop(Uri source, Uri destination) {
        cropIntent = new Intent();
        cropIntent.setData(source);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, destination);
    }

    
    public Crop withAspect(int x, int y) {
        cropIntent.putExtra(Extra.ASPECT_X, x);
        cropIntent.putExtra(Extra.ASPECT_Y, y);
        return this;
    }

    
    public Crop asSquare() {
        cropIntent.putExtra(Extra.ASPECT_X, 1);
        cropIntent.putExtra(Extra.ASPECT_Y, 1);
        return this;
    }

    
    public Crop withMaxSize(int width, int height) {
        cropIntent.putExtra(Extra.MAX_X, width);
        cropIntent.putExtra(Extra.MAX_Y, height);
        return this;
    }

    
    public Crop asPng(boolean asPng) {
        cropIntent.putExtra(Extra.AS_PNG, asPng);
        return this;
    }

    
    public void start(Activity activity) {
        start(activity, REQUEST_CROP);
    }

    
    public void start(Activity activity, int requestCode) {
        activity.startActivityForResult(getIntent(activity), requestCode);
    }

    
    public void start(Context context, Fragment fragment) {
        start(context, fragment, REQUEST_CROP);
    }


    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void start(Context context, Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getIntent(context), requestCode);
    }

    


    
    public Intent getIntent(Context context) {
        cropIntent.setClass(context, CropImageActivity.class);
        return cropIntent;
    }

    
    public static Uri getOutput(Intent result) {
        return result.getParcelableExtra(MediaStore.EXTRA_OUTPUT);
    }

    
    public static Throwable getError(Intent result) {
        return (Throwable) result.getSerializableExtra(Extra.ERROR);
    }

    
    public static void pickImage(Activity activity) {
        pickImage(activity, REQUEST_PICK);
    }

    
    public static void pickImage(Context context, Fragment fragment) {
        pickImage(context, fragment, REQUEST_PICK);
    }


    
    public static void pickImage(Activity activity, int requestCode) {
        try {
            activity.startActivityForResult(getImagePicker(), requestCode);
        } catch (ActivityNotFoundException e) {
            showImagePickerError(activity);
        }
    }

    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void pickImage(Context context, Fragment fragment, int requestCode) {
        try {
            fragment.startActivityForResult(getImagePicker(), requestCode);
        } catch (ActivityNotFoundException e) {
            showImagePickerError(context);
        }
    }



    private static Intent getImagePicker() {
        return new Intent(Intent.ACTION_GET_CONTENT).setType("image/*");
    }

    private static void showImagePickerError(Context context) {
        Toast.makeText(context.getApplicationContext(), R.string.crop__pick_error, Toast.LENGTH_SHORT).show();
    }

}
