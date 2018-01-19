package com.oursky.skeleton.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout

import com.oursky.skeleton.R
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.ViewTransition

import com.oursky.skeleton.helper.ResourceHelper.color

abstract class AppBottomPopup : FrameLayout {
    private val ANIMATION_DURATION = 250
    private var mDimBackground: View? = null
    private var mContentView: View? = null
    var isVisible: Boolean = false
        private set
    private var mInTransition: Boolean = false

    protected abstract fun onCreateView(context: Context): View
    protected fun onAttach(view: View) {}
    protected fun onDetach(view: View) {}

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    // NOTE: It is bad to call abstract function from constructor, as child's member is not properly instantiated during super construction execution.
    //       So, We will do raii to create our view before onAttach
    private fun raii() {
        if (mContentView != null) return
        val context = context
        isVisible = false
        mInTransition = false
        mDimBackground = View(context)
        mDimBackground!!.setBackgroundColor(color(R.color.immersive_background))
        mDimBackground!!.isClickable = true
        addView(mDimBackground, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build())
        mContentView = onCreateView(context)
        addView(mContentView, LP.frame(LP.MATCH_PARENT, LP.WRAP_CONTENT, Gravity.BOTTOM).build())
        mDimBackground!!.visibility = View.INVISIBLE
        mContentView!!.visibility = View.INVISIBLE
    }
    //---------------------------------------------------------------
    //endregion

    //region Attach / Detach
    //---------------------------------------------------------------
    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        raii()
        onAttach(mContentView!!)
    }
    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        onDetach(mContentView!!)
    }
    //---------------------------------------------------------------
    //endregion

    //region Visibility
    //---------------------------------------------------------------
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mInTransition || super.onInterceptTouchEvent(ev)
    }
    fun show(visible: Boolean) {
        if (mInTransition || isVisible == visible) return
        isVisible = visible
        if (isVisible) {
            mInTransition = true
            ViewTransition.fadeIn(mDimBackground!!, ANIMATION_DURATION) { mInTransition = false }
            ViewTransition.slideIn(mContentView!!, ViewTransition.Direction.UP, mContentView!!.height, ANIMATION_DURATION, null)
        } else {
            mInTransition = true
            ViewTransition.fadeOut(mDimBackground!!, ANIMATION_DURATION) { mInTransition = false }
            ViewTransition.slideOut(mContentView!!, ViewTransition.Direction.DOWN, mContentView!!.height, ANIMATION_DURATION, null)
        }
    }
    //---------------------------------------------------------------
    //endregion
}
