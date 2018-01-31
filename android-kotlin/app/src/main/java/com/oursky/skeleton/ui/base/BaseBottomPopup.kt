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

@Suppress("unused", "PrivatePropertyName")
abstract class BaseBottomPopup : FrameLayout {
    // delegate
    protected abstract fun onCreateView(context: Context): View
    protected abstract fun onAttach(view: View)
    protected abstract fun onDetach(view: View)

    private val ANIMATION_DURATION = 250
    private var mDimBackground: View? = null
    private var mContentView: View? = null
    var isVisible: Boolean = false
        private set
    private var mInTransition: Boolean = false

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    // NOTE: It is bad to call abstract function from constructor, as child's member is not properly instantiated during super construction execution.
    //       So, We will do raii to create our view before onAttach
    private fun raii() {
        if (mContentView != null) return
        val context = context
        isVisible = false
        mInTransition = false
        mDimBackground = View(context)
        mDimBackground?.setBackgroundColor(color(R.color.immersive_background))
        mDimBackground?.isClickable = true
        mDimBackground?.visibility = View.INVISIBLE
        mContentView = onCreateView(context)
        mContentView?.visibility = View.INVISIBLE
        addView(mDimBackground, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build())
        addView(mContentView, LP.frame(LP.MATCH_PARENT, LP.WRAP_CONTENT, Gravity.BOTTOM).build())
    }
    //---------------------------------------------------------------
    //endregion

    //region Attach / Detach
    //---------------------------------------------------------------
    public override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        raii()
        mContentView?.let { onAttach(it) }
    }
    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mContentView?.let { onDetach(it) }
    }
    //---------------------------------------------------------------
    //endregion

    //region Visibility
    //---------------------------------------------------------------
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return mInTransition || super.onInterceptTouchEvent(ev)
    }
    fun show(visible: Boolean) {
        val bg = mDimBackground ?: return
        val content = mContentView ?: return
        if (mInTransition || isVisible == visible) return
        if (mDimBackground == null || mContentView == null) return
        isVisible = visible
        if (isVisible) {
            mInTransition = true
            ViewTransition.fadeIn(bg, ANIMATION_DURATION) { mInTransition = false }
            ViewTransition.slideIn(content, ViewTransition.Direction.UP, content.height, ANIMATION_DURATION, null)
        } else {
            mInTransition = true
            ViewTransition.fadeOut(bg, ANIMATION_DURATION) { mInTransition = false }
            ViewTransition.slideOut(content, ViewTransition.Direction.DOWN, content.height, ANIMATION_DURATION, null)
        }
    }
    //---------------------------------------------------------------
    //endregion
}
