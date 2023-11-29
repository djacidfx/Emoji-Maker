package com.demo.example.emoji.ultis;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import com.demo.example.emoji.ActivityEmojiMaker;
import com.demo.example.emoji.item.ItemPhoto;
import com.demo.example.emoji.models.EmojiObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


public class UltilsMethod {
    private Context context;

    public UltilsMethod(Context context) {
        this.context = context;
    }

    public String SaveImage(Context context, Bitmap bitmap, String str) {
        String str2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/" + Constant.MYFOLDER + "/" + str;
        File file = new File(str2);
        if (!file.exists()) {
            file.mkdirs();
        }
        String str3 = str + Calendar.getInstance().getTime().getTime() + ".png";
        String str4 = str2 + "/" + str3;
        File file2 = new File(file, str3);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            refreshGallery(context, file2);
            return str4;
        } catch (Exception unused) {
            return "";
        }
    }

    public void refreshGallery(Context context, File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile(file));
        context.sendBroadcast(intent);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor query = this.context.getContentResolver().query(uri, null, null, null, null);
        if (query == null) {
            return uri.getPath();
        }
        query.moveToFirst();
        return query.getString(query.getColumnIndex("_data"));
    }

    public Bitmap getBitmapFromAsset(Context context, String str) {
        try {
            return BitmapFactory.decodeStream(context.getAssets().open(str));
        } catch (IOException unused) {
            return null;
        }
    }

    public int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public ArrayList<EmojiObject> getPathEmojiFromAsstes(String str, int i) {
        AssetManager assets = this.context.getAssets();
        ArrayList<EmojiObject> arrayList = new ArrayList<>();
        try {
            String[] list = assets.list(str);
            if (str.contains("EmojiUser")) {
                for (String str2 : list) {
                    arrayList.add(new EmojiObject(Constant.FILE_ASSET + str + "/" + str2, i, false));
                }
            } else {
                for (int i2 = 0; i2 < list.length; i2++) {
                    if (i2 < list.length / 2) {
                        arrayList.add(new EmojiObject(Constant.FILE_ASSET + str + "/" + list[i2], i, false));
                    } else {
                        arrayList.add(new EmojiObject(Constant.FILE_ASSET + str + "/" + list[i2], i, true));
                    }
                }
            }
            return arrayList;
        } catch (IOException unused) {
            return null;
        }
    }

    public static Bitmap overlay(Bitmap bitmap, Bitmap bitmap2, float f, float f2) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(bitmap, new Matrix(), null);
        canvas.drawBitmap(bitmap2, f, f2, (Paint) null);
        return createBitmap;
    }

    
    public ArrayList<String> getAllNameImageByType(Context context, String str) {
        String[] strArr;
        ArrayList<String> arrayList = new ArrayList<>();
        String[] strArr2 = new String[0];
        try {
            strArr = context.getAssets().list(str);
        } catch (IOException e) {
            e.printStackTrace();
            strArr = strArr2;
        }
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = str + "/" + strArr[i];
        }
        for (int i2 = 0; i2 < Arrays.asList(strArr).size(); i2++) {
            arrayList.add(Arrays.asList(strArr).get(i2));
        }
        return arrayList;
    }

    public void generateBitmap(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, (Paint) null);
        view.draw(canvas);
        new SaveBitmap(this.context, createBitmap, Constant.FOLDER_EMOJI).execute(new Void[0]);
    }

    public List<EmojiObject> getExternalCacheDir(Context context, int i) {
        ArrayList arrayList = new ArrayList();
        File file = new File(context.getExternalCacheDir().toString() + "/" + Constant.NAME_FOLDER_SAVE);
        if (!file.exists()) {
            file.mkdir();
        } else {
            try {
                for (File file2 : file.listFiles()) {
                    arrayList.add(new EmojiObject(file2.getPath(), i, true));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public ArrayList<ItemPhoto> getExternalCacheDir2(Context context, int i) {
        ArrayList<ItemPhoto> arrayList = new ArrayList<>();
        File file = new File(context.getExternalCacheDir().toString() + "/" + Constant.NAME_FOLDER_SAVE);
        if (!file.exists()) {
            file.mkdir();
        } else {
            try {
                for (File file2 : file.listFiles()) {
                    arrayList.add(new ItemPhoto(file2.getPath(), false));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(arrayList);
        return arrayList;
    }

    public void saveImage(Activity activity, View view, SharedPreferences sharedPreferences) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(createBitmap, 0.0f, 0.0f, (Paint) null);
        view.draw(canvas);
        File file = new File(activity.getExternalCacheDir().toString() + "/" + Constant.NAME_FOLDER_SAVE);
        if (!file.exists()) {
            file.mkdir();
        }
        String absolutePath = file.getAbsolutePath();
        File file2 = new File(absolutePath, "IMG_" + System.currentTimeMillis() + ".jpg");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            createBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            Intent intent = new Intent(activity, ActivityEmojiMaker.class);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putBoolean(Constant.FROM_SMILEYS, true);
            edit.apply();
            intent.putExtra("hihi", file2.getAbsolutePath());
            activity.onBackPressed();
            activity.finish();
            try {
                fileOutputStream.flush();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
            try {
                fileOutputStream.close();
            } catch (IOException e3) {
                e3.printStackTrace();
            }
        } catch (FileNotFoundException e4) {
            e4.printStackTrace();
        }
    }

    
    class SaveBitmap extends AsyncTask<Void, Integer, Void> {
        Bitmap bitmap;
        Context ctx;
        String folder;

        public SaveBitmap(Context context, Bitmap bitmap, String str) {
            this.ctx = context;
            this.bitmap = bitmap;
            this.folder = str;
        }

        
        public Void doInBackground(Void... voidArr) {
            String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File file2 = new File(file + "/" + Constant.MYFOLDER + "/" + this.folder);
            if (!file2.exists()) {
                file2.mkdirs();
            }
            File file3 = new File(file2, this.folder + Calendar.getInstance().getTime().getTime() + ".png");
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file3);
                this.bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                UltilsMethod.this.refreshGallery(UltilsMethod.this.context, file3);
                return null;
            } catch (Exception unused) {
                return null;
            }
        }

        
        public void onPostExecute(Void r1) {
            super.onPostExecute( r1);
        }
    }

    @SuppressLint("WrongConstant")
    public void rateApp(Context context) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getPackageName()));
        intent.addFlags(1208483840);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException unused) {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }

    public Bitmap blurRenderScript(Bitmap bitmap, int i, Context context) {
        try {
            bitmap = RGB565toARGB888(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript create = RenderScript.create(context);
        Allocation createFromBitmap = Allocation.createFromBitmap(create, bitmap);
        Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
        ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
        create2.setInput(createFromBitmap);
        create2.setRadius((float) i);
        create2.forEach(createFromBitmap2);
        createFromBitmap2.copyTo(createBitmap);
        create.destroy();
        return createBitmap;
    }

    private Bitmap RGB565toARGB888(Bitmap bitmap) throws Exception {
        int[] iArr = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(iArr, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        createBitmap.setPixels(iArr, 0, createBitmap.getWidth(), 0, 0, createBitmap.getWidth(), createBitmap.getHeight());
        return createBitmap;
    }

    public void showHideView(View view, ArrayList<View> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (view == arrayList.get(i)) {
                arrayList.get(i).setVisibility(View.VISIBLE);
            } else {
                arrayList.get(i).setVisibility(View.GONE);
            }
        }
    }

    public void sendFeedback(Context context, String str) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/email");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{"tuannv@ecomobi.com"});
        intent.putExtra("android.intent.extra.SUBJECT", "Diy Emoji Feedback!");
        intent.putExtra("android.intent.extra.TEXT", str);
        context.startActivity(Intent.createChooser(intent, "Send Feedback:"));
    }

    public void SaveInt(SharedPreferences sharedPreferences, String str, int i) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public void SaveBoolean(SharedPreferences sharedPreferences, String str, boolean z) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(str, z);
        edit.apply();
    }
}
