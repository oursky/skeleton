package com.oursky.skeleton.ui

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
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
import com.oursky.skeleton.client.Login
import com.oursky.skeleton.redux.ClientStore
import com.oursky.skeleton.redux.ViewStore
import com.oursky.skeleton.ui.base.BaseController
import com.oursky.skeleton.widget.ActionBar
import com.oursky.skeleton.widget.Button
import com.oursky.skeleton.MainApplication.Companion.store

class MainScreen : BaseController() {
    private val mSubscriptions = CompositeDisposable()
    private var mActionBar: ActionBar? = null
    private var mMessage: TextView? = null

    //region Lifecycle
    //---------------------------------------------------------------
    override fun onCreateView(context: Context): View {
        val contentView = LinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        mActionBar = ActionBar(context)
        mActionBar?.setTitle(R.string.login_title)
        contentView.addView(mActionBar, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())

        mMessage = EditText(context)
        mMessage?.textSize = 20f
        mMessage?.typeface = font(R.font.barlow_condensed_regular)
        mMessage?.setHint(R.string.login_name_hint)
        contentView.addView(mMessage, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())

        val next = Button(context)
        next.setTextPadding(dp(32), dp(12), dp(32), dp(12))
        next.setBackgroundColor(color(R.color.main_next_bg))
        next.setTextColor(color(R.color.main_next_text))
        next.setTypeface(font(R.font.barlow_condensed_bold))
        next.setTextSize(24f)
        next.setText(R.string.main_next)
        contentView.addView(next, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT)
                                    .setMargins(dp(16), dp(16), dp(16), dp(16))
                                    .build())
        // Register event listener
        next.setOnClickListener(onNextClick)
        return contentView
    }
    override fun onAttach(view: View) {
        super.onAttach(view)
        mSubscriptions.add(store().observe(store().view)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapTitle)
                .subscribe(consumeTitle)
        )
        mSubscriptions.add(store().observe(store().client)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapLoginState)
                .subscribe(consumeLoginState)
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
    private val onNextClick = View.OnClickListener { _: View ->
        pushController(SecondScreen())
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private val mapTitle = Function<ViewStore.State, String> { state ->
        state.title
    }
    private val consumeTitle = Consumer<String> { mapped ->
        mActionBar?.setTitle(mapped)
    }
    private val mapLoginState = Function<ClientStore.State, ClientStore.APIState<Login.Output>> { state ->
        state.login
    }
    private val consumeLoginState = Consumer<ClientStore.APIState<Login.Output>> { mapped ->
        mapped.data?.me?.let {
            mMessage?.text = resources?.getString(R.string.main_message, it.name)
        }
    }
    //---------------------------------------------------------------
    //endregion
}
