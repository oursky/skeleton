package com.oursky.skeleton.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.FontRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.SparseArray;
import android.util.TypedValue;
import java.lang.ref.WeakReference;

@SuppressWarnings({"WeakerAccess", "unused"})
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

    //region Font support
    private static SparseArray<Typeface> sFontCache = new SparseArray<>();
    public static Typeface font(@FontRes int font) {
        if (sFontCache.indexOfKey(font) >= 0) {
            return sFontCache.get(font);
        } else {
            Context context = sContext.get();
            if (context == null) return null;
            Typeface cache = ResourcesCompat.getFont(context, font);
            sFontCache.put(font, cache);
            return cache;
        }
    }
    //endregion
}
