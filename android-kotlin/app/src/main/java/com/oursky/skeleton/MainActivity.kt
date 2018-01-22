package com.oursky.skeleton

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.facebook.drawee.backends.pipeline.Fresco
import com.oursky.skeleton.MainApplication.Companion.store
import com.oursky.skeleton.client.Login
import com.oursky.skeleton.redux.ClientStore
import com.oursky.skeleton.redux.ViewStore
import com.oursky.skeleton.ui.LoginScreen
import com.oursky.skeleton.ui.MainScreen
import com.oursky.skeleton.ui.SplashScreen
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function

class MainActivity : AppCompatActivity() {
    private val mSubscriptions = CompositeDisposable()
    private var mRouter: Router? = null
    private var mStoreRetained: Boolean = false
    private var mInSplash: Boolean = false
    private var mShowingMain: Boolean = false

    //region Lifecycle
    //---------------------------------------------------------------
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        if (!mStoreRetained) {
            mStoreRetained = true
            MainApplication.retainStore(this)
        }
        val layout = FrameLayout(this)
        setContentView(layout)
        mRouter = Conductor.attachRouter(this, layout, savedInstanceState)
        mRouter?.setRoot(RouterTransaction.with(SplashScreen()))
        mInSplash = true
        mShowingMain = false
    }
    override fun onStart() {
        // NOTE: We need to get store ready before super.onStart(),
        //       otherwise Conductor will re-create our view and cause NPE upon using store
        if (!mStoreRetained) {
            mStoreRetained = true
            MainApplication.retainStore(this)
        }
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
        mSubscriptions.add(store().observe(store().client)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapLoginState)
                .subscribe(consumeLoginState)
        )
        if (mInSplash) {
            // Show main screen after a delay, can also do things like login to server
            window.decorView.postDelayed({ showAppContent() }, 3000)
        }
    }
    override fun onPause() {
        super.onPause()
        mSubscriptions.clear()
        // TODO: We going background, release resource here
    }
    override fun onStop() {
        super.onStop()
        if (mStoreRetained) {
            mStoreRetained = false
            MainApplication.releaseStore(this)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
    }
    //---------------------------------------------------------------
    //endregion

    //region UI Events
    //---------------------------------------------------------------
    override fun onBackPressed() {
        if (mRouter?.handleBack() != true) {
            super.onBackPressed()
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
        if (mInSplash) return@Consumer   // skip if in splash screen
        val logined = mapped?.data?.result == Login.Output.Result.Success
        if (mShowingMain != logined) {
            mShowingMain = logined
            mRouter?.replaceTopController(RouterTransaction.with(if (logined) MainScreen() else LoginScreen()))
        }
    }
    //---------------------------------------------------------------
    //endregion

    private fun showAppContent() {
        if (mInSplash) {
            mInSplash = false
            val login = store().client.state.login.data
            val logined = login?.result === Login.Output.Result.Success
            mShowingMain = logined
            mRouter?.replaceTopController(RouterTransaction.with(if (logined) MainScreen() else LoginScreen()))
            store().dispatch(ViewStore.Action.SetTitle("Hello World"))
        }
    }
}
