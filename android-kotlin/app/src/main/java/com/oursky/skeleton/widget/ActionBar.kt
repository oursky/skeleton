package com.oursky.skeleton.widget

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView

import com.oursky.skeleton.R
import com.oursky.skeleton.helper.LP

import com.oursky.skeleton.helper.ResourceHelper.dimen
import com.oursky.skeleton.helper.ResourceHelper.dp
import com.oursky.skeleton.helper.ResourceHelper.font
import com.oursky.skeleton.helper.ResourceHelper.color

class ActionBar : LinearLayout {
    private var mLeft: LinearLayout? = null
    private var mRight: LinearLayout? = null
    private var mCenter: FrameLayout? = null
    private var mTitle: TextView? = null

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context) {
        ctor(context, null, 0)
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        ctor(context, attrs, 0)
    }
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        ctor(context, attrs, defStyle)
    }
    private fun ctor(context: Context, attrs: AttributeSet?, defStyle: Int) {
        val bar = LinearLayout(context)
        bar.setPadding(dp(4), dp(4), dp(4), dp(4))
        bar.orientation = LinearLayout.HORIZONTAL
        bar.gravity = Gravity.CENTER_VERTICAL
        mLeft = LinearLayout(context)
        mLeft!!.orientation = LinearLayout.HORIZONTAL
        mLeft!!.gravity = Gravity.CENTER_VERTICAL
        bar.addView(mLeft, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build())
        mCenter = FrameLayout(context)
        bar.addView(mCenter, LP.linear(0, LP.WRAP_CONTENT)
                .setWeight(1)
                .build())
        mRight = LinearLayout(context)
        mRight!!.orientation = LinearLayout.HORIZONTAL
        mRight!!.gravity = Gravity.CENTER_VERTICAL
        bar.addView(mRight, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT).build())

        val line = View(context)
        line.setBackgroundColor(0x60000000)

        orientation = LinearLayout.VERTICAL
        addView(bar, LP.linear(LP.MATCH_PARENT, dimen(R.dimen.actionbar_height)).build())
        addView(line, LP.linear(LP.MATCH_PARENT, 1).build())
    }
    //---------------------------------------------------------------
    //endregion

    //region Title
    //---------------------------------------------------------------
    private fun raiiTitle() {
        if (mTitle != null) return
        mTitle = TextView(context)
        mTitle!!.gravity = Gravity.CENTER_HORIZONTAL
        mTitle!!.typeface = font(R.font.barlow_condensed_medium)
        mTitle!!.setTextColor(color(R.color.actionbar_title))
        mTitle!!.textSize = 20f
        mCenter!!.addView(mTitle, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER)
                .build())
    }
    fun setTitle(@StringRes title: Int) {
        raiiTitle()
        mTitle!!.setText(title)
    }
    fun setTitle(title: String) {
        raiiTitle()
        mTitle!!.text = title
    }
    fun setTitleView(v: View) {
        mCenter!!.addView(mTitle, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER)
                .build())
    }
    //---------------------------------------------------------------
    //endregion

    //region Button
    //---------------------------------------------------------------
    fun addLeftButton(@DrawableRes icon: Int, listener: View.OnClickListener) {
        val button = Button(context)
        button.setIcon(icon, dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
        button.setOnClickListener(listener)
        mLeft!!.addView(button, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                .setMargins(0, 0, dp(16), 0)
                .build())
    }
    fun addLeftPadding() {
        val view = View(context)
        mLeft!!.addView(view, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                .setMargins(0, 0, dp(16), 0)
                .build())
    }
    fun addRightButton(@DrawableRes icon: Int, listener: View.OnClickListener) {
        val button = Button(context)
        button.setIcon(icon, dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
        button.setOnClickListener(listener)
        mRight!!.addView(button, 0, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                .setMargins(0, 0, dp(16), 0)
                .build())
    }
    fun addRightPadding() {
        val view = View(context)
        mRight!!.addView(view, 0, LP.linear(dimen(R.dimen.actionbar_icon), dimen(R.dimen.actionbar_icon))
                .setMargins(0, 0, dp(16), 0)
                .build())
    }
    //---------------------------------------------------------------
    //endregion
}
