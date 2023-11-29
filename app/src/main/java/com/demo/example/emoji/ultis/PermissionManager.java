package com.demo.example.emoji.ultis;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;


public class PermissionManager {
    private static PermissionManager Intance;
    public String[] perSave = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    public static PermissionManager getIntance() {
        if (Intance == null) {
            Intance = new PermissionManager();
        }
        return Intance;
    }

    public boolean hasReadExternal(Context context) {
        if (Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public boolean hasWriteExternal(Context context) {
        if (Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public boolean hasCamera(Context context) {
        if (Build.VERSION.SDK_INT < 23 || context.checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public void requestPermision(String[] strArr, Activity activity) {
        if (Build.VERSION.SDK_INT >= 23) {
            for (String str : strArr) {
                if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    activity.requestPermissions(strArr, 11);
                }
            }
        }
    }

    public void requestPermission(String str, Activity activity) {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(activity, str) != 0) {
            activity.requestPermissions(new String[]{str}, 12);
        }
    }
}
