package com.oursky.skeleton.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.Touchable

@Suppress("unused", "UNUSED_PARAMETER")
class Button : FrameLayout {
    private val mText: TextView
    private var mIcon: ImageView? = null
    private var mScaleType: ImageView.ScaleType
    private var mEnabled: Boolean = false

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context) {
        mText = TextView(context)
        mScaleType = ImageView.ScaleType.FIT_CENTER
        initView(context, null, 0)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mText = TextView(context)
        mScaleType = ImageView.ScaleType.FIT_CENTER
        initView(context, attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mText = TextView(context)
        mScaleType = ImageView.ScaleType.FIT_CENTER
        initView(context, attrs, defStyle)
    }
    private fun initView(context: Context, attrs: AttributeSet?, defStyle: Int) {
        mEnabled = true
        mText.setAllCaps(true)
        mText.background = null
        mText.gravity = Gravity.CENTER
        addView(mText, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build())
        Touchable.make(this)
    }
    //---------------------------------------------------------------
    //endregion

    //region UI State Functions
    //---------------------------------------------------------------
    override fun setEnabled(b: Boolean) {
        super.setEnabled(b)
        if (mEnabled != b) {
            mEnabled = b
            alpha = if (mEnabled) 1.0f else 0.5f
        }
    }
    override fun setActivated(activated: Boolean) {
        super.setActivated(activated)
        mIcon?.isActivated = activated
    }
    //---------------------------------------------------------------
    //endregion

    //region Text Functions
    //---------------------------------------------------------------
    fun setText(@StringRes stringId: Int) {
        mText.setText(stringId)
    }
    fun setText(text: String) {
        mText.text = text
    }
    fun setTextColor(@ColorInt color: Int) {
        mText.setTextColor(color)
    }
    fun setTextColor(color: ColorStateList) {
        mText.setTextColor(color)
    }
    fun setTextSize(s: Float) {
        mText.textSize = s
    }
    fun setTypeface(tf: Typeface?) {
        mText.setTypeface(tf, Typeface.NORMAL)
    }
    fun setTypeface(tf: Typeface?, style: Int) {
        mText.setTypeface(tf, style)
    }
    fun setAllCaps(b: Boolean) {
        mText.setAllCaps(b)
    }
    fun setTextPadding(left: Int, top: Int, right: Int, bottom: Int) {
        mText.setPadding(left, top, right, bottom)
    }
    //region Icon Functions
    //---------------------------------------------------------------
    @JvmOverloads
    fun setIcon(@DrawableRes resId: Int,
                width: Int = FrameLayout.LayoutParams.MATCH_PARENT,
                height: Int = FrameLayout.LayoutParams.MATCH_PARENT,
                margin: Int = 0) {
        if (mIcon == null) {
            mIcon = ImageView(context)
            mIcon?.scaleType = mScaleType
            addView(mIcon, LP.frame(width, height, Gravity.CENTER_VERTICAL)
                    .setMargins(margin, margin, margin, margin)
                    .build())
        } else {
            mIcon?.layoutParams = LP.frame(width, height, Gravity.CENTER_VERTICAL)
                    .setMargins(margin, margin, margin, margin)
                    .build()
        }
        mIcon?.setImageResource(resId)
        mIcon?.isActivated = super.isActivated()
    }
    fun setScaleType(scaleType: ImageView.ScaleType) {
        mScaleType = scaleType
        mIcon?.scaleType = scaleType
    }
    //---------------------------------------------------------------
    //endregion
}
