package com.oursky.skeleton.ui

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout

import com.oursky.skeleton.R
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.Logger
import com.oursky.skeleton.ui.base.BaseBottomPopup
import com.oursky.skeleton.widget.Button
import com.oursky.skeleton.widget.Checkbox

import com.oursky.skeleton.helper.ResourceHelper.dp
import com.oursky.skeleton.helper.ResourceHelper.color
import com.oursky.skeleton.helper.ResourceHelper.font

class DummyPopup : BaseBottomPopup {
    var onClose: ((self: DummyPopup) -> Unit)? = null

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
    override fun onCreateView(context: Context): View {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER_HORIZONTAL
        layout.setBackgroundColor(color(R.color.white))

        val checkbox = Checkbox(context)
        checkbox.setText(R.string.dummy_check)
        checkbox.setTypeface(font(R.font.barlow_condensed_regular))
        checkbox.setTextSize(20f)
        checkbox.setButtonDrawable(R.drawable.ic_checkbox, dp(28), dp(28))
        layout.addView(checkbox, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT)
                .setMargins(0, dp(32), 0, dp(32))
                .build())

        val close = Button(context)
        close.setTextPadding(dp(16), dp(8), dp(16), dp(8))
        close.setBackgroundColor(color(R.color.main_next_bg))
        close.setTextColor(color(R.color.main_next_text))
        close.setTypeface(font(R.font.barlow_condensed_bold))
        close.setTextSize(24f)
        close.setText(R.string.dummy_close)
        layout.addView(close, LP.linear(LP.WRAP_CONTENT, LP.WRAP_CONTENT)
                .setMargins(0, dp(96), 0, dp(96))
                .build())

        // Register event listener
        checkbox.onCheckChanged = onCheckToggle
        close.setOnClickListener(onCloseClick)
        return layout
    }
    //---------------------------------------------------------------
    //endregion

    override fun onAttach(view: View){}
    override fun onDetach(view: View){}

    //region UI Events
    //---------------------------------------------------------------
    private val onCloseClick = OnClickListener{ _ ->
        onClose?.invoke(this@DummyPopup)
    }
    private val onCheckToggle = { _: Checkbox, isChecked: Boolean ->
        Logger.d("DummyPopup", "Checkbox: " + if (isChecked) "YES" else "NO")
    }
    //---------------------------------------------------------------
    //endregion
}
