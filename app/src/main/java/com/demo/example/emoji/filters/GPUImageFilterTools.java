package com.demo.example.emoji.filters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.opengl.Matrix;

import com.demo.example.R;

import java.util.LinkedList;
import java.util.List;
import jp.co.cyberagent.android.gpuimage.GPUImage3x3ConvolutionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDirectionalSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFalseColorFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLaplacianFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageNonMaximumSuppressionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBDilationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToneCurveFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTwoInputFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWeakPixelInclusionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;


public class GPUImageFilterTools {

    
    public enum FilterType {
        CONTRAST,
        GRAYSCALE,
        SHARPEN,
        SEPIA,
        SOBEL_EDGE_DETECTION,
        THREE_X_THREE_CONVOLUTION,
        FILTER_GROUP,
        EMBOSS,
        POSTERIZE,
        GAMMA,
        BRIGHTNESS,
        INVERT,
        HUE,
        PIXELATION,
        SATURATION,
        EXPOSURE,
        HIGHLIGHT_SHADOW,
        MONOCHROME,
        OPACITY,
        RGB,
        WHITE_BALANCE,
        VIGNETTE,
        TONE_CURVE,
        BLEND_COLOR_BURN,
        BLEND_COLOR_DODGE,
        BLEND_DARKEN,
        BLEND_DIFFERENCE,
        BLEND_DISSOLVE,
        BLEND_EXCLUSION,
        BLEND_SOURCE_OVER,
        BLEND_HARD_LIGHT,
        BLEND_LIGHTEN,
        BLEND_ADD,
        BLEND_DIVIDE,
        BLEND_MULTIPLY,
        BLEND_OVERLAY,
        BLEND_SCREEN,
        BLEND_ALPHA,
        BLEND_COLOR,
        BLEND_HUE,
        BLEND_SATURATION,
        BLEND_LUMINOSITY,
        BLEND_LINEAR_BURN,
        BLEND_SOFT_LIGHT,
        BLEND_SUBTRACT,
        BLEND_CHROMA_KEY,
        BLEND_NORMAL,
        LOOKUP_AMATORKA,
        GAUSSIAN_BLUR,
        CROSSHATCH,
        BOX_BLUR,
        CGA_COLORSPACE,
        DILATION,
        KUWAHARA,
        RGB_DILATION,
        SKETCH,
        TOON,
        SMOOTH_TOON,
        BULGE_DISTORTION,
        GLASS_SPHERE,
        HAZE,
        LAPLACIAN,
        NON_MAXIMUM_SUPPRESSION,
        SPHERE_REFRACTION,
        SWIRL,
        WEAK_PIXEL_INCLUSION,
        FALSE_COLOR,
        COLOR_BALANCE,
        LEVELS_FILTER_MIN,
        BILATERAL_BLUR,
        HALFTONE,
        TRANSFORM2D,
        I_1977,
        I_AMARO,
        I_BRANNAN,
        I_EARLYBIRD,
        I_HEFE,
        I_HUDSON,
        I_INKWELL,
        I_LORDKELVIN,
        I_NASHVILLE,
        I_SUTRO,
        I_VALENCIA,
        I_WALDEN,
        I_XPROII,
        I_LOMO,
        I_SIERRA,
        I_TOASTER,
        AFTER_GLOW,
        AUGUST_MARCH,
        BABY_FACE,
        BLOOD_ORANGE,
        COLDDESERT,
        COUNTRY,
        FOGY_BLUE,
        FRESH_BLUE,
        GHOSTSINYOUR_HEAD,
        GOLDEN_HOUR,
        GREEN_ENVY,
        KISSKISS,
        LULLABYE,
        MOTH_WING,
        MYSTERY,
        OLD_POSTCARDS_II,
        PISTOL,
        REVERS_AND_RAIN,
        SPARKS,
        TOES,
        TONEL_EMON,
        TRAINS,
        TWIN_LUNGS,
        WILD_AT_HEART,
        WINDOWN_WARMTH,
        XANH,
        TEXTURE_1,
        TEXTURE_2,
        TEXTURE_3,
        TEXTURE_4,
        TEXTURE_5,
        TEXTURE_6,
        TEXTURE_7,
        TEXTURE_72,
        TEXTURE_8,
        TEXTURE_9,
        TEXTURE_10,
        TEXTURE_102,
        TEXTURE_11,
        TEXTURE_112,
        TEXTURE_12,
        TEXTURE_122,
        TEXTURE_13,
        TEXTURE_132,
        TEXTURE_14,
        TEXTURE_142,
        TextTure14Filter,
        TextTure10Filter,
        TextTure13Filter,
        IFRiseFilter,
        TextTure2Filter,
        TextTure8Filter,
        TextTure4Filter,
        TextTure7Filter,
        TextTure9Filter,
        TextTure12Filter,
        TextTure5Filter,
        TextTure6Filter
    }

    
    public interface OnGpuImageFilterChosenListener {
        void onGpuImageFilterChosenListener(GPUImageFilter gPUImageFilter, int i);
    }

    public static GPUImageFilter createFilterForType(Context context, FilterType filterType) {
        switch (filterType) {
            case CONTRAST:
                return new GPUImageContrastFilter(2.0f);
            case GAMMA:
                return new GPUImageGammaFilter(2.0f);
            case INVERT:
                return new GPUImageColorInvertFilter();
            case PIXELATION:
                return new GPUImagePixelationFilter();
            case HUE:
                return new GPUImageHueFilter(90.0f);
            case BRIGHTNESS:
                return new GPUImageBrightnessFilter(1.5f);
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case SHARPEN:
                GPUImageSharpenFilter gPUImageSharpenFilter = new GPUImageSharpenFilter();
                gPUImageSharpenFilter.setSharpness(2.0f);
                return gPUImageSharpenFilter;
            case SOBEL_EDGE_DETECTION:
                return new GPUImageSobelEdgeDetection();
            case THREE_X_THREE_CONVOLUTION:
                GPUImage3x3ConvolutionFilter gPUImage3x3ConvolutionFilter = new GPUImage3x3ConvolutionFilter();
                gPUImage3x3ConvolutionFilter.setConvolutionKernel(new float[]{-1.0f, 0.0f, 1.0f, -2.0f, 0.0f, 2.0f, -1.0f, 0.0f, 1.0f});
                return gPUImage3x3ConvolutionFilter;
            case EMBOSS:
                return new GPUImageEmbossFilter();
            case POSTERIZE:
                return new GPUImagePosterizeFilter();
            case FILTER_GROUP:
                LinkedList linkedList = new LinkedList();
                linkedList.add(new GPUImageContrastFilter());
                linkedList.add(new GPUImageDirectionalSobelEdgeDetectionFilter());
                linkedList.add(new GPUImageGrayscaleFilter());
                return new GPUImageFilterGroup(linkedList);
            case SATURATION:
                return new GPUImageSaturationFilter(1.0f);
            case EXPOSURE:
                return new GPUImageExposureFilter(0.0f);
            case HIGHLIGHT_SHADOW:
                return new GPUImageHighlightShadowFilter(0.0f, 1.0f);
            case MONOCHROME:
                return new GPUImageMonochromeFilter(1.0f, new float[]{0.6f, 0.45f, 0.3f, 1.0f});
            case OPACITY:
                return new GPUImageOpacityFilter(1.0f);
            case RGB:
                return new GPUImageRGBFilter(1.0f, 1.0f, 1.0f);
            case WHITE_BALANCE:
                return new GPUImageWhiteBalanceFilter(5000.0f, 0.0f);
            case VIGNETTE:
                PointF pointF = new PointF();
                pointF.x = 0.5f;
                pointF.y = 0.5f;
                return new GPUImageVignetteFilter(pointF, new float[]{0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case TONE_CURVE:
                GPUImageToneCurveFilter gPUImageToneCurveFilter = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.tone_cuver_sample));
                return gPUImageToneCurveFilter;
            case GAUSSIAN_BLUR:
                return new GPUImageGaussianBlurFilter();
            case CROSSHATCH:
                return new GPUImageCrosshatchFilter();
            case BOX_BLUR:
                return new GPUImageBoxBlurFilter();
            case CGA_COLORSPACE:
                return new GPUImageCGAColorspaceFilter();
            case DILATION:
                return new GPUImageDilationFilter();
            case KUWAHARA:
                return new GPUImageKuwaharaFilter();
            case RGB_DILATION:
                return new GPUImageRGBDilationFilter();
            case SKETCH:
                return new GPUImageSketchFilter();
            case TOON:
                return new GPUImageToonFilter();
            case SMOOTH_TOON:
                return new GPUImageSmoothToonFilter();
            case BULGE_DISTORTION:
                return new GPUImageBulgeDistortionFilter();
            case GLASS_SPHERE:
                return new GPUImageGlassSphereFilter();
            case HAZE:
                return new GPUImageHazeFilter();
            case LAPLACIAN:
                return new GPUImageLaplacianFilter();
            case NON_MAXIMUM_SUPPRESSION:
                return new GPUImageNonMaximumSuppressionFilter();
            case SPHERE_REFRACTION:
                return new GPUImageSphereRefractionFilter();
            case SWIRL:
                return new GPUImageSwirlFilter();
            case WEAK_PIXEL_INCLUSION:
                return new GPUImageWeakPixelInclusionFilter();
            case FALSE_COLOR:
                return new GPUImageFalseColorFilter();
            case COLOR_BALANCE:
                return new GPUImageColorBalanceFilter();
            case LEVELS_FILTER_MIN:
                GPUImageLevelsFilter gPUImageLevelsFilter = new GPUImageLevelsFilter();
                gPUImageLevelsFilter.setMin(0.0f, 3.0f, 1.0f);
                return gPUImageLevelsFilter;
            case HALFTONE:
                return new GPUImageHalftoneFilter();
            case BILATERAL_BLUR:
                return new GPUImageBilateralFilter();
            case TRANSFORM2D:
                return new GPUImageTransformFilter();
            case I_1977:
                return new IF1977Filter(context);
            case AFTER_GLOW:
                GPUImageToneCurveFilter gPUImageToneCurveFilter2 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter2.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.afterglow));
                return gPUImageToneCurveFilter2;
            case AUGUST_MARCH:
                GPUImageToneCurveFilter gPUImageToneCurveFilter3 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter3.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.august_march));
                return gPUImageToneCurveFilter3;
            case BABY_FACE:
                GPUImageToneCurveFilter gPUImageToneCurveFilter4 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter4.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.babyface));
                return gPUImageToneCurveFilter4;
            case BLOOD_ORANGE:
                GPUImageToneCurveFilter gPUImageToneCurveFilter5 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter5.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.blood_orange));
                return gPUImageToneCurveFilter5;
            case COLDDESERT:
                GPUImageToneCurveFilter gPUImageToneCurveFilter6 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter6.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.colddesert));
                return gPUImageToneCurveFilter6;
            case COUNTRY:
                GPUImageToneCurveFilter gPUImageToneCurveFilter7 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter7.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.country));
                return gPUImageToneCurveFilter7;
            case FOGY_BLUE:
                GPUImageToneCurveFilter gPUImageToneCurveFilter8 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter8.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.goldenhour));
                return gPUImageToneCurveFilter8;
            case FRESH_BLUE:
                GPUImageToneCurveFilter gPUImageToneCurveFilter9 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter9.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.fresh_blue));
                return gPUImageToneCurveFilter9;
            case GHOSTSINYOUR_HEAD:
                GPUImageToneCurveFilter gPUImageToneCurveFilter10 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter10.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.ghostsinyourhead));
                return gPUImageToneCurveFilter10;
            case GOLDEN_HOUR:
                GPUImageToneCurveFilter gPUImageToneCurveFilter11 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter11.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.goldenhour));
                return gPUImageToneCurveFilter11;
            case GREEN_ENVY:
                GPUImageToneCurveFilter gPUImageToneCurveFilter12 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter12.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.greenenvy));
                return gPUImageToneCurveFilter12;
            case KISSKISS:
                GPUImageToneCurveFilter gPUImageToneCurveFilter13 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter13.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.kisskiss));
                return gPUImageToneCurveFilter13;
            case LULLABYE:
                GPUImageToneCurveFilter gPUImageToneCurveFilter14 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter14.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.lullabye));
                return gPUImageToneCurveFilter14;
            case MOTH_WING:
                GPUImageToneCurveFilter gPUImageToneCurveFilter15 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter15.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.moth_wings));
                return gPUImageToneCurveFilter15;
            case MYSTERY:
                GPUImageToneCurveFilter gPUImageToneCurveFilter16 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter16.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.mystery));
                return gPUImageToneCurveFilter16;
            case OLD_POSTCARDS_II:
                GPUImageToneCurveFilter gPUImageToneCurveFilter17 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter17.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.old_postcards_ii));
                return gPUImageToneCurveFilter17;
            case PISTOL:
                GPUImageToneCurveFilter gPUImageToneCurveFilter18 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter18.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.pistol));
                return gPUImageToneCurveFilter18;
            case REVERS_AND_RAIN:
                GPUImageToneCurveFilter gPUImageToneCurveFilter19 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter19.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.rivers_and_rain));
                return gPUImageToneCurveFilter19;
            case SPARKS:
                GPUImageToneCurveFilter gPUImageToneCurveFilter20 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter20.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.sparks));
                return gPUImageToneCurveFilter20;
            case TOES:
                GPUImageToneCurveFilter gPUImageToneCurveFilter21 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter21.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.toes));
                return gPUImageToneCurveFilter21;
            case TONEL_EMON:
                GPUImageToneCurveFilter gPUImageToneCurveFilter22 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter22.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.tonelemon));
                return gPUImageToneCurveFilter22;
            case TRAINS:
                GPUImageToneCurveFilter gPUImageToneCurveFilter23 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter23.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.trains));
                return gPUImageToneCurveFilter23;
            case TWIN_LUNGS:
                GPUImageToneCurveFilter gPUImageToneCurveFilter24 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter24.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.twin_lungs));
                return gPUImageToneCurveFilter24;
            case WILD_AT_HEART:
                GPUImageToneCurveFilter gPUImageToneCurveFilter25 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter25.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.wild_at_heart));
                return gPUImageToneCurveFilter25;
            case WINDOWN_WARMTH:
                GPUImageToneCurveFilter gPUImageToneCurveFilter26 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter26.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.windowwarmth));
                return gPUImageToneCurveFilter26;
            case XANH:
                GPUImageToneCurveFilter gPUImageToneCurveFilter27 = new GPUImageToneCurveFilter();
                gPUImageToneCurveFilter27.setFromCurveFileInputStream(context.getResources().openRawResource(R.raw.xanh));
                return gPUImageToneCurveFilter27;
            default:
                throw new IllegalStateException("No filter of that type!");
        }
    }

    private static GPUImageFilter createBlendFilter(Context context, Class<? extends GPUImageTwoInputFilter> cls, int i) {
        try {
            GPUImageTwoInputFilter gPUImageTwoInputFilter = (GPUImageTwoInputFilter) cls.newInstance();
            gPUImageTwoInputFilter.setBitmap(BitmapFactory.decodeResource(context.getResources(), i));
            return gPUImageTwoInputFilter;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    
    private static class FilterList {
        public List<String> names = new LinkedList();
        public List<FilterType> filters = new LinkedList();

        private FilterList() {
        }

        public void addFilter(String str, FilterType filterType) {
            this.names.add(str);
            this.filters.add(filterType);
        }
    }

    private void hihi() {
        new GPUImageToneCurveFilter();
    }

    
    public static class FilterAdjuster {
        private final Adjuster<? extends GPUImageFilter> adjuster;

        public FilterAdjuster(GPUImageFilter gPUImageFilter) {
            if (gPUImageFilter instanceof GPUImageSharpenFilter) {
                this.adjuster = new SharpnessAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSepiaFilter) {
                this.adjuster = new SepiaAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageContrastFilter) {
                this.adjuster = new ContrastAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGammaFilter) {
                this.adjuster = new GammaAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBrightnessFilter) {
                this.adjuster = new BrightnessAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSobelEdgeDetection) {
                this.adjuster = new SobelAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageEmbossFilter) {
                this.adjuster = new EmbossAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImage3x3TextureSamplingFilter) {
                this.adjuster = new GPU3x3TextureAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHueFilter) {
                this.adjuster = new HueAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePosterizeFilter) {
                this.adjuster = new PosterizeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePixelationFilter) {
                this.adjuster = new PixelationAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSaturationFilter) {
                this.adjuster = new SaturationAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageExposureFilter) {
                this.adjuster = new ExposureAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHighlightShadowFilter) {
                this.adjuster = new HighlightShadowAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageMonochromeFilter) {
                this.adjuster = new MonochromeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageOpacityFilter) {
                this.adjuster = new OpacityAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageRGBFilter) {
                this.adjuster = new RGBAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageWhiteBalanceFilter) {
                this.adjuster = new WhiteBalanceAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageVignetteFilter) {
                this.adjuster = new VignetteAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageDissolveBlendFilter) {
                this.adjuster = new DissolveBlendAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGaussianBlurFilter) {
                this.adjuster = new GaussianBlurAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageCrosshatchFilter) {
                this.adjuster = new CrosshatchBlurAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBulgeDistortionFilter) {
                this.adjuster = new BulgeDistortionAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGlassSphereFilter) {
                this.adjuster = new GlassSphereAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHazeFilter) {
                this.adjuster = new HazeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSphereRefractionFilter) {
                this.adjuster = new SphereRefractionAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSwirlFilter) {
                this.adjuster = new SwirlAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageColorBalanceFilter) {
                this.adjuster = new ColorBalanceAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageLevelsFilter) {
                this.adjuster = new LevelsMinMidAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBilateralFilter) {
                this.adjuster = new BilateralAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageTransformFilter) {
                this.adjuster = new RotateAdjuster().filter(gPUImageFilter);
            } else {
                this.adjuster = null;
            }
        }

        public boolean canAdjust() {
            return this.adjuster != null;
        }

        public void adjust(int i) {
            if (this.adjuster != null) {
                this.adjuster.adjust(i);
            }
        }

        
        private abstract class Adjuster<T extends GPUImageFilter> {
            private T filter;

            public abstract void adjust(int i);

            protected float range(int i, float f, float f2) {
                return (((f2 - f) * ((float) i)) / 100.0f) + f;
            }

            private Adjuster() {
            }

            
            public Adjuster<T> filter(GPUImageFilter gPUImageFilter) {
                this.filter = (T) gPUImageFilter;
                return this;
            }

            public T getFilter() {
                return this.filter;
            }

            protected int range(int i, int i2, int i3) {
                return (((i3 - i2) * i) / 100) + i2;
            }
        }

        
        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            private SharpnessAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setSharpness(range(i, -4.0f, 4.0f));
            }
        }

        
        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            private PixelationAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setPixel(range(i, 1.0f, 100.0f));
            }
        }

        
        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            private HueAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setHue(range(i, 0.0f, 360.0f));
            }
        }

        
        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            private ContrastAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setContrast(range(i, 0.0f, 2.0f));
            }
        }

        
        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            private GammaAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setGamma(range(i, 0.0f, 3.0f));
            }
        }

        
        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            private BrightnessAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setBrightness(range(i, -1.0f, 1.0f));
            }
        }

        
        private class SepiaAdjuster extends Adjuster<GPUImageSepiaFilter> {
            private SepiaAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setIntensity(range(i, 0.0f, 2.0f));
            }
        }

        
        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetection> {
            private SobelAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        
        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            private EmbossAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setIntensity(range(i, 0.0f, 4.0f));
            }
        }

        
        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            private PosterizeAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setColorLevels(range(i, 1, 50));
            }
        }

        
        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            private GPU3x3TextureAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        
        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            private SaturationAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setSaturation(range(i, 0.0f, 2.0f));
            }
        }

        
        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            private ExposureAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setExposure(range(i, -10.0f, 10.0f));
            }
        }

        
        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            private HighlightShadowAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setShadows(range(i, 0.0f, 1.0f));
                getFilter().setHighlights(range(i, 0.0f, 1.0f));
            }
        }

        
        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            private MonochromeAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setIntensity(range(i, 0.0f, 1.0f));
            }
        }

        
        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            private OpacityAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setOpacity(range(i, 0.0f, 1.0f));
            }
        }

        
        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            private RGBAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setRed(range(i, 0.0f, 1.0f));
            }
        }

        
        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            private WhiteBalanceAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setTemperature(range(i, 2000.0f, 8000.0f));
            }
        }

        
        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            private VignetteAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setVignetteStart(range(i, 0.0f, 1.0f));
            }
        }

        
        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            private DissolveBlendAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setMix(range(i, 0.0f, 1.0f));
            }
        }

        
        private class GaussianBlurAdjuster extends Adjuster<GPUImageGaussianBlurFilter> {
            private GaussianBlurAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setBlurSize(range(i, 0.0f, 1.0f));
            }
        }

        
        private class CrosshatchBlurAdjuster extends Adjuster<GPUImageCrosshatchFilter> {
            private CrosshatchBlurAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setCrossHatchSpacing(range(i, 0.0f, 0.06f));
                getFilter().setLineWidth(range(i, 0.0f, 0.006f));
            }
        }

        
        private class BulgeDistortionAdjuster extends Adjuster<GPUImageBulgeDistortionFilter> {
            private BulgeDistortionAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setRadius(range(i, 0.0f, 1.0f));
                getFilter().setScale(range(i, -1.0f, 1.0f));
            }
        }

        
        private class GlassSphereAdjuster extends Adjuster<GPUImageGlassSphereFilter> {
            private GlassSphereAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setRadius(range(i, 0.0f, 1.0f));
            }
        }

        
        private class HazeAdjuster extends Adjuster<GPUImageHazeFilter> {
            private HazeAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setDistance(range(i, -0.3f, 0.3f));
                getFilter().setSlope(range(i, -0.3f, 0.3f));
            }
        }

        
        private class SphereRefractionAdjuster extends Adjuster<GPUImageSphereRefractionFilter> {
            private SphereRefractionAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setRadius(range(i, 0.0f, 1.0f));
            }
        }

        
        private class SwirlAdjuster extends Adjuster<GPUImageSwirlFilter> {
            private SwirlAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setAngle(range(i, 0.0f, 2.0f));
            }
        }

        
        private class ColorBalanceAdjuster extends Adjuster<GPUImageColorBalanceFilter> {
            private ColorBalanceAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setMidtones(new float[]{range(i, 0.0f, 1.0f), range(i / 2, 0.0f, 1.0f), range(i / 3, 0.0f, 1.0f)});
            }
        }

        
        private class LevelsMinMidAdjuster extends Adjuster<GPUImageLevelsFilter> {
            private LevelsMinMidAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setMin(0.0f, range(i, 0.0f, 1.0f), 1.0f);
            }
        }

        
        private class BilateralAdjuster extends Adjuster<GPUImageBilateralFilter> {
            private BilateralAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                getFilter().setDistanceNormalizationFactor(range(i, 0.0f, 15.0f));
            }
        }

        
        private class RotateAdjuster extends Adjuster<GPUImageTransformFilter> {
            private RotateAdjuster() {
                super();
            }

            @Override 
            public void adjust(int i) {
                float[] fArr = new float[16];
                Matrix.setRotateM(fArr, 0, (float) ((360 * i) / 100), 0.0f, 0.0f, 1.0f);
                getFilter().setTransform3D(fArr);
            }
        }
    }
}
