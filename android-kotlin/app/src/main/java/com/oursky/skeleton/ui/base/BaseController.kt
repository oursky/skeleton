package com.oursky.skeleton.ui.base

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.oursky.skeleton.helper.LP

@Suppress("unused")
abstract class BaseController : Controller {
    // delegate
    protected abstract fun onCreateView(context: Context): View

    //region Lifecycle
    //---------------------------------------------------------------
    constructor(): super()
    constructor(args: Bundle): super(args)
    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val context = container.context
        val view = onCreateView(context)
        val blockable = BlockableFrame(context)
        blockable.addView(view, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build())
        return blockable
    }
    //---------------------------------------------------------------
    //endregion

    //region Block clicks while transition
    //---------------------------------------------------------------
    override fun onChangeStarted(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeStarted(changeHandler, changeType)
        val v = view
        if (v != null && v is BlockableFrame) v.setBlockTouch(true)
    }
    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        val v = view
        if (v != null && v is BlockableFrame) v.setBlockTouch(false)
    }
    @Suppress("unused", "UNUSED_PARAMETER")
    private class BlockableFrame : FrameLayout {
        private var mBlockTouch: Boolean = false
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
            mBlockTouch = false
        }
        fun setBlockTouch(b: Boolean) {
            mBlockTouch = b
        }
        override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
            return mBlockTouch || super.onInterceptTouchEvent(ev)
        }
    }
    //---------------------------------------------------------------
    //endregion

    //region Navigation
    //---------------------------------------------------------------
    protected fun popController() {
        router.popCurrentController()
    }
    protected fun pushController(controller: Controller) {
        router.pushController(RouterTransaction.with(controller)
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler())
        )
    }
    protected fun pushController(controller: Controller, pushEffect: ControllerChangeHandler, popEffect: ControllerChangeHandler) {
        router.pushController(RouterTransaction.with(controller)
                .pushChangeHandler(pushEffect)
                .popChangeHandler(popEffect)
        )
    }
    protected fun topController(controller: Controller, pushEffect: ControllerChangeHandler, popEffect: ControllerChangeHandler) {
        router.replaceTopController(RouterTransaction.with(controller)
                .pushChangeHandler(pushEffect)
                .popChangeHandler(popEffect)
        )
    }
    //---------------------------------------------------------------
    //endregion
}
