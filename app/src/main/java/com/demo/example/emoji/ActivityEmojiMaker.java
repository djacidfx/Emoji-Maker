package com.demo.example.emoji;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.demo.example.R;
import com.demo.example.emoji.adapter.BackgroundAdapter;
import com.demo.example.emoji.adapter.EmojiAdapter;
import com.demo.example.emoji.dialog.DialogLoading;
import com.demo.example.emoji.librate.ExitDialog;
import com.demo.example.emoji.librate.FeedbackDialog;
import com.demo.example.emoji.librate.FirstRatingDialog;
import com.demo.example.emoji.librate.MoreEmojiDialog;
import com.demo.example.emoji.librate.RatingDialog;
import com.demo.example.emoji.models.EmojiObject;
import com.demo.example.emoji.models.StickerListModel;
import com.demo.example.emoji.stickers.StickerUtils;
import com.demo.example.emoji.ultis.Constant;
import com.demo.example.emoji.ultis.PermissionManager;
import com.demo.example.emoji.ultis.UltilsMethod;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.github.clans.fab.FloatingActionMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pl.droidsonroids.gif.GifTextView;


public class ActivityEmojiMaker extends Activity implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener {
    private static String nameShape;

    private ArrayList<Integer> arrRateGif;
    private ArrayList<StickerListModel> arrStickerList;
    private BackgroundAdapter backgroundAdapter;
    private Bitmap bmImageBackground;
    private RelativeLayout bottomControl;
    private DialogLoading dialogLoading;
    private EmojiAdapter emojiAdapter;
    private ExitDialog exitDialog;
    private FeedbackDialog feedbackDialog;
    Uri file;
    private FirstRatingDialog firstRatingDialog;
    private GridView gvBackground;
    private GridView gvEmoji;
    private int heightScreen;
    private ImageView imgBackground;
    private ImageView imgChangeColorSticker;
    private ImageView imgDeleteSticker;
    private ImageView imgRotateLeftSticker;
    private ImageView imgRotateVerticalSticker;
    private LinearLayout linearScroll;
    private List<List<EmojiObject>> listEmojiAll;
    private List<EmojiObject> listEmojiGr;
    String mCurrentPhotoPath;
    private MoreEmojiDialog mMoreEmojiDialog;
    private RatingDialog mRatingDialog;
    private FloatingActionMenu menu;
    private int positionTab;
    private RelativeLayout rltBackground;
    private RelativeLayout rltBottom;
    private RelativeLayout rltEmoji;
    private RelativeLayout rltEmojiBig;
    private SeekBar seekbarBlur;
    private SharedPreferences sharedPreferences;
    private StickerUtils stickerUtils;
    private UltilsMethod ultilsMethod;
    private String[] nameFolderEmoji = {"Shape", "MoreShape", "EmojiUser", "HappyMouth", "SadMouth", "Eyes", "EyesBig", "Eyebrows", "Nose", "Beard", "Stache", "Glasses", "Hair", "Mask", "Misc", "Hats", "Hands", "backgrounds"};
    private int[] listTab = {R.drawable.ic_shape1, R.drawable.ic_moreshape1, R.drawable.ic_user1, R.drawable.ic_happymouth1, R.drawable.ic_sadmouth1, R.drawable.ic_eye1, R.drawable.ic_eyebig1, R.drawable.ic_eyebrow1, R.drawable.ic_nose1, R.drawable.ic_beard1, R.drawable.ic_stache1, R.drawable.ic_glasses1, R.drawable.ic_hair1, R.drawable.ic_mask1, R.drawable.ic_misc1, R.drawable.ic_hat1, R.drawable.ic_hand1, R.drawable.ic_background_active};
    private int[] listTabNonSelect = {R.drawable.ic_shape2, R.drawable.ic_moreshape2, R.drawable.ic_user2, R.drawable.ic_happymouth2, R.drawable.ic_sadmouth2, R.drawable.ic_eye2, R.drawable.ic_eyebig2, R.drawable.ic_eyebrow2, R.drawable.ic_nose2, R.drawable.ic_beard2, R.drawable.ic_stache2, R.drawable.ic_glasses2, R.drawable.ic_hair2, R.drawable.ic_mask2, R.drawable.ic_misc2, R.drawable.ic_hat2, R.drawable.ic_hand2, R.drawable.ic_background_tab};
    private int positionLv = 0;
    private List<ImageView> listSelectTab = new ArrayList();
    private List<ImageView> listChoice = new ArrayList();
    int CAMERA_PIC_REQUEST = 589;

    private void findViews() {
        this.rltEmoji = (RelativeLayout) findViewById(R.id.rltEmoji);
        findViewById(R.id.imgSaveEmoji).setOnClickListener(this);
        findViewById(R.id.imgOpenMyEmoji).setOnClickListener(this);
        findViewById(R.id.imgCleanEmoji).setOnClickListener(this);
        this.gvBackground = (GridView) findViewById(R.id.gvBackground);
        findViewById(R.id.fabCamera).setOnClickListener(this);
        findViewById(R.id.fabGallery).setOnClickListener(this);
        findViewById(R.id.fabColors).setOnClickListener(this);
        this.imgBackground = (ImageView) findViewById(R.id.imgBackground);
        this.rltBackground = (RelativeLayout) findViewById(R.id.rltBackground);
        this.bottomControl = (RelativeLayout) findViewById(R.id.bottomControl);
        this.menu = (FloatingActionMenu) findViewById(R.id.menu);

        this.arrRateGif = new ArrayList<>();
        this.arrRateGif.add(Integer.valueOf((int) R.drawable.rate1));
        this.arrRateGif.add(Integer.valueOf((int) R.drawable.rate2));

        this.seekbarBlur = (SeekBar) findViewById(R.id.seekbarBlur);
        this.rltBottom = (RelativeLayout) findViewById(R.id.rltBottom);
        this.rltEmojiBig = (RelativeLayout) findViewById(R.id.rltEmojiBig);
        this.gvEmoji = (GridView) findViewById(R.id.gvEmoji);
        this.imgChangeColorSticker = (ImageView) findViewById(R.id.imgChangeColorSticker);
        this.imgRotateLeftSticker = (ImageView) findViewById(R.id.imgRotateLeftSticker);
        this.imgRotateVerticalSticker = (ImageView) findViewById(R.id.imgRotateVerticalSticker);
        this.imgDeleteSticker = (ImageView) findViewById(R.id.imgDeleteSticker);
        this.sharedPreferences = getSharedPreferences(Constant.NAME_SHAREDPREFERENCES, 0);
        this.rltEmoji.setOnTouchListener(this);
        this.stickerUtils = new StickerUtils(this);
        this.ultilsMethod = new UltilsMethod(this);
        this.arrStickerList = new ArrayList<>();
        this.linearScroll = (LinearLayout) findViewById(R.id.linearScroll);
        gvEmojiClicked();
        this.gvBackground.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                if (i == 0) {
                    ActivityEmojiMaker.this.imgBackground.setVisibility(View.GONE);
                    return;
                }
                ActivityEmojiMaker.this.imgBackground.setColorFilter((ColorFilter) null);

                Glide.with((Activity) ActivityEmojiMaker.this).load(((EmojiObject) ((List) ActivityEmojiMaker.this.listEmojiAll.get(ActivityEmojiMaker.this.listEmojiAll.size() - 1)).get(i)).getLinkEmojiNomal()).asBitmap().into(new SimpleTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        ActivityEmojiMaker.this.imgBackground.setImageBitmap(bitmap);
                        Bitmap unused = ActivityEmojiMaker.this.bmImageBackground = bitmap;
                        if (ActivityEmojiMaker.this.seekbarBlur.getProgress() > 1) {
                            ActivityEmojiMaker.this.imgBackground.setImageBitmap(ActivityEmojiMaker.this.ultilsMethod.blurRenderScript(ActivityEmojiMaker.this.bmImageBackground, ActivityEmojiMaker.this.seekbarBlur.getProgress(), ActivityEmojiMaker.this));
                        }
                        if (ActivityEmojiMaker.this.imgBackground.getVisibility() == View.GONE) {
                            ActivityEmojiMaker.this.imgBackground.setVisibility(View.VISIBLE);
                        }
                    }
                });


                ActivityEmojiMaker.this.backgroundAdapter.positionChange(i);
            }
        });
        this.seekbarBlur.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (seekBar.getProgress() > 1) {
                    try {
                        if (ActivityEmojiMaker.this.bmImageBackground != null && ActivityEmojiMaker.this.imgBackground != null && ActivityEmojiMaker.this.ultilsMethod != null) {
                            ActivityEmojiMaker.this.imgBackground.setImageBitmap(ActivityEmojiMaker.this.ultilsMethod.blurRenderScript(ActivityEmojiMaker.this.bmImageBackground, seekBar.getProgress(), ActivityEmojiMaker.this));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        this.imgChangeColorSticker.setOnClickListener(this);
        this.imgRotateLeftSticker.setOnClickListener(this);
        this.imgRotateVerticalSticker.setOnClickListener(this);
        this.imgDeleteSticker.setOnClickListener(this);
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
                        ActivityEmojiMaker.this.ultilsMethod.rateApp(ActivityEmojiMaker.this);
                        ActivityEmojiMaker.this.ultilsMethod.SaveBoolean(ActivityEmojiMaker.this.sharedPreferences, Constant.RATE_APP, true);
                        return;
                    }
                    ActivityEmojiMaker.this.mMoreEmojiDialog.showDialog(false, ActivityEmojiMaker.this);
                    ActivityEmojiMaker.this.mMoreEmojiDialog.changeTitle("Step 2!", "Please help us out - Your Play Store Reviews make a difference!");
                    ActivityEmojiMaker.this.mMoreEmojiDialog.hideButton("Ok!");
                    ActivityEmojiMaker.this.mMoreEmojiDialog.CommentOnGooglePlay(true);
                } else if (z) {
                    ActivityEmojiMaker.this.feedbackDialog.showDialog(false, ActivityEmojiMaker.this);
                    ActivityEmojiMaker.this.feedbackDialog.isMoreEmoji(true);
                } else {
                    ActivityEmojiMaker.this.feedbackDialog.showDialog(false, ActivityEmojiMaker.this);
                    ActivityEmojiMaker.this.feedbackDialog.isMoreEmoji(false);
                }
            }

            @Override
            public void maybe() {
                if (ActivityEmojiMaker.this.sharedPreferences.getInt(Constant.NUMBER_SAVED, 0) > 5) {
                    ActivityEmojiMaker.this.ultilsMethod.SaveInt(ActivityEmojiMaker.this.sharedPreferences, Constant.NUMBER_SAVED, 0);
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
                    ActivityEmojiMaker.this.feedbackDialog.showDialog(false, ActivityEmojiMaker.this);
                } else {
                    ActivityEmojiMaker.this.ultilsMethod.rateApp(ActivityEmojiMaker.this);
                }
                ActivityEmojiMaker.this.savePicture();
            }

            @Override
            public void onMaybe() {
                ActivityEmojiMaker.this.savePicture();
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
                        ActivityEmojiMaker.this.ultilsMethod.sendFeedback(ActivityEmojiMaker.this, str);
                    }
                } else if (!str.equalsIgnoreCase("")) {
                    ActivityEmojiMaker.this.ultilsMethod.sendFeedback(ActivityEmojiMaker.this, str);
                    ActivityEmojiMaker.this.ultilsMethod.SaveBoolean(ActivityEmojiMaker.this.sharedPreferences, Constant.RATE_APP, true);
                    ActivityEmojiMaker.this.emojiAdapter.rateChanges();
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
                    ActivityEmojiMaker.this.mRatingDialog.showDialog(false, ActivityEmojiMaker.this);
                    return;
                }
                ActivityEmojiMaker.this.ultilsMethod.rateApp(ActivityEmojiMaker.this);
                ActivityEmojiMaker.this.ultilsMethod.SaveBoolean(ActivityEmojiMaker.this.sharedPreferences, Constant.RATE_APP, true);
                ActivityEmojiMaker.this.emojiAdapter.rateChanges();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_emoji_maker);
        findViews();
        init();
        setupDialog();
    }



    void init() {
        new getEmoji().execute(new Void[0]);
        new getAllEmoji().execute(new Void[0]);
        for (int i = 0; i < this.listTab.length; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_emoji_choice, (ViewGroup) null);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.imgEmoji);
            imageView.setImageResource(this.listTab[i]);
            imageView.setTag(Integer.valueOf(i));
            this.linearScroll.addView(inflate);
            this.listSelectTab.add(imageView);
            ImageView imageView2 = (ImageView) inflate.findViewById(R.id.imgChoice);
            this.listChoice.add(imageView2);
            imageView2.setVisibility(View.GONE);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int parseInt = Integer.parseInt(view.getTag().toString());
                    ActivityEmojiMaker.this.positionTab = parseInt;
                    if (parseInt < ActivityEmojiMaker.this.listSelectTab.size() - 1) {
                        ActivityEmojiMaker.this.positionLv = parseInt;
                        ActivityEmojiMaker.this.emojiAdapter.dataChanges((List) ActivityEmojiMaker.this.listEmojiAll.get(parseInt), parseInt, ActivityEmojiMaker.nameShape, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
                        ActivityEmojiMaker.this.rltBackground.setVisibility(View.GONE);
                        ActivityEmojiMaker.this.gvEmoji.setVisibility(View.VISIBLE);
                        ActivityEmojiMaker.this.seekbarBlur.setVisibility(View.GONE);
                        ActivityEmojiMaker.this.menu.setVisibility(View.GONE);
                    } else {
                        ActivityEmojiMaker.this.rltBackground.setVisibility(View.VISIBLE);
                        ActivityEmojiMaker.this.menu.setVisibility(View.VISIBLE);
                        ActivityEmojiMaker.this.gvEmoji.setVisibility(View.GONE);
                        ActivityEmojiMaker.this.seekbarBlur.setVisibility(View.VISIBLE);
                        int nextInt = new Random().nextInt(30);
                        if (nextInt == 0) {
                            nextInt = 1;
                        }
                        Glide.with((Activity) ActivityEmojiMaker.this).load(((EmojiObject) ((List) ActivityEmojiMaker.this.listEmojiAll.get(ActivityEmojiMaker.this.listEmojiAll.size() - 1)).get(nextInt)).getLinkEmojiNomal()).asBitmap().into(new SimpleTarget<Bitmap>() {
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                ActivityEmojiMaker.this.imgBackground.setImageBitmap(bitmap);
                                Bitmap unused = ActivityEmojiMaker.this.bmImageBackground = bitmap;
                                if (ActivityEmojiMaker.this.seekbarBlur.getProgress() > 1) {
                                    ActivityEmojiMaker.this.imgBackground.setImageBitmap(ActivityEmojiMaker.this.ultilsMethod.blurRenderScript(ActivityEmojiMaker.this.bmImageBackground, ActivityEmojiMaker.this.seekbarBlur.getProgress(), ActivityEmojiMaker.this));
                                }
                            }
                        });

                        ActivityEmojiMaker.this.backgroundAdapter.positionChange(nextInt);
                    }
                    ActivityEmojiMaker.this.setChoice(parseInt);
                }
            });
        }
        setChoice(0);
    }

    void setChoice(int i) {
        int i2 = 0;
        int i3 = 0;
        while (i3 < this.listChoice.size()) {
            this.listChoice.get(i3).setVisibility(i == i3 ? View.VISIBLE : View.GONE);
            i3++;
        }
        while (i2 < this.listSelectTab.size()) {
            this.listSelectTab.get(i2).setImageResource(i == i2 ? this.listTab[i2] : this.listTabNonSelect[i2]);
            i2++;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.sharedPreferences.getBoolean(Constant.FROM_SMILEYS, false) && this.listEmojiAll != null) {
            this.positionLv = 2;
            List<EmojiObject> externalCacheDir = this.ultilsMethod.getExternalCacheDir(this, 2);
            externalCacheDir.add(0, this.ultilsMethod.getPathEmojiFromAsstes("EmojiUser", 2).get(0));
            this.listEmojiAll.set(2, externalCacheDir);
            this.emojiAdapter.dataChanges(this.listEmojiAll.get(2), 2, nameShape, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            this.stickerUtils.addStickerView(this, this.listEmojiAll.get(2).get(1).getLinkEmojiNomal(), this.rltEmoji, this.positionLv, this.rltEmojiBig.getHeight(), this.arrStickerList);
            this.emojiAdapter.positionChange(1);
            nameShape = this.listEmojiAll.get(2).get(1).getLinkEmojiNomal();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imgOpenMyEmoji) {
            startActivity(new Intent(this, ActivityAlbum.class));
            return;
        }
        int i = 0;
        if (view.getId() == R.id.imgSaveEmoji) {
            if (Build.VERSION.SDK_INT >= 23) {
                if (!PermissionManager.getIntance().hasWriteExternal(this) || !PermissionManager.getIntance().hasReadExternal(this)) {
                    if (!PermissionManager.getIntance().hasWriteExternal(this) && ActivityCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 2);
                    }
                    if (!(PermissionManager.getIntance().hasReadExternal(this) || ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0)) {
                        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 3);
                    }
                } else if (!this.sharedPreferences.getBoolean(Constant.KEY_FIRST_CREATE, true)) {
                    savePicture();
                } else {
                    this.firstRatingDialog.showDialog(false, this);
                    this.ultilsMethod.SaveBoolean(this.sharedPreferences, Constant.KEY_FIRST_CREATE, false);
                }
            } else if (!this.sharedPreferences.getBoolean(Constant.KEY_FIRST_CREATE, true)) {
                savePicture();
            } else {
                this.firstRatingDialog.showDialog(false, this);
                this.ultilsMethod.SaveBoolean(this.sharedPreferences, Constant.KEY_FIRST_CREATE, false);
            }
        } else if (view.getId() == R.id.imgBack) {
            onBackPressed();
        } else if (view.getId() == R.id.imgCleanEmoji) {
            int childCount = this.rltEmoji.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (i2 != 0) {
                    this.rltEmoji.removeViewAt(1);
                }
            }
            this.positionLv = 0;
            nameShape = this.listEmojiGr.get(0).getLinkEmojiNomal();
            this.emojiAdapter.dataChanges(this.listEmojiGr, 0, nameShape, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
            this.emojiAdapter.positionChange(0);
            setChoice(this.positionLv);
        } else if (view.getId() == R.id.fabCamera) {
            if (Build.VERSION.SDK_INT < 23) {
                takePicture();
            } else if (PermissionManager.getIntance().hasCamera(this)) {
                takePicture();
            } else if (ActivityCompat.checkSelfPermission(this, "android.permission.CAMERA") != 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 12);
            }
        } else if (view.getId() == R.id.fabGallery) {
            if (Build.VERSION.SDK_INT < 23) {
                Intent intent = new Intent("android.intent.action.PICK");
                intent.setType("image/*");
                startActivityForResult(intent, 1306);
            } else if (PermissionManager.getIntance().hasReadExternal(this)) {
                Intent intent2 = new Intent("android.intent.action.PICK");
                intent2.setType("image/*");
                startActivityForResult(intent2, 1306);
            } else if (ActivityCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") != 0) {
                requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 13);
            }
        } else if (view.getId() == R.id.fabColors) {
            showDialogColor();
        } else if (view.getId() == R.id.imgChangeColorSticker) {
            while (i < this.arrStickerList.size()) {
                if (this.arrStickerList.get(i).getPositionTab() == this.positionTab) {
                    this.arrStickerList.get(this.arrStickerList.get(i).getPositionIndex()).getStickerView().setColor(-16776961);
                }
                i++;
            }
        } else if (view.getId() == R.id.imgRotateLeftSticker) {
            while (i < this.arrStickerList.size()) {
                if (this.arrStickerList.get(i).getPositionTab() == this.positionTab) {
                    this.arrStickerList.get(this.arrStickerList.get(i).getPositionIndex()).getStickerView().flip();
                }
                i++;
            }
        } else if (view.getId() == R.id.imgRotateVerticalSticker) {
            while (i < this.arrStickerList.size()) {
                if (this.arrStickerList.get(i).getPositionTab() == this.positionTab) {
                    this.arrStickerList.get(this.arrStickerList.get(i).getPositionIndex()).getStickerView().flipVertical();
                }
                i++;
            }
        } else if (view.getId() == R.id.imgDeleteSticker) {
            while (i < this.arrStickerList.size()) {
                if (this.arrStickerList.get(i).getPositionTab() == this.positionTab) {
                    this.arrStickerList.get(this.arrStickerList.get(i).getPositionIndex()).getStickerView().delete();
                }
                i++;
            }
        }
    }


    public void savePicture() {
        try {
            this.seekbarBlur.setVisibility(View.GONE);
            StickerUtils stickerUtils = this.stickerUtils;
            StickerUtils.mCurrentView.setInEdit(false);
            this.ultilsMethod.generateBitmap(this.rltEmojiBig);
            Toast.makeText(this, (int) R.string.saved, Toast.LENGTH_SHORT).show();
            this.ultilsMethod.SaveInt(this.sharedPreferences, Constant.NUMBER_SAVED, this.sharedPreferences.getInt(Constant.NUMBER_SAVED, 0) + 1);
            if (this.sharedPreferences.getInt(Constant.NUMBER_SAVED, 0) > 5 && !this.sharedPreferences.getBoolean(Constant.RATE_APP, false)) {
                this.mRatingDialog.showDialog(false, this);
                Random random = new Random();
                this.mRatingDialog.changeTitle(Constant.arrTitle[random.nextInt(2)], Constant.arrContent[random.nextInt(2)]);
            }
            if (this.menu.getVisibility() == View.VISIBLE) {
                this.seekbarBlur.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takePicture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        if (intent.resolveActivity(getPackageManager()) != null) {
            try {
                Uri uriForFile = FileProvider.getUriForFile(this, this.getPackageName() + ".provider", createImageFile());
                this.file = uriForFile;
                intent.putExtra("output", uriForFile);
                startActivityForResult(intent, this.CAMERA_PIC_REQUEST);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
    }

    private void showDialogColor() {
        ColorPickerDialogBuilder.with(this).setTitle("Choose Color").initialColor(0xffff0000).wheelType(ColorPickerView.WHEEL_TYPE.FLOWER).density(12).setOnColorSelectedListener(new OnColorSelectedListener() {
            @Override
            public void onColorSelected(int i) {
            }
        }).setPositiveButton("Ok", new ColorPickerClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, Integer[] numArr) {
                ActivityEmojiMaker.this.imgBackground.setColorFilter(i);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).build().show();
    }

    private void gvEmojiClicked() {
        this.gvEmoji.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                EmojiObject emojiObject = (EmojiObject) adapterView.getItemAtPosition(i);
                if (ActivityEmojiMaker.this.sharedPreferences.getBoolean(Constant.RATE_APP, false)) {
                    if (ActivityEmojiMaker.this.positionLv != 0 && ActivityEmojiMaker.this.positionLv != 1 && ActivityEmojiMaker.this.positionLv != 2) {
                        ActivityEmojiMaker.this.stickerUtils.addStickerView(ActivityEmojiMaker.this, emojiObject.getLinkEmojiNomal(), ActivityEmojiMaker.this.rltEmoji, ActivityEmojiMaker.this.positionLv, ActivityEmojiMaker.this.rltEmojiBig.getHeight(), ActivityEmojiMaker.this.arrStickerList);
                        ActivityEmojiMaker.this.emojiAdapter.positionChange(i);
                    } else if (((EmojiObject) adapterView.getItemAtPosition(i)).getLinkEmojiNomal().contains("icon_plus")) {
                        ActivityEmojiMaker.this.startActivity(new Intent(ActivityEmojiMaker.this, ActivityCustomizeSmileys.class));
                    } else {
                        String unused = ActivityEmojiMaker.nameShape = ((EmojiObject) adapterView.getItemAtPosition(i)).getLinkEmojiNomal();
                        ActivityEmojiMaker.this.stickerUtils.addStickerView(ActivityEmojiMaker.this, emojiObject.getLinkEmojiNomal(), ActivityEmojiMaker.this.rltEmoji, ActivityEmojiMaker.this.positionLv, ActivityEmojiMaker.this.rltEmojiBig.getHeight(), ActivityEmojiMaker.this.arrStickerList);
                        ActivityEmojiMaker.this.emojiAdapter.positionChange(i);
                    }
                } else if (emojiObject.isLock()) {
                    ActivityEmojiMaker.this.mMoreEmojiDialog.showDialog(false, ActivityEmojiMaker.this);
                    ActivityEmojiMaker.this.mMoreEmojiDialog.changeTitle("Hi there!", "Would you like to have more emojis?");
                    ActivityEmojiMaker.this.mMoreEmojiDialog.showButton("Yes, I want!");
                    ActivityEmojiMaker.this.mMoreEmojiDialog.CommentOnGooglePlay(false);
                    ActivityEmojiMaker.this.mRatingDialog.changeTitle("Step 1!", "Please feel free to give us your feedback!");
                    ActivityEmojiMaker.this.mRatingDialog.isMoreEmoji(true);
                } else if (ActivityEmojiMaker.this.positionLv != 0 && ActivityEmojiMaker.this.positionLv != 1 && ActivityEmojiMaker.this.positionLv != 2) {
                    ActivityEmojiMaker.this.stickerUtils.addStickerView(ActivityEmojiMaker.this, emojiObject.getLinkEmojiNomal(), ActivityEmojiMaker.this.rltEmoji, ActivityEmojiMaker.this.positionLv, ActivityEmojiMaker.this.rltEmojiBig.getHeight(), ActivityEmojiMaker.this.arrStickerList);
                    ActivityEmojiMaker.this.emojiAdapter.positionChange(i);
                } else if (((EmojiObject) adapterView.getItemAtPosition(i)).getLinkEmojiNomal().contains("icon_plus")) {
                    ActivityEmojiMaker.this.startActivity(new Intent(ActivityEmojiMaker.this, ActivityCustomizeSmileys.class));
                } else {
                    String unused2 = ActivityEmojiMaker.nameShape = ((EmojiObject) adapterView.getItemAtPosition(i)).getLinkEmojiNomal();
                    ActivityEmojiMaker.this.stickerUtils.addStickerView(ActivityEmojiMaker.this, emojiObject.getLinkEmojiNomal(), ActivityEmojiMaker.this.rltEmoji, ActivityEmojiMaker.this.positionLv, ActivityEmojiMaker.this.rltEmojiBig.getHeight(), ActivityEmojiMaker.this.arrStickerList);
                    ActivityEmojiMaker.this.emojiAdapter.positionChange(i);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        StickerUtils stickerUtils = this.stickerUtils;
        if (StickerUtils.mCurrentView == null) {
            return true;
        }
        StickerUtils stickerUtils2 = this.stickerUtils;
        StickerUtils.mCurrentView.setInEdit(false);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        this.positionLv = i;
        this.emojiAdapter.dataChanges(this.listEmojiAll.get(i), i, nameShape, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION);
    }


    public class getEmoji extends AsyncTask<Void, Void, Void> {
        private getEmoji() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        public Void doInBackground(Void... voidArr) {
            ActivityEmojiMaker.this.listEmojiGr = new ArrayList();
            ActivityEmojiMaker.this.listEmojiGr = ActivityEmojiMaker.this.ultilsMethod.getPathEmojiFromAsstes("Shape", 0);
            return null;
        }


        public void onPostExecute(Void r10) {
            String unused = ActivityEmojiMaker.nameShape = ((EmojiObject) ActivityEmojiMaker.this.listEmojiGr.get(0)).getLinkEmojiNomal();
            ActivityEmojiMaker.this.emojiAdapter = new EmojiAdapter(ActivityEmojiMaker.this.listEmojiGr, ActivityEmojiMaker.this, ActivityEmojiMaker.nameShape, 0, 0, ActivityEmojiMaker.this.sharedPreferences);
            ActivityEmojiMaker.this.gvEmoji.setAdapter((ListAdapter) ActivityEmojiMaker.this.emojiAdapter);
            ActivityEmojiMaker.this.stickerUtils.addStickerView(ActivityEmojiMaker.this, ActivityEmojiMaker.nameShape, ActivityEmojiMaker.this.rltEmoji, 0, ActivityEmojiMaker.this.rltEmojiBig.getHeight(), ActivityEmojiMaker.this.arrStickerList);
            super.onPostExecute(r10);
        }
    }


    public class getAllEmoji extends AsyncTask<Void, Void, Void> {
        private getAllEmoji() {
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        public Void doInBackground(Void... voidArr) {
            ActivityEmojiMaker.this.listEmojiAll = new ArrayList();
            for (int i = 0; i < ActivityEmojiMaker.this.nameFolderEmoji.length; i++) {
                ActivityEmojiMaker.this.listEmojiAll.add(ActivityEmojiMaker.this.ultilsMethod.getPathEmojiFromAsstes(ActivityEmojiMaker.this.nameFolderEmoji[i], i));
            }
            try {
                List<EmojiObject> externalCacheDir = ActivityEmojiMaker.this.ultilsMethod.getExternalCacheDir(ActivityEmojiMaker.this, 2);
                externalCacheDir.add(0, ActivityEmojiMaker.this.ultilsMethod.getPathEmojiFromAsstes("EmojiUser", 2).get(0));
                ActivityEmojiMaker.this.listEmojiAll.set(2, externalCacheDir);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ActivityEmojiMaker.this.backgroundAdapter = new BackgroundAdapter((List) ActivityEmojiMaker.this.listEmojiAll.get(ActivityEmojiMaker.this.listEmojiAll.size() - 1), ActivityEmojiMaker.this, 0, ActivityEmojiMaker.this.sharedPreferences);
            ActivityEmojiMaker.this.gvBackground.setAdapter((ListAdapter) ActivityEmojiMaker.this.backgroundAdapter);
            if (MainActivity.dialogLoading != null && MainActivity.dialogLoading.isShowing()) {
                MainActivity.dialogLoading.dismiss();
                MainActivity.dialogLoading = null;
            }
            return null;
        }
    }

    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        if (i != 589) {
            if (i == 1306) {
                if (i2 == -1) {
                    try {
                        this.imgBackground.setColorFilter((ColorFilter) null);


                        Glide.with((Activity) this).load(this.ultilsMethod.getRealPathFromURI(intent.getData())).asBitmap().into(new SimpleTarget<Bitmap>() {
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                ActivityEmojiMaker.this.imgBackground.setImageBitmap(bitmap);
                                Bitmap unused = ActivityEmojiMaker.this.bmImageBackground = bitmap;
                                Bitmap unused2 = ActivityEmojiMaker.this.bmImageBackground = Bitmap.createScaledBitmap(ActivityEmojiMaker.this.bmImageBackground, (int) (((double) ActivityEmojiMaker.this.bmImageBackground.getWidth()) * 0.6d), (int) (((double) ActivityEmojiMaker.this.bmImageBackground.getHeight()) * 0.6d), true);
                                if (ActivityEmojiMaker.this.seekbarBlur.getProgress() > 1) {
                                    ActivityEmojiMaker.this.imgBackground.setImageBitmap(ActivityEmojiMaker.this.ultilsMethod.blurRenderScript(ActivityEmojiMaker.this.bmImageBackground, ActivityEmojiMaker.this.seekbarBlur.getProgress(), ActivityEmojiMaker.this));
                                }
                            }
                        });


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
                this.imgBackground.setColorFilter((ColorFilter) null);
                Glide.with((Activity) this).load(this.file).asBitmap().into(new SimpleTarget<Bitmap>() {
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        ActivityEmojiMaker.this.imgBackground.setImageBitmap(bitmap);
                        Bitmap unused = ActivityEmojiMaker.this.bmImageBackground = bitmap;
                        Bitmap unused2 = ActivityEmojiMaker.this.bmImageBackground = Bitmap.createScaledBitmap(ActivityEmojiMaker.this.bmImageBackground, (int) (((double) ActivityEmojiMaker.this.bmImageBackground.getWidth()) * 0.6d), (int) (((double) ActivityEmojiMaker.this.bmImageBackground.getHeight()) * 0.6d), true);
                        if (ActivityEmojiMaker.this.seekbarBlur.getProgress() > 1) {
                            ActivityEmojiMaker.this.imgBackground.setImageBitmap(ActivityEmojiMaker.this.ultilsMethod.blurRenderScript(ActivityEmojiMaker.this.bmImageBackground, ActivityEmojiMaker.this.seekbarBlur.getProgress(), ActivityEmojiMaker.this));
                        }
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

    private void setupDialog() {
        this.exitDialog = new ExitDialog(this);
        this.exitDialog.setRatingDialogListener(new ExitDialog.ExitDialogInterFace() {
            @Override
            public void maybe() {
            }

            @Override
            public void ok(boolean z) {
                ActivityEmojiMaker.this.finish();
            }
        });
    }

    private File createImageFile() throws IOException {
        File createTempFile = File.createTempFile("diy_emoji_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
        this.mCurrentPhotoPath = createTempFile.getAbsolutePath();
        return createTempFile;
    }

    @Override
    public void onBackPressed() {


        finish();
    }
}
