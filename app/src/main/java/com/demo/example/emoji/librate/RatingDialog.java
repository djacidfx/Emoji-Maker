package com.demo.example.emoji.librate;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.example.R;


public class RatingDialog {
    private ImageView btnCacncel;
    private TextView btnMaybe;
    private TextView btnSubmit;
    private Context context;
    private Dialog dialog;
    SharedPreferences.Editor edit;
    private Activity mActivity;
    private String mDescription;
    private boolean mIsFinish;
    private boolean mMoreEmoji;
    RatingDialogInterFace mRatingDialogListener;
    private String mTitle;
    private RelativeLayout main;
    SharedPreferences pre;
    private ImageView ratingFace;
    private RotationRatingBar rotationratingbar_main;
    private TextView tvDes;
    private TextView tvTitle;
    private boolean isEnable = true;
    private int defRating = 0;

    
    public interface RatingDialogInterFace {
        void maybe();

        void onDismiss();

        void onRatingChanged(float f);

        void onSubmit(float f, boolean z);
    }

    public RatingDialog(Context context) {
        this.context = context;
        this.pre = context.getSharedPreferences("rateData", 0);
        this.edit = this.pre.edit();
        this.dialog = new Dialog(this.context);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.dialogmain);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.btnCacncel = (ImageView) this.dialog.findViewById(R.id.btnCacncel);
        this.ratingFace = (ImageView) this.dialog.findViewById(R.id.ratingFace);
        this.main = (RelativeLayout) this.dialog.findViewById(R.id.main);
        this.rotationratingbar_main = (RotationRatingBar) this.dialog.findViewById(R.id.rotationratingbar_main);
        this.btnSubmit = (TextView) this.dialog.findViewById(R.id.btnSubmit);
        this.btnMaybe = (TextView) this.dialog.findViewById(R.id.btnMaybe);
        this.tvTitle = (TextView) this.dialog.findViewById(R.id.tvTitle);
        this.tvDes = (TextView) this.dialog.findViewById(R.id.tvDes);
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public void onDismiss(DialogInterface dialogInterface) {
                RatingDialog.this.main.setRotation(0.0f);
                RatingDialog.this.main.setAlpha(0.0f);
                RatingDialog.this.main.setScaleY(0.0f);
                RatingDialog.this.main.setScaleX(0.0f);
                RatingDialog.this.main.clearAnimation();
                RatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (RatingDialog.this.mRatingDialogListener != null) {
                    RatingDialog.this.mRatingDialogListener.onDismiss();
                }
            }
        });
        this.btnCacncel.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                RatingDialog.this.closeDialog();
            }
        });
        this.rotationratingbar_main.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() { 
            @Override 
            public void onRatingChange(BaseRatingBar baseRatingBar, float f) {
                if (baseRatingBar.getRating() < 4.0f) {
                    RatingDialog.this.setRatingFace(false);
                } else {
                    RatingDialog.this.setRatingFace(true);
                }
                if (RatingDialog.this.mRatingDialogListener != null) {
                    RatingDialog.this.mRatingDialogListener.onRatingChanged(RatingDialog.this.rotationratingbar_main.getRating());
                }
            }
        });
        this.btnSubmit.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                RatingDialog.this.main.animate().scaleY(0.0f).scaleX(0.0f).alpha(0.0f).rotation(-1800.0f).setDuration(600).setListener(new Animator.AnimatorListener() { 
                    @Override 
                    public void onAnimationCancel(Animator animator) {
                    }

                    @Override 
                    public void onAnimationRepeat(Animator animator) {
                    }

                    @Override 
                    public void onAnimationStart(Animator animator) {
                    }

                    @Override 
                    public void onAnimationEnd(Animator animator) {
                        RatingDialog.this.dialog.dismiss();
                        RatingDialog.this.main.clearAnimation();
                        RatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                        if (RatingDialog.this.mRatingDialogListener != null) {
                            RatingDialog.this.mRatingDialogListener.onSubmit(RatingDialog.this.rotationratingbar_main.getRating(), RatingDialog.this.mMoreEmoji);
                        }
                    }
                }).start();
            }
        });
        this.btnMaybe.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                RatingDialog.this.dialog.dismiss();
                RatingDialog.this.main.clearAnimation();
                RatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (RatingDialog.this.mIsFinish) {
                    RatingDialog.this.mActivity.finish();
                }
                if (RatingDialog.this.mRatingDialogListener != null) {
                    RatingDialog.this.mRatingDialogListener.maybe();
                }
            }
        });
    }

    public void changeTitle(String str, String str2) {
        this.mTitle = str;
        this.mDescription = str2;
        this.tvDes.setText(this.mDescription);
        this.tvTitle.setText(this.mTitle);
    }

    public void showDialog(boolean z, Activity activity) {
        this.mIsFinish = z;
        this.mActivity = activity;
        this.isEnable = this.pre.getBoolean("enb", true);
        if (this.isEnable) {
            this.dialog.show();
            this.rotationratingbar_main.clearAnimation();
            this.rotationratingbar_main.setRating((float) this.defRating);
            setRatingFace(true);
            this.main.animate().scaleY(1.0f).scaleX(1.0f).rotation(1800.0f).alpha(1.0f).setDuration(600).setListener(new Animator.AnimatorListener() { 
                @Override 
                public void onAnimationCancel(Animator animator) {
                }

                @Override 
                public void onAnimationRepeat(Animator animator) {
                }

                @Override 
                public void onAnimationStart(Animator animator) {
                }

                @Override 
                public void onAnimationEnd(Animator animator) {
                    RatingDialog.this.main.clearAnimation();
                    RatingDialog.this.rotationratingbar_main.setVisibility(View.VISIBLE);
                    RatingDialog.this.rotationratingbar_main.startAnimation(AnimationUtils.loadAnimation(RatingDialog.this.context, R.anim.bounce_amn));
                }
            }).start();
        }
    }

    public void setEnable(boolean z) {
        this.edit.putBoolean("enb", z);
        this.edit.commit();
    }

    public boolean getEnable() {
        return this.pre.getBoolean("enb", true);
    }

    
    public void setRatingFace(boolean z) {
        if (z) {
            this.ratingFace.setImageResource(R.drawable.favorite);
        } else {
            this.ratingFace.setImageResource(R.drawable.favorite2);
        }
    }

    public void closeDialog() {
        this.main.animate().scaleY(0.0f).scaleX(0.0f).alpha(0.0f).rotation(-1800.0f).setDuration(600).setListener(new Animator.AnimatorListener() { 
            @Override 
            public void onAnimationCancel(Animator animator) {
            }

            @Override 
            public void onAnimationRepeat(Animator animator) {
            }

            @Override 
            public void onAnimationStart(Animator animator) {
            }

            @Override 
            public void onAnimationEnd(Animator animator) {
                RatingDialog.this.dialog.dismiss();
                RatingDialog.this.main.clearAnimation();
                RatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (RatingDialog.this.mRatingDialogListener != null) {
                    RatingDialog.this.mRatingDialogListener.onDismiss();
                }
            }
        }).start();
    }

    public void isMoreEmoji(boolean z) {
        this.mMoreEmoji = z;
    }

    public void setDefaultRating(int i) {
        this.defRating = i;
    }

    public void setRatingDialogListener(RatingDialogInterFace ratingDialogInterFace) {
        this.mRatingDialogListener = ratingDialogInterFace;
    }
}
