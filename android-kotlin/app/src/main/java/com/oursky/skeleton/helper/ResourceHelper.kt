package com.oursky.skeleton.helper

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.FontRes
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.SparseArray
import android.util.TypedValue
import java.lang.ref.WeakReference

@Suppress("unused")
object ResourceHelper {
    private var context: WeakReference<Context>? = null
    fun setup(c: Context) {
        context = WeakReference(c)
    }
    fun dp(value: Float): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context?.get()!!.resources.displayMetrics))
    }
    fun dp(value: Int): Int {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), context?.get()!!.resources.displayMetrics))
    }
    fun dimen(@DimenRes resId: Int): Int {
        return context?.get()!!.resources.getDimensionPixelOffset(resId)
    }
    fun color(@ColorRes resId: Int): Int {
        return ContextCompat.getColor(context?.get()!!, resId)
    }

    private var sFontCache: SparseArray<Typeface> = SparseArray()
    fun font(@FontRes resId: Int): Typeface? {
        return if (sFontCache.indexOfKey(resId) >= 0) {
            sFontCache.get(resId)
        } else {
            val con = context?.get()
            val cache = if (con!=null) ResourcesCompat.getFont(con, resId) else null
            if (cache != null) sFontCache.put(resId, cache)
            cache
        }
    }
}