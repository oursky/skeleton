package com.oursky.skeleton.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.graphics.drawable.StateListDrawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.AppCompatCheckBox
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.Touchable
import com.oursky.skeleton.helper.ResourceHelper.dp

@Suppress("unused", "UNUSED_PARAMETER")
class Checkbox : LinearLayout {
    private val mCheckbox: AppCompatCheckBox
    private val mLabel: TextView

    var onCheckChanged: ((view: Checkbox, isChecked: Boolean) -> Unit)? = null
    var isChecked: Boolean
        get() = mCheckbox.isChecked
        set(b) {
            mCheckbox.isChecked = b
        }

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context) {
        mCheckbox = AppCompatCheckBox(context)
        mLabel = TextView(context)
        initView(context, null, 0)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mCheckbox = AppCompatCheckBox(context)
        mLabel = TextView(context)
        initView(context, attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        mCheckbox = AppCompatCheckBox(context)
        mLabel = TextView(context)
        initView(context, attrs, defStyle)
    }
    private fun initView(context: Context, attrs: AttributeSet?, defStyle: Int) {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        mCheckbox.setButtonDrawable(android.R.color.transparent)
        mLabel.setPadding(dp(16), 0, dp(16), 0)
        mLabel.visibility = View.GONE
        addView(mCheckbox, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build())
        addView(mLabel, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())
        Touchable.make(this)
        mCheckbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckChanged?.invoke(this@Checkbox, isChecked)
        }
        setOnClickListener { _ -> mCheckbox.isChecked = !mCheckbox.isChecked }
    }
    //---------------------------------------------------------------
    //endregion

    // Block all touch event to child, we toggle with onClick over top layout
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return true
    }

    //region Checkbox Functions
    //---------------------------------------------------------------
    fun setText(text: String) {
        mLabel.text = text
        mLabel.visibility = if (text.isEmpty()) View.GONE else View.VISIBLE
    }
    fun setText(@StringRes text: Int) {
        mLabel.setText(text)
        mLabel.visibility = View.VISIBLE
    }
    fun setTextColor(@ColorInt color: Int) {
        mLabel.setTextColor(color)
    }
    fun setTextColor(color: ColorStateList) {
        mLabel.setTextColor(color)
    }
    fun setTextSize(s: Float) {
        mLabel.textSize = s
    }
    fun setTypeface(tf: Typeface?) {
        mLabel.setTypeface(tf, Typeface.NORMAL)
    }
    fun setTypeface(tf: Typeface, style: Int) {
        mLabel.setTypeface(tf, style)
    }
    fun setAllCaps(b: Boolean) {
        mLabel.setAllCaps(b)
    }
    //---------------------------------------------------------------
    //endregion

    //region Icon
    //---------------------------------------------------------------
    fun setButtonDrawable(@DrawableRes resId: Int, width: Int, height: Int) {
        val lp = mCheckbox.layoutParams
        lp.width = width
        lp.height = height
        mCheckbox.layoutParams = lp
        mCheckbox.setBackgroundResource(resId)
    }
    fun setButtonDrawable(drawable: StateListDrawable, width: Int, height: Int) {
        val lp = mCheckbox.layoutParams
        lp.width = width
        lp.height = height
        mCheckbox.layoutParams = lp
        mCheckbox.background = drawable
    }
    //---------------------------------------------------------------
    //endregion
}
