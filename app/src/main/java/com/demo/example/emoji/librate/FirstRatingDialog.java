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


public class FirstRatingDialog {
    private ImageView btnCacncel;
    private TextView btnMaybe;
    private TextView btnSubmit;
    private Context context;
    private Dialog dialog;
    SharedPreferences.Editor edit;
    RatingDialogInterFace mRatingDialogListener;
    private RelativeLayout main;
    SharedPreferences pre;
    private ImageView ratingFace;
    private RotationRatingBar rotationratingbar_main;
    private boolean isEnable = true;
    private int defRating = 0;

    
    public interface RatingDialogInterFace {
        void onDismiss();

        void onMaybe();

        void onRatingChanged(float f);

        void onSubmit(float f);
    }

    public FirstRatingDialog(Context context) {
        this.context = context;
        this.pre = context.getSharedPreferences("rateData", 0);
        this.edit = this.pre.edit();
        this.dialog = new Dialog(this.context);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.first_create_main);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.btnCacncel = (ImageView) this.dialog.findViewById(R.id.btnCacncel);
        this.ratingFace = (ImageView) this.dialog.findViewById(R.id.ratingFace);
        this.main = (RelativeLayout) this.dialog.findViewById(R.id.main);
        this.rotationratingbar_main = (RotationRatingBar) this.dialog.findViewById(R.id.rotationratingbar_main);
        this.btnSubmit = (TextView) this.dialog.findViewById(R.id.btnSubmit);
        this.btnMaybe = (TextView) this.dialog.findViewById(R.id.btnMaybe);
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public void onDismiss(DialogInterface dialogInterface) {
                FirstRatingDialog.this.main.setRotation(0.0f);
                FirstRatingDialog.this.main.setAlpha(0.0f);
                FirstRatingDialog.this.main.setScaleY(0.0f);
                FirstRatingDialog.this.main.setScaleX(0.0f);
                FirstRatingDialog.this.main.clearAnimation();
                FirstRatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (FirstRatingDialog.this.mRatingDialogListener != null) {
                    FirstRatingDialog.this.mRatingDialogListener.onDismiss();
                }
            }
        });
        this.btnCacncel.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FirstRatingDialog.this.closeDialog();
            }
        });
        this.rotationratingbar_main.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() { 
            @Override 
            public void onRatingChange(BaseRatingBar baseRatingBar, float f) {
                if (baseRatingBar.getRating() < 4.0f) {
                    FirstRatingDialog.this.setRatingFace(false);
                } else {
                    FirstRatingDialog.this.setRatingFace(true);
                }
                if (FirstRatingDialog.this.mRatingDialogListener != null) {
                    FirstRatingDialog.this.mRatingDialogListener.onRatingChanged(FirstRatingDialog.this.rotationratingbar_main.getRating());
                }
            }
        });
        this.btnSubmit.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FirstRatingDialog.this.main.animate().scaleY(0.0f).scaleX(0.0f).alpha(0.0f).rotation(-1800.0f).setDuration(600).setListener(new Animator.AnimatorListener() { 
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
                        FirstRatingDialog.this.dialog.dismiss();
                        FirstRatingDialog.this.main.clearAnimation();
                        FirstRatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                        if (FirstRatingDialog.this.mRatingDialogListener != null) {
                            FirstRatingDialog.this.mRatingDialogListener.onSubmit(FirstRatingDialog.this.rotationratingbar_main.getRating());
                        }
                    }
                }).start();
            }
        });
        this.btnMaybe.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FirstRatingDialog.this.dialog.dismiss();
                FirstRatingDialog.this.main.clearAnimation();
                FirstRatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (FirstRatingDialog.this.mRatingDialogListener != null) {
                    FirstRatingDialog.this.mRatingDialogListener.onMaybe();
                }
            }
        });
    }

    public void showDialog(boolean z, Activity activity) {
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
                    FirstRatingDialog.this.main.clearAnimation();
                    FirstRatingDialog.this.rotationratingbar_main.setVisibility(View.VISIBLE);
                    FirstRatingDialog.this.rotationratingbar_main.startAnimation(AnimationUtils.loadAnimation(FirstRatingDialog.this.context, R.anim.bounce_amn));
                }
            }).start();
        }
        if (z) {
            activity.finish();
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
            this.ratingFace.setImageResource(R.drawable.icon_first_create);
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
                FirstRatingDialog.this.dialog.dismiss();
                FirstRatingDialog.this.main.clearAnimation();
                FirstRatingDialog.this.rotationratingbar_main.setVisibility(View.INVISIBLE);
                if (FirstRatingDialog.this.mRatingDialogListener != null) {
                    FirstRatingDialog.this.mRatingDialogListener.onDismiss();
                }
            }
        }).start();
    }

    public void setDefaultRating(int i) {
        this.defRating = i;
    }

    public void setRatingDialogListener(RatingDialogInterFace ratingDialogInterFace) {
        this.mRatingDialogListener = ratingDialogInterFace;
    }
}
