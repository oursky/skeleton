package com.oursky.skeleton.ui

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

import com.oursky.skeleton.R
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.ResourceHelper.color
import com.oursky.skeleton.helper.ResourceHelper.dp
import com.oursky.skeleton.helper.ResourceHelper.font
import com.oursky.skeleton.redux.ViewStore
import com.oursky.skeleton.ui.base.BaseController
import com.oursky.skeleton.widget.Button

import com.oursky.skeleton.MainApplication.Companion.store

class MainScreen : BaseController() {
    private var mTitle: TextView? = null
    private val mSubscriptions = CompositeDisposable()

    //region Lifecycle
    //---------------------------------------------------------------
    override fun onCreateView(context: Context): View {
        val layout = FrameLayout(context)
        mTitle = TextView(context)
        mTitle!!.textSize = 32f
        mTitle!!.typeface = font(R.font.barlow_condensed_bold)
        layout.addView(mTitle, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER).build())

        val next = Button(context)
        next.setTextPadding(dp(32), dp(12), dp(32), dp(12))
        next.setBackgroundColor(color(R.color.main_next_bg))
        next.setTextColor(color(R.color.main_next_text))
        next.setTypeface(font(R.font.barlow_condensed_bold))
        next.setTextSize(24f)
        next.setText(R.string.main_next)
        layout.addView(next, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT, Gravity.CENTER or Gravity.BOTTOM)
                .setMargins(0, dp(16), 0, dp(16))
                .build())
        // Register event listener
        next.setOnClickListener(onNextClick)
        return layout
    }
    override fun onAttach(view: View) {
        super.onAttach(view)
        mSubscriptions.add(store!!.observe(store!!.view)
                                  .distinctUntilChanged()
                                  .observeOn(AndroidSchedulers.mainThread())
                                  .map(mapTitle)
                                  .subscribe(consumeTitle)
        )
    }
    override fun onDetach(view: View) {
        super.onDetach(view)
        mSubscriptions.clear()
    }
    //---------------------------------------------------------------
    //endregion

    //region UI Events
    //---------------------------------------------------------------
    private val onNextClick = { _: View ->
        pushController(SecondScreen())
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private val mapTitle = Function<ViewStore.State,String> { state ->
        state.title
    }
    private val consumeTitle = Consumer<String> { mapped ->
        mTitle?.text = mapped
    }
    //---------------------------------------------------------------
    //endregion
}
