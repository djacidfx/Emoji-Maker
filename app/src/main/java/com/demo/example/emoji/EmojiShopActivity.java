package com.demo.example.emoji;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demo.example.R;
import com.demo.example.emoji.adapter.EmojiAdapter;
import com.demo.example.emoji.adapter.FontTextAdapter;
import com.demo.example.emoji.adapter.SymboldTextAdapters;
import com.demo.example.emoji.customview.CurvedView;
import com.demo.example.emoji.customview.MotionTouchListener;
import com.demo.example.emoji.dialog.DialogLoading;
import com.demo.example.emoji.librate.ExitDialog;
import com.demo.example.emoji.librate.FeedbackDialog;
import com.demo.example.emoji.librate.FirstRatingDialog;
import com.demo.example.emoji.librate.MoreEmojiDialog;
import com.demo.example.emoji.librate.RatingDialog;
import com.demo.example.emoji.models.EmojiObject;
import com.demo.example.emoji.ultis.Constant;
import com.demo.example.emoji.ultis.PermissionManager;
import com.demo.example.emoji.ultis.StickerTextInEmojiUtils;
import com.demo.example.emoji.ultis.UltilsMethod;


import it.sephiroth.android.library.widget.HListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import uz.shift.colorpicker.LineColorPicker;
import uz.shift.colorpicker.OnColorChangedListener;


public class EmojiShopActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static String nameShape;

    private ArrayList<String> arrFonts;
    private ArrayList<String[]> arrListSymbols;
    private ArrayList<View> arrViewShow;
    private CurvedView curvedView;
    private DialogLoading dialogLoading;
    private EmojiAdapter emojiAdapter;
    private ExitDialog exitDialog;
    private FeedbackDialog feedbackDialog;
    private FirstRatingDialog firstRatingDialog;
    private FontTextAdapter fontTextAdapter;
    private GridView grvSymbol;
    private GridView gvBackground;
    private GridView gvShape;
    private HListView hlvFonts;
    private ImageView imgEmoji;
    private LinearLayout linearScroll;
    private List<ImageView> listChoice;
    private List<ImageView> listChoiceInputText;
    private List<EmojiObject> listEmojiShop;
    private List<ImageView> listSelectTab;
    private List<ImageView> listSelectTabInputText;
    private LinearLayout lnlScrollInputText;
    private LinearLayout lnlTextEmojiShop;
    private MoreEmojiDialog mMoreEmojiDialog;
    private RatingDialog mRatingDialog;
    private LineColorPicker pickerColorText;
    private LineColorPicker pickerShadowColor;
    private int positionTabSymbol;
    private RelativeLayout rltBackground;
    private RelativeLayout rltEmoji;
    private RelativeLayout rltEmojiBig;
    private RelativeLayout rltInputText;
    private RelativeLayout rltShape;
    private SeekBar seekbarTextSize;
    private SharedPreferences sharedPreferences;
    private StickerTextInEmojiUtils stickerUtils;
    private SymboldTextAdapters symboldTextAdapters;
    private UltilsMethod ultilsMethod;
    private int[] listTab = {R.drawable.ic_shape_active, R.drawable.ic_text1_active, R.drawable.ic_keyboard_active};
    private int[] listTabNonSelect = {R.drawable.ic_shape, R.drawable.ic_text1, R.drawable.ic_keyboard};
    private int[] listTabInputText = {R.drawable.ic_keyboard_active, R.drawable.ic_shape2_active, R.drawable.ic_symbols_active, R.drawable.ic_numbers_active};
    private int[] listTabNonSelectInputText = {R.drawable.ic_keyboard2, R.drawable.ic_shape_text, R.drawable.ic_symbols, R.drawable.ic_numbers};

    @Override 
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.layout_emojishop_activity);
        findView();
    }

    private void findView() {
        this.ultilsMethod = new UltilsMethod(this);
        findViewById(R.id.imgClose).setOnClickListener(this);
        findViewById(R.id.imgSaveEmojiShop).setOnClickListener(this);
        findViewById(R.id.imgOpenMyShop).setOnClickListener(this);
        this.rltEmojiBig = (RelativeLayout) findViewById(R.id.rltEmojiBig);
        this.rltEmoji = (RelativeLayout) findViewById(R.id.rltEmoji);
        this.linearScroll = (LinearLayout) findViewById(R.id.linearScroll);
        this.rltShape = (RelativeLayout) findViewById(R.id.rltShape);
        this.gvShape = (GridView) findViewById(R.id.gvShape);
        this.rltBackground = (RelativeLayout) findViewById(R.id.rltBackground);
        this.gvBackground = (GridView) findViewById(R.id.gvBackground);
        this.linearScroll = (LinearLayout) findViewById(R.id.linearScroll);
        this.lnlTextEmojiShop = (LinearLayout) findViewById(R.id.lnlTextEmojiShop);
        this.seekbarTextSize = (SeekBar) findViewById(R.id.seekbarTextSize);
        this.pickerColorText = (LineColorPicker) findViewById(R.id.pickerColorText);
        this.pickerShadowColor = (LineColorPicker) findViewById(R.id.pickerShadowColor);
        this.rltInputText = (RelativeLayout) findViewById(R.id.rltInputText);
        this.lnlScrollInputText = (LinearLayout) findViewById(R.id.lnlScrollInputText);
        this.grvSymbol = (GridView) findViewById(R.id.grvSymbol);
        this.hlvFonts = (HListView) findViewById(R.id.hlvFonts);
        this.imgEmoji = (ImageView) findViewById(R.id.imgEmoji);
        this.sharedPreferences = getSharedPreferences(Constant.NAME_SHAREDPREFERENCES, 0);
        this.arrViewShow = new ArrayList<>();
        this.arrViewShow.add(this.gvShape);
        this.arrViewShow.add(this.lnlTextEmojiShop);
        this.arrViewShow.add(this.rltInputText);
        this.stickerUtils = new StickerTextInEmojiUtils(this);
        this.listSelectTab = new ArrayList();
        this.listSelectTabInputText = new ArrayList();
        this.listChoice = new ArrayList();
        this.listChoiceInputText = new ArrayList();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        for (int i2 = 0; i2 < this.listTab.length; i2++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_emoji_choice, (ViewGroup) null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.imgEmoji);
            imageView.setImageResource(this.listTab[i2]);
            imageView.setTag(Integer.valueOf(i2));
            this.linearScroll.addView(inflate, new RelativeLayout.LayoutParams(i / 3, -1));
            this.listSelectTab.add(imageView);
            ImageView imageView2 = (ImageView) inflate.findViewById(R.id.imgChoice);
            this.listChoice.add(imageView2);
            imageView2.setVisibility(View.GONE);
            imageView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    int parseInt = Integer.parseInt(view.getTag().toString());
                    EmojiShopActivity.this.ultilsMethod.showHideView((View) EmojiShopActivity.this.arrViewShow.get(parseInt), EmojiShopActivity.this.arrViewShow);
                    EmojiShopActivity.this.setChoice(parseInt, EmojiShopActivity.this.listChoice, EmojiShopActivity.this.listTab, EmojiShopActivity.this.listTabNonSelect, EmojiShopActivity.this.listSelectTab);
                }
            });
        }
        this.gvShape.setOnItemClickListener(this);
        setChoice(0, this.listChoice, this.listTab, this.listTabNonSelect, this.listSelectTab);
        this.seekbarTextSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { 
            @Override 
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override 
            public void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                if (i3 > 1 && EmojiShopActivity.this.curvedView != null) {
                    EmojiShopActivity.this.curvedView.setTextSize((float) i3);
                }
            }
        });
        this.pickerColorText.setOnColorChangedListener(new OnColorChangedListener() { 
            @Override 
            public void onColorChanged(int i3) {
                if (EmojiShopActivity.this.curvedView != null) {
                    EmojiShopActivity.this.curvedView.setTextColor(i3);
                }
            }
        });
        this.pickerShadowColor.setOnColorChangedListener(new OnColorChangedListener() { 
            @Override 
            public void onColorChanged(int i3) {
                if (EmojiShopActivity.this.curvedView != null) {
                    EmojiShopActivity.this.curvedView.setColorForShadown(i3);
                }
            }
        });
        listFontsClick();
        tabInputText();
        dialogMain();

    }

    private void dialogMain() {
        this.mRatingDialog = new RatingDialog(this);
        this.mRatingDialog.setRatingDialogListener(new RatingDialog.RatingDialogInterFace() { 
            @Override 
            public void onDismiss() {
            }

            @Override 
            public void onRatingChanged(float f) {
            }

            @Override 
            public void onSubmit(float f, boolean z) {
                if (0.0f >= f || f > 3.0f) {
                    if (!z) {
                        EmojiShopActivity.this.ultilsMethod.rateApp(EmojiShopActivity.this);
                        EmojiShopActivity.this.ultilsMethod.SaveBoolean(EmojiShopActivity.this.sharedPreferences, Constant.RATE_APP, true);
                        return;
                    }
                    EmojiShopActivity.this.mMoreEmojiDialog.showDialog(false, EmojiShopActivity.this);
                    EmojiShopActivity.this.mMoreEmojiDialog.changeTitle("Step 2!", "Please help us out - Your Play Store Reviews make a difference!");
                    EmojiShopActivity.this.mMoreEmojiDialog.hideButton("Ok!");
                    EmojiShopActivity.this.mMoreEmojiDialog.CommentOnGooglePlay(true);
                } else if (z) {
                    EmojiShopActivity.this.feedbackDialog.showDialog(false, EmojiShopActivity.this);
                    EmojiShopActivity.this.feedbackDialog.isMoreEmoji(true);
                } else {
                    EmojiShopActivity.this.feedbackDialog.showDialog(false, EmojiShopActivity.this);
                    EmojiShopActivity.this.feedbackDialog.isMoreEmoji(false);
                }
            }

            @Override 
            public void maybe() {
                if (EmojiShopActivity.this.sharedPreferences.getInt(Constant.NUMBER_SAVED, 0) > 5) {
                    EmojiShopActivity.this.ultilsMethod.SaveInt(EmojiShopActivity.this.sharedPreferences, Constant.NUMBER_SAVED, 0);
                }
            }
        });
        this.firstRatingDialog = new FirstRatingDialog(this);
        this.firstRatingDialog.setRatingDialogListener(new FirstRatingDialog.RatingDialogInterFace() { 
            @Override 
            public void onDismiss() {
            }

            @Override 
            public void onRatingChanged(float f) {
            }

            @Override 
            public void onSubmit(float f) {
                if (f <= 3.0f) {
                    EmojiShopActivity.this.feedbackDialog.showDialog(false, EmojiShopActivity.this);
                } else {
                    EmojiShopActivity.this.ultilsMethod.rateApp(EmojiShopActivity.this);
                    EmojiShopActivity.this.ultilsMethod.SaveBoolean(EmojiShopActivity.this.sharedPreferences, Constant.RATE_APP, true);
                }
                EmojiShopActivity.this.saveEmoji();
            }

            @Override 
            public void onMaybe() {
                EmojiShopActivity.this.saveEmoji();
            }
        });
        this.feedbackDialog = new FeedbackDialog(this);
        this.feedbackDialog.setFeedbackDialogListener(new FeedbackDialog.FeedbackDialogInterFace() { 
            @Override 
            public void onDismiss() {
            }

            @Override 
            public void onSubmit(String str, boolean z) {
                if (!z) {
                    if (!str.equalsIgnoreCase("")) {
                        EmojiShopActivity.this.ultilsMethod.sendFeedback(EmojiShopActivity.this, str);
                    }
                } else if (!str.equalsIgnoreCase("")) {
                    EmojiShopActivity.this.ultilsMethod.sendFeedback(EmojiShopActivity.this, str);
                    EmojiShopActivity.this.ultilsMethod.SaveBoolean(EmojiShopActivity.this.sharedPreferences, Constant.RATE_APP, true);
                    EmojiShopActivity.this.emojiAdapter.rateChanges();
                }
            }
        });
        this.mMoreEmojiDialog = new MoreEmojiDialog(this);
        this.mMoreEmojiDialog.setRatingDialogListener(new MoreEmojiDialog.MoreEmojiDialogInterFace() { 
            @Override 
            public void maybe() {
            }

            @Override 
            public void ok(boolean z) {
                if (!z) {
                    EmojiShopActivity.this.mRatingDialog.showDialog(false, EmojiShopActivity.this);
                    return;
                }
                EmojiShopActivity.this.ultilsMethod.rateApp(EmojiShopActivity.this);
                EmojiShopActivity.this.ultilsMethod.SaveBoolean(EmojiShopActivity.this.sharedPreferences, Constant.RATE_APP, true);
                EmojiShopActivity.this.emojiAdapter.rateChanges();
            }
        });
        setupDialog();
    }

    private void tabInputText() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        for (int i2 = 0; i2 < this.listTabInputText.length; i2++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_emoji_choice, (ViewGroup) null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.imgEmoji);
            imageView.setImageResource(this.listTabInputText[i2]);
            imageView.setTag(Integer.valueOf(i2));
            this.lnlScrollInputText.addView(inflate, new RelativeLayout.LayoutParams(i / 4, -1));
            this.listSelectTabInputText.add(imageView);
            ImageView imageView2 = (ImageView) inflate.findViewById(R.id.imgChoice);
            this.listChoice.add(imageView2);
            imageView2.setVisibility(View.GONE);
            imageView.setOnClickListener(new View.OnClickListener() { 
                @Override 
                public void onClick(View view) {
                    int parseInt = Integer.parseInt(view.getTag().toString());
                    EmojiShopActivity.this.setChoice(parseInt, EmojiShopActivity.this.listChoiceInputText, EmojiShopActivity.this.listTabInputText, EmojiShopActivity.this.listTabNonSelectInputText, EmojiShopActivity.this.listSelectTabInputText);
                    if (parseInt == 0) {
                        EmojiShopActivity.this.positionTabSymbol = 0;
                        EmojiShopActivity.this.showDialogInputText();
                    } else if (parseInt == 1) {
                        EmojiShopActivity.this.positionTabSymbol = 0;
                        EmojiShopActivity.this.symboldTextAdapters.setData((String[]) EmojiShopActivity.this.arrListSymbols.get(0));
                    } else if (parseInt == 2) {
                        EmojiShopActivity.this.positionTabSymbol = 1;
                        EmojiShopActivity.this.symboldTextAdapters.setData((String[]) EmojiShopActivity.this.arrListSymbols.get(1));
                    } else if (parseInt == 3) {
                        EmojiShopActivity.this.positionTabSymbol = 2;
                        EmojiShopActivity.this.symboldTextAdapters.setData((String[]) EmojiShopActivity.this.arrListSymbols.get(2));
                    }
                }
            });
        }
        this.grvSymbol.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
            @Override 
            public void onItemClick(AdapterView<?> adapterView, View view, int i3, long j) {
                EmojiShopActivity.this.curvedView.setText(((String[]) EmojiShopActivity.this.arrListSymbols.get(EmojiShopActivity.this.positionTabSymbol))[i3]);
            }
        });
    }

    private void listFontsClick() {


        hlvFonts.setOnItemClickListener(new it.sephiroth.android.library.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> parent, View view, int position, long id) {
                EmojiShopActivity.this.curvedView.setTypeface(Typeface.createFromAsset(EmojiShopActivity.this.getAssets(), (String) EmojiShopActivity.this.arrFonts.get(position)));

            }
        });


    }

    
    public void showDialogInputText() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_input_emojishop);
        final EditText editText = (EditText) dialog.findViewById(R.id.edtInputEmojishop);
        ((TextView) dialog.findViewById(R.id.tvCancelAddText)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.tvOKAddText)).setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                EmojiShopActivity.this.curvedView.setText(editText.getText().toString().trim());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override 
    protected void onResume() {
        new getEmojiShop().execute(new Void[0]);
        super.onResume();
    }

    void setChoice(int i, List<ImageView> list, int[] iArr, int[] iArr2, List<ImageView> list2) {
        int i2 = 0;
        int i3 = 0;
        while (i3 < list.size()) {
            list.get(i3).setVisibility(i == i3 ? View.VISIBLE : View.GONE);
            i3++;
        }
        while (i2 < list2.size()) {
            list2.get(i2).setImageResource(i == i2 ? iArr[i2] : iArr2[i2]);
            i2++;
        }
    }

    @Override 
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        EmojiObject emojiObject = (EmojiObject) adapterView.getItemAtPosition(i);
        if (this.sharedPreferences.getBoolean(Constant.RATE_APP, false)) {
            nameShape = emojiObject.getLinkEmojiNomal();
            this.stickerUtils.addStickerView(this, this.listEmojiShop.get(i).getLinkEmojiNomal(), this.rltEmoji, 0, this.rltEmojiBig.getHeight());
            this.emojiAdapter.positionChange(i);
            this.symboldTextAdapters.setEmoji(nameShape);
        } else if (emojiObject.isLock()) {
            this.mMoreEmojiDialog.showDialog(false, this);
            this.mMoreEmojiDialog.changeTitle("Hi there!", "Would you like to have more emojis?");
            this.mMoreEmojiDialog.showButton("Yes, I want!");
            this.mMoreEmojiDialog.CommentOnGooglePlay(false);
            this.mRatingDialog.changeTitle("Step 1!", "Please feel free to give us your feedback!");
            this.mRatingDialog.isMoreEmoji(true);
        } else {
            nameShape = emojiObject.getLinkEmojiNomal();
            this.stickerUtils.addStickerView(this, this.listEmojiShop.get(i).getLinkEmojiNomal(), this.rltEmoji, 0, this.rltEmojiBig.getHeight());
            this.emojiAdapter.positionChange(i);
            this.symboldTextAdapters.setEmoji(nameShape);
        }
    }

    
    
    public class getEmojiShop extends AsyncTask<Void, Void, Void> {
        private getEmojiShop() {
        }

        @Override 
        protected void onPreExecute() {
            EmojiShopActivity.this.listEmojiShop = new ArrayList();
            EmojiShopActivity.this.arrFonts = new ArrayList();
            EmojiShopActivity.this.arrListSymbols = new ArrayList();
            EmojiShopActivity.this.dialogLoading = new DialogLoading(EmojiShopActivity.this, "Loading");
            EmojiShopActivity.this.dialogLoading.show();
            super.onPreExecute();
        }

        
        public Void doInBackground(Void... voidArr) {
            EmojiShopActivity.this.listEmojiShop = EmojiShopActivity.this.ultilsMethod.getPathEmojiFromAsstes("emojishop", 1);
            EmojiShopActivity.this.arrFonts = EmojiShopActivity.this.ultilsMethod.getAllNameImageByType(EmojiShopActivity.this, "fonts");
            return null;
        }

        
        public void onPostExecute(Void r10) {
            EmojiShopActivity.this.emojiAdapter = new EmojiAdapter(EmojiShopActivity.this.listEmojiShop, EmojiShopActivity.this, "emojishop", 0, 0, EmojiShopActivity.this.sharedPreferences);
            EmojiShopActivity.this.gvShape.setAdapter((ListAdapter) EmojiShopActivity.this.emojiAdapter);
            String unused = EmojiShopActivity.nameShape = ((EmojiObject) EmojiShopActivity.this.listEmojiShop.get(0)).getLinkEmojiNomal();

            Glide.with((Activity) EmojiShopActivity.this).load(EmojiShopActivity.nameShape).asBitmap().into(new SimpleTarget<Bitmap>() {
                public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                    EmojiShopActivity.this.imgEmoji.setImageBitmap(bitmap);
                }
            });


            EmojiShopActivity.this.fontTextAdapter = new FontTextAdapter(EmojiShopActivity.this.arrFonts, EmojiShopActivity.this);
            EmojiShopActivity.this.hlvFonts.setAdapter((ListAdapter) EmojiShopActivity.this.fontTextAdapter);
            EmojiShopActivity.this.arrListSymbols.add(Constant.arrSymbol);
            EmojiShopActivity.this.arrListSymbols.add(Constant.arrSymbol2);
            EmojiShopActivity.this.arrListSymbols.add(Constant.arrSymbol3);
            EmojiShopActivity.this.symboldTextAdapters = new SymboldTextAdapters(Constant.arrSymbol, EmojiShopActivity.this, EmojiShopActivity.nameShape);
            EmojiShopActivity.this.grvSymbol.setAdapter((ListAdapter) EmojiShopActivity.this.symboldTextAdapters);
            EmojiShopActivity.this.curvedView = new CurvedView(EmojiShopActivity.this);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            layoutParams.addRule(13, -1);
            EmojiShopActivity.this.rltEmojiBig.addView(EmojiShopActivity.this.curvedView, layoutParams);
            EmojiShopActivity.this.curvedView.setOnTouchListener(new MotionTouchListener(EmojiShopActivity.this.curvedView));
            if (EmojiShopActivity.this.dialogLoading != null && EmojiShopActivity.this.dialogLoading.isShowing()) {
                EmojiShopActivity.this.dialogLoading.dismiss();
                EmojiShopActivity.this.dialogLoading = null;
            }
            super.onPostExecute(r10);
        }
    }

    @Override 
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.imgClose) {
            onBackPressed();
            finish();
        } else if (id == R.id.imgOpenMyShop) {
            startActivity(new Intent(this, ActivityAlbum.class));
        } else if (id == R.id.imgSaveEmojiShop) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!PermissionManager.getIntance().hasWriteExternal(this) || !PermissionManager.getIntance().hasReadExternal(this)) {
                    if (!PermissionManager.getIntance().hasWriteExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
                    }
                    if (!PermissionManager.getIntance().hasReadExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 3);
                    }
                } else if (!this.sharedPreferences.getBoolean(Constant.KEY_FIRST_CREATE, true)) {
                    saveEmoji();
                } else {
                    this.firstRatingDialog.showDialog(false, this);
                    this.ultilsMethod.SaveBoolean(this.sharedPreferences, Constant.KEY_FIRST_CREATE, false);
                }
            } else if (!this.sharedPreferences.getBoolean(Constant.KEY_FIRST_CREATE, true)) {
                saveEmoji();
            } else {
                this.firstRatingDialog.showDialog(false, this);
                this.ultilsMethod.SaveBoolean(this.sharedPreferences, Constant.KEY_FIRST_CREATE, false);
            }
        }
    }

    
    public void saveEmoji() {
        this.ultilsMethod.generateBitmap(this.rltEmojiBig);
        Toast.makeText(this, (int) R.string.saved, Toast.LENGTH_SHORT).show();
        this.ultilsMethod.SaveInt(this.sharedPreferences, Constant.NUMBER_SAVED, this.sharedPreferences.getInt(Constant.NUMBER_SAVED, 0) + 1);
        if (this.sharedPreferences.getInt(Constant.NUMBER_SAVED, 0) > 5 && !this.sharedPreferences.getBoolean(Constant.RATE_APP, false)) {
            this.mRatingDialog.showDialog(false, this);
            Random random = new Random();
            this.mRatingDialog.changeTitle(Constant.arrTitle[random.nextInt(2)], Constant.arrContent[random.nextInt(2)]);
        }
    }

    private void setupDialog() {
        this.exitDialog = new ExitDialog(this);
        this.exitDialog.setRatingDialogListener(new ExitDialog.ExitDialogInterFace() { 
            @Override 
            public void maybe() {
            }

            @Override 
            public void ok(boolean z) {
                EmojiShopActivity.this.finish();
            }
        });
    }

    @Override 
    public void onBackPressed() {



        finish();
    }
}
