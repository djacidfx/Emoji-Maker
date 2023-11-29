package com.demo.example.emoji.librate;

import android.animation.Animator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.example.R;


public class FeedbackDialog {
    private TextView btnMaybe;
    private TextView btnSubmit;
    private Context context;
    private Dialog dialog;
    SharedPreferences.Editor edit;
    private EditText edtFeedback;
    private boolean isMoreEmoji;
    FeedbackDialogInterFace mFeedbackDialogListener;
    private RelativeLayout main;
    SharedPreferences pre;
    private boolean isEnable = true;
    private int defFeedback = 0;

    
    public interface FeedbackDialogInterFace {
        void onDismiss();

        void onSubmit(String str, boolean z);
    }

    public FeedbackDialog(Context context) {
        this.context = context;
        this.pre = context.getSharedPreferences("feedbackData", 0);
        this.edit = this.pre.edit();
        this.dialog = new Dialog(this.context);
        this.dialog.requestWindowFeature(1);
        this.dialog.setContentView(R.layout.feedbackmain);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.edtFeedback = (EditText) this.dialog.findViewById(R.id.edtFeedback);
        this.main = (RelativeLayout) this.dialog.findViewById(R.id.main);
        this.btnSubmit = (TextView) this.dialog.findViewById(R.id.btnSubmit);
        this.btnMaybe = (TextView) this.dialog.findViewById(R.id.btnMaybe);
        this.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public void onDismiss(DialogInterface dialogInterface) {
                FeedbackDialog.this.main.setRotation(0.0f);
                FeedbackDialog.this.main.setAlpha(0.0f);
                FeedbackDialog.this.main.setScaleY(0.0f);
                FeedbackDialog.this.main.setScaleX(0.0f);
                FeedbackDialog.this.main.clearAnimation();
                if (FeedbackDialog.this.mFeedbackDialogListener != null) {
                    FeedbackDialog.this.mFeedbackDialogListener.onDismiss();
                }
            }
        });
        this.btnSubmit.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (FeedbackDialog.this.edtFeedback.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(FeedbackDialog.this.context, "Give us a feedback", 1).show();
                } else {
                    FeedbackDialog.this.main.animate().scaleY(0.0f).scaleX(0.0f).alpha(0.0f).setDuration(500).setListener(new Animator.AnimatorListener() { 
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
                            FeedbackDialog.this.dialog.dismiss();
                            FeedbackDialog.this.main.clearAnimation();
                            if (FeedbackDialog.this.mFeedbackDialogListener != null) {
                                FeedbackDialog.this.mFeedbackDialogListener.onSubmit(FeedbackDialog.this.edtFeedback.getText().toString().trim(), FeedbackDialog.this.isMoreEmoji);
                            }
                        }
                    }).start();
                }
            }
        });
        this.btnMaybe.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FeedbackDialog.this.dialog.dismiss();
                FeedbackDialog.this.main.clearAnimation();
            }
        });
    }

    public void dismissDialog() {
        this.dialog.dismiss();
        this.main.clearAnimation();
        if (this.mFeedbackDialogListener != null) {
            this.mFeedbackDialogListener.onDismiss();
        }
    }

    public void showDialog(boolean z, Activity activity) {
        this.isEnable = this.pre.getBoolean("enb", true);
        if (this.isEnable) {
            this.dialog.show();
            this.main.animate().scaleY(1.0f).scaleX(1.0f).alpha(1.0f).setDuration(500).setListener(new Animator.AnimatorListener() { 
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
                    FeedbackDialog.this.main.clearAnimation();
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
                FeedbackDialog.this.dialog.dismiss();
                FeedbackDialog.this.main.clearAnimation();
                if (FeedbackDialog.this.mFeedbackDialogListener != null) {
                    FeedbackDialog.this.mFeedbackDialogListener.onDismiss();
                }
            }
        }).start();
    }

    public void setDefaultFeedback(int i) {
        this.defFeedback = i;
    }

    public void setFeedbackDialogListener(FeedbackDialogInterFace feedbackDialogInterFace) {
        this.mFeedbackDialogListener = feedbackDialogInterFace;
    }

    public void isMoreEmoji(boolean z) {
        this.isMoreEmoji = z;
    }
}
