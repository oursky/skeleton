package com.oursky.skeleton.helper

import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.google.android.flexbox.FlexboxLayout

//! LayoutParams Builder
@Suppress("unused")
object LP {
    const val MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT
    const val WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT

    private fun toSpec(value: Int): Int {
        return when (value) {
            0, MATCH_PARENT, WRAP_CONTENT -> value
            else -> return if (Build.VERSION.SDK_INT >= 21) { // 5.0
                View.MeasureSpec.makeMeasureSpec(value, View.MeasureSpec.EXACTLY)
            } else {
                value // Android 4 bugged with makeMeasureSpec
            }
        }
    }

    // ViewGroup
    class ViewGroupLayoutBuilder internal constructor(width: Int, height: Int) {
        private val mParams = ViewGroup.LayoutParams(toSpec(width), toSpec(height))

        fun build(): ViewGroup.LayoutParams {
            return mParams
        }
    }

    // FrameLayout
    class FrameLayoutBuilder internal constructor(width: Int, height: Int, gravity: Int) {
        private val mParams = FrameLayout.LayoutParams(toSpec(width), toSpec(height), gravity)

        fun setMargins(left: Int, top: Int, right: Int, bottom: Int): FrameLayoutBuilder {
            mParams.setMargins(left, top, right, bottom)
            return this
        }
        fun setGravity(gravity: Int): FrameLayoutBuilder {
            mParams.gravity = gravity
            return this
        }
        fun build(): FrameLayout.LayoutParams {
            return mParams
        }
    }

    // LinearLayout
    class LinearLayoutBuilder {
        private var mParams: LinearLayout.LayoutParams

        internal constructor(width: Int, height: Int) {
            mParams = LinearLayout.LayoutParams(toSpec(width), toSpec(height))
        }
        internal constructor(width: Int, height: Int, gravity: Int) {
            mParams = LinearLayout.LayoutParams(toSpec(width), toSpec(height))
            mParams.gravity = gravity
        }
        fun setWeight(weight: Int): LinearLayoutBuilder {
            mParams.weight = weight.toFloat()
            return this
        }
        fun setMargins(left: Int, top: Int, right: Int, bottom: Int): LinearLayoutBuilder {
            mParams.setMargins(left, top, right, bottom)
            return this
        }
        fun setGravity(gravity: Int): LinearLayoutBuilder {
            mParams.gravity = gravity
            return this
        }
        fun build(): LinearLayout.LayoutParams? {
            return mParams
        }
    }

    // Relative
    class RelativeLayoutBuilder internal constructor(width: Int, height: Int) {
        private val mParams = RelativeLayout.LayoutParams(width, height)

        fun setMargins(left: Int, top: Int, right: Int, bottom: Int): RelativeLayoutBuilder {
            mParams.setMargins(left, top, right, bottom)
            return this
        }
        fun alignParentEnd(): RelativeLayoutBuilder {
            mParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 1)
            return this
        }
        fun alignParentBottom(): RelativeLayoutBuilder {
            mParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 1)
            return this
        }
        fun toLeftOf(id: Int): RelativeLayoutBuilder {
            mParams.addRule(RelativeLayout.LEFT_OF, id)
            return this
        }
        fun toRightOf(id: Int): RelativeLayoutBuilder {
            mParams.addRule(RelativeLayout.RIGHT_OF, id)
            return this
        }
        fun build(): RelativeLayout.LayoutParams {
            return mParams
        }
    }

    // Flexbox
    class FlexboxLayoutBuilder internal constructor(width: Int, height: Int) {
        private val mParams = FlexboxLayout.LayoutParams(toSpec(width), toSpec(height))

        fun setMargins(left: Int, top: Int, right: Int, bottom: Int): FlexboxLayoutBuilder {
            mParams.setMargins(left, top, right, bottom)
            return this
        }
        fun setBasisPercent(percent: Float): FlexboxLayoutBuilder {
            mParams.flexBasisPercent = percent
            return this
        }
        fun setAlignSelf(value: Int): FlexboxLayoutBuilder {
            mParams.alignSelf = value
            return this
        }
        fun build(): FlexboxLayout.LayoutParams {
            return mParams
        }
    }

    fun vg(width: Int, height: Int): ViewGroupLayoutBuilder {
        return ViewGroupLayoutBuilder(width, height)
    }
    fun frame(width: Int, height: Int): FrameLayoutBuilder {
        return FrameLayoutBuilder(width, height, 0)
    }
    fun frame(width: Int, height: Int, gravity: Int): FrameLayoutBuilder {
        return FrameLayoutBuilder(width, height, gravity)
    }
    fun linear(width: Int, height: Int): LinearLayoutBuilder {
        return LinearLayoutBuilder(width, height)
    }
    fun linear(width: Int, height: Int, gravity: Int): LinearLayoutBuilder {
        return LinearLayoutBuilder(width, height, gravity)
    }
    fun relative(width: Int, height: Int): RelativeLayoutBuilder {
        return RelativeLayoutBuilder(width, height)
    }
    fun flexbox(width: Int, height: Int): FlexboxLayoutBuilder {
        return FlexboxLayoutBuilder(width, height)
    }
}
