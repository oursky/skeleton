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

object ResourceHelper {
    var context: WeakReference<Context>? = null

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
    fun font(@FontRes resId: Int): Typeface {
        if (sFontCache.indexOfKey(resId) >= 0) {
            return sFontCache.get(resId)
        } else {
            val cache = ResourcesCompat.getFont(context?.get()!!, resId)
            sFontCache.put(resId, cache)
            return cache!!
        }
    }
}