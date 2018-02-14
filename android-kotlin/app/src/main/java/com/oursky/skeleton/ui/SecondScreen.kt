package com.oursky.skeleton.ui

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout

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
import com.oursky.skeleton.widget.ActionBar
import com.oursky.skeleton.widget.Button
import com.oursky.skeleton.ui.base.BaseController
import com.oursky.skeleton.MainApplication.Companion.store

class SecondScreen : BaseController() {
    private var mPopup: DummyPopup? = null
    private val mSubscriptions = CompositeDisposable()

    //region Lifecycle
    //---------------------------------------------------------------
    override fun onCreateView(context: Context): View {
        val contentView = LinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        val actionbar = ActionBar(context)
        actionbar.addLeftButton(R.drawable.ic_back, onBackClick)
        actionbar.addRightPadding()
        actionbar.setTitle(R.string.app_name)
        contentView.addView(actionbar, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())

        val popup = Button(context)
        popup.setTextPadding(dp(32), dp(12), dp(32), dp(12))
        popup.setBackgroundColor(color(R.color.main_next_bg))
        popup.setTextColor(color(R.color.main_next_text))
        popup.setTypeface(font(R.font.barlow_condensed_bold))
        popup.setTextSize(24f)
        popup.setText(R.string.main_popup)
        contentView.addView(popup, LP.frame(LP.WRAP_CONTENT, LP.WRAP_CONTENT)
                .setMargins(0, dp(64), 0, 0)
                .build())

        mPopup = DummyPopup(context)

        val frame = FrameLayout(context)
        frame.addView(contentView, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build())
        frame.addView(mPopup, LP.frame(LP.MATCH_PARENT, LP.MATCH_PARENT).build())

        // Register event listener
        popup.setOnClickListener(onPopupClick)
        mPopup?.onClose = onPopupClose
        return frame
    }
    override fun onAttach(view: View) {
        super.onAttach(view)
        mSubscriptions.add(store().observe(store().view)
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapTitle)
                .distinctUntilChanged()
                .subscribe(consumeTitle)
        )
    }
    override fun onDetach(view: View) {
        super.onDetach(view)
        mSubscriptions.clear()
    }
    //---------------------------------------------------------------
    //endregion

    override fun handleBack(): Boolean {
        if (mPopup?.isVisible == true) {
            mPopup?.show(false)
            return true
        }
        return super.handleBack()
    }

    //region UI Events
    //---------------------------------------------------------------
    private val onBackClick = View.OnClickListener { _ ->
        if (mPopup?.isVisible == true) {
            mPopup?.show(false)
        } else {
            popController()
        }
    }
    private val onPopupClick = View.OnClickListener { _ ->
        mPopup?.show(true)
    }
    private val onPopupClose = fun (_: DummyPopup) {
        mPopup?.show(false)
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private val mapTitle = Function<ViewStore.State, String> { state ->
        state.title
    }
    private val consumeTitle = Consumer<String> { _ ->
        // mTitle?.text = mapped
    }
    //---------------------------------------------------------------
    //endregion
}
