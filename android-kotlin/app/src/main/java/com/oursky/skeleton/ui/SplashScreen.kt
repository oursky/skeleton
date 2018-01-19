package com.oursky.skeleton.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.oursky.skeleton.R
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.ResourceHelper.font
import com.oursky.skeleton.ui.base.AppController

class SplashScreen : AppController() {
    override fun onCreateView(context: Context): View {
        val layout = FrameLayout(context)
        val tv = TextView(context)
        tv.typeface = font(R.font.barlow_condensed_thin)
        tv.textSize = 32f
        tv.setText(R.string.splash_title)
        layout.addView(tv, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER).build())
        return layout
    }
}
