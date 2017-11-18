package com.oursky.skeleton.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import java.lang.ref.WeakReference;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

public class ResourceHelper {
    private static WeakReference<Context> sContext;
    public static void setContext(Context context) {
        sContext = new WeakReference<>(context);
    }

    public static int dp(int dp) {
        Context context = sContext.get();
        if (context == null) return dp;
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
    public static int dimen(@DimenRes int res) {
        Context context = sContext.get();
        if (context == null) return 0;
        return context.getResources().getDimensionPixelOffset(res);
    }
    public static int color(@ColorRes int res) {
        Context context = sContext.get();
        if (context == null) return 0;
        return ContextCompat.getColor(context, res);
    }
    public static Typeface font(String font) {
        Context context = sContext.get();
        if (context == null) return null;
        return TypefaceUtils.load(context.getAssets(), "fonts/" + font);
    }
}
