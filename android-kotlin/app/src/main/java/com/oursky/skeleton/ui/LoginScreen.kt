package com.oursky.skeleton.ui

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

import com.oursky.skeleton.R
import com.oursky.skeleton.helper.LP
import com.oursky.skeleton.helper.ResourceHelper.color
import com.oursky.skeleton.helper.ResourceHelper.dp
import com.oursky.skeleton.helper.ResourceHelper.font
import com.oursky.skeleton.ui.base.BaseController
import com.oursky.skeleton.widget.Button

import com.oursky.skeleton.MainApplication.Companion.store
import com.oursky.skeleton.client.Login
import com.oursky.skeleton.helper.KeyboardHelper
import com.oursky.skeleton.redux.ClientStore
import com.oursky.skeleton.widget.ActionBar

class LoginScreen : BaseController() {
    private val mSubscriptions = CompositeDisposable()
    private var mActionBar: ActionBar? = null
    private var mEamil: EditText? = null
    private var mPass: EditText? = null
    private var mSubmit: Button? = null

    //region Lifecycle
    //---------------------------------------------------------------
    override fun onCreateView(context: Context): View {
        val contentView = LinearLayout(context)
        contentView.orientation = LinearLayout.VERTICAL

        mActionBar = ActionBar(context)
        mActionBar?.setTitle(R.string.login_title)
        contentView.addView(mActionBar, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())

        mEamil = EditText(context)
        mEamil?.setTextSize(20f)
        mEamil?.setTypeface(font(R.font.barlow_condensed_regular))
        mEamil?.setHint(R.string.login_name_hint)
        contentView.addView(mEamil, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())

        mPass = EditText(context)
        mPass?.setTextSize(20f)
        mPass?.setTypeface(font(R.font.barlow_condensed_regular))
        mPass?.setHint(R.string.login_pass_hint)
        contentView.addView(mPass, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT).build())

        mSubmit = Button(context)
        mSubmit?.setTextPadding(dp(32), dp(12), dp(32), dp(12))
        mSubmit?.setBackgroundColor(color(R.color.main_next_bg))
        mSubmit?.setTextColor(color(R.color.main_next_text))
        mSubmit?.setTypeface(font(R.font.barlow_condensed_bold))
        mSubmit?.setTextSize(24f)
        mSubmit?.setText(R.string.login_submit)
        contentView.addView(mSubmit, LP.linear(LP.MATCH_PARENT, LP.WRAP_CONTENT)
                .setMargins(dp(16), dp(16), dp(16), dp(16))
                .build())
        // Register event listener
        mSubmit?.setOnClickListener(onSubmitClick)
        return contentView
    }
    override fun onAttach(view: View) {
        super.onAttach(view)
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
    private val onSubmitClick = View.OnClickListener { _: View ->
        KeyboardHelper.hide(activity)
        val email = mEamil?.text.toString()
        val pass = mPass?.text.toString()
        val input = Login.Input(email, pass)
        if (validateForm(input)) {
            ClientStore.login(store(), input)
        } else {
            Toast.makeText(applicationContext, R.string.login_formerr, Toast.LENGTH_SHORT).show()
        }
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    private val mapLoginState = Function<ClientStore.State, ClientStore.APIState<Login.Output>> { state ->
        state.login
    }
    private val consumeLoginState = Consumer<ClientStore.APIState<Login.Output>> { mapped ->
        val enableUI = !mapped.inprogress
        mSubmit?.isEnabled = enableUI
        mEamil?.isEnabled = enableUI
        mPass?.isEnabled = enableUI
    }
    //---------------------------------------------------------------
    //endregion

    //region Form Validation
    private fun validateForm(input: Login.Input): Boolean {
        if (input.email.isEmpty()) return false
        if (input.pass.isEmpty()) return false
        return true
    }
    //endregion
}
