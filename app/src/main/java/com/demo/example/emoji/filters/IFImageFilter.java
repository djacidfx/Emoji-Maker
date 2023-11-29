package com.demo.example.emoji.filters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.OpenGlUtils;


public class IFImageFilter extends GPUImageFilter {
    private int filterInputTextureUniform2;
    private int filterInputTextureUniform3;
    private int filterInputTextureUniform4;
    private int filterInputTextureUniform5;
    private int filterInputTextureUniform6;
    public int filterSourceTexture2 = -1;
    public int filterSourceTexture3 = -1;
    public int filterSourceTexture4 = -1;
    public int filterSourceTexture5 = -1;
    public int filterSourceTexture6 = -1;
    private Context mContext;
    private List<Integer> mResIds;

    public IFImageFilter(Context context, String str) {
        super(GPUImageFilter.NO_FILTER_VERTEX_SHADER, str);
        this.mContext = context;
    }

    @Override 
    public void onInit() {
        super.onInit();
        this.filterInputTextureUniform2 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture2");
        this.filterInputTextureUniform3 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture3");
        this.filterInputTextureUniform4 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture4");
        this.filterInputTextureUniform5 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture5");
        this.filterInputTextureUniform6 = GLES20.glGetUniformLocation(getProgram(), "inputImageTexture6");
        initInputTexture();
    }

    @Override 
    public void onDestroy() {
        super.onDestroy();
        if (this.filterSourceTexture2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.filterSourceTexture2}, 0);
            this.filterSourceTexture2 = -1;
        }
        if (this.filterSourceTexture3 != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.filterSourceTexture3}, 0);
            this.filterSourceTexture3 = -1;
        }
        if (this.filterSourceTexture4 != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.filterSourceTexture4}, 0);
            this.filterSourceTexture4 = -1;
        }
        if (this.filterSourceTexture5 != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.filterSourceTexture5}, 0);
            this.filterSourceTexture5 = -1;
        }
        if (this.filterSourceTexture6 != -1) {
            GLES20.glDeleteTextures(1, new int[]{this.filterSourceTexture6}, 0);
            this.filterSourceTexture6 = -1;
        }
    }

    
    @Override 
    public void onDrawArraysPre() {
        super.onDrawArraysPre();
        if (this.filterSourceTexture2 != -1) {
            GLES20.glActiveTexture(33987);
            GLES20.glBindTexture(3553, this.filterSourceTexture2);
            GLES20.glUniform1i(this.filterInputTextureUniform2, 3);
        }
        if (this.filterSourceTexture3 != -1) {
            GLES20.glActiveTexture(33988);
            GLES20.glBindTexture(3553, this.filterSourceTexture3);
            GLES20.glUniform1i(this.filterInputTextureUniform3, 4);
        }
        if (this.filterSourceTexture4 != -1) {
            GLES20.glActiveTexture(33989);
            GLES20.glBindTexture(3553, this.filterSourceTexture4);
            GLES20.glUniform1i(this.filterInputTextureUniform4, 5);
        }
        if (this.filterSourceTexture5 != -1) {
            GLES20.glActiveTexture(33990);
            GLES20.glBindTexture(3553, this.filterSourceTexture5);
            GLES20.glUniform1i(this.filterInputTextureUniform5, 6);
        }
        if (this.filterSourceTexture6 != -1) {
            GLES20.glActiveTexture(33991);
            GLES20.glBindTexture(3553, this.filterSourceTexture6);
            GLES20.glUniform1i(this.filterInputTextureUniform6, 7);
        }
    }

    public void addInputTexture(int i) {
        if (this.mResIds == null) {
            this.mResIds = new ArrayList();
        }
        this.mResIds.add(Integer.valueOf(i));
    }

    public void initInputTexture() {
        if (this.mResIds != null) {
            if (this.mResIds.size() > 0) {
                runOnDraw(new Runnable() { 
                    @Override 
                    public void run() {
                        Bitmap decodeResource = BitmapFactory.decodeResource(IFImageFilter.this.mContext.getResources(), ((Integer) IFImageFilter.this.mResIds.get(0)).intValue());
                        IFImageFilter.this.filterSourceTexture2 = OpenGlUtils.loadTexture(decodeResource, -1, true);
                    }
                });
            }
            if (this.mResIds.size() > 1) {
                runOnDraw(new Runnable() { 
                    @Override 
                    public void run() {
                        Bitmap decodeResource = BitmapFactory.decodeResource(IFImageFilter.this.mContext.getResources(), ((Integer) IFImageFilter.this.mResIds.get(1)).intValue());
                        IFImageFilter.this.filterSourceTexture3 = OpenGlUtils.loadTexture(decodeResource, -1, true);
                    }
                });
            }
            if (this.mResIds.size() > 2) {
                runOnDraw(new Runnable() { 
                    @Override 
                    public void run() {
                        Bitmap decodeResource = BitmapFactory.decodeResource(IFImageFilter.this.mContext.getResources(), ((Integer) IFImageFilter.this.mResIds.get(2)).intValue());
                        IFImageFilter.this.filterSourceTexture4 = OpenGlUtils.loadTexture(decodeResource, -1, true);
                    }
                });
            }
            if (this.mResIds.size() > 3) {
                runOnDraw(new Runnable() { 
                    @Override 
                    public void run() {
                        Bitmap decodeResource = BitmapFactory.decodeResource(IFImageFilter.this.mContext.getResources(), ((Integer) IFImageFilter.this.mResIds.get(3)).intValue());
                        IFImageFilter.this.filterSourceTexture5 = OpenGlUtils.loadTexture(decodeResource, -1, true);
                    }
                });
            }
            if (this.mResIds.size() > 4) {
                runOnDraw(new Runnable() { 
                    @Override 
                    public void run() {
                        Bitmap decodeResource = BitmapFactory.decodeResource(IFImageFilter.this.mContext.getResources(), ((Integer) IFImageFilter.this.mResIds.get(4)).intValue());
                        IFImageFilter.this.filterSourceTexture6 = OpenGlUtils.loadTexture(decodeResource, -1, true);
                    }
                });
            }
        }
    }
}
