package com.oursky.skeleton.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class BaseFrameLayout : FrameLayout {
    // delegate
    protected abstract fun onAttach(view: View)
    protected abstract fun onDetach(view: View)

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)
    //---------------------------------------------------------------
    //endregion

    //region Attach / Detach
    //---------------------------------------------------------------
    final override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onAttach(this)
    }
    final override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDetach(this)
    }
    //---------------------------------------------------------------
    //endregion
}
