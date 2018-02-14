package com.oursky.skeleton;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.support.v7.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.oursky.skeleton.client.Login;
import com.oursky.skeleton.redux.AppState;
import com.oursky.skeleton.redux.AppStateObservable;
import com.oursky.skeleton.redux.ClientState;
import com.oursky.skeleton.redux.ViewAction;
import com.oursky.skeleton.ui.LoginScreen;
import com.oursky.skeleton.ui.MainScreen;
import com.oursky.skeleton.ui.SplashScreen;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static com.oursky.skeleton.MainApplication.store;

@SuppressWarnings({"ConstantConditions", "Convert2MethodRef"})
public class MainActivity extends AppCompatActivity {
    private boolean mStoreRetained = false;
    private boolean mInSplash;
    private boolean mShowingMain;
    private Router mRouter;
    private CompositeDisposable mSubscriptions = new CompositeDisposable();

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        if (!mStoreRetained) {
            mStoreRetained = true;
            MainApplication.retainStore(this);
        }
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);
        mRouter = Conductor.attachRouter(this, layout, savedInstanceState);
        mInSplash = true;
        mShowingMain = false;
        if (AppConfig.SPLASH_DURATION == 0) {
            showAppContent();
        } else {
            mRouter.setRoot(RouterTransaction.with(new SplashScreen()));
        }
    }
    @Override
    protected void onStart() {
        // NOTE: We need to get store ready before super.onStart(),
        //       otherwise Conductor will re-create our view and cause NPE upon using store
        if (!mStoreRetained) {
            mStoreRetained = true;
            MainApplication.retainStore(this);
        }
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // subscribe to login state, we'll switch between LoginScreen and MainScreen
        mSubscriptions.add(AppStateObservable.create()
                .observeOn(AndroidSchedulers.mainThread())
                .map(mapLoginState)
                .distinctUntilChanged()
                .subscribe(consumeLoginState)
        );
        if (mInSplash) {
            // Show main screen after a delay, can also do things like login to server
            getWindow().getDecorView().postDelayed(() -> showAppContent(), AppConfig.SPLASH_DURATION);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        mSubscriptions.clear();
        // TODO: We going background, release resource here
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mStoreRetained) {
            mStoreRetained = false;
            MainApplication.releaseStore(this);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //---------------------------------------------------------------
    //endregion

    //region UI Events
    //---------------------------------------------------------------
    @Override
    public void onBackPressed() {
        if (!mRouter.handleBack()) {
            super.onBackPressed();
        }
    }
    //---------------------------------------------------------------
    //endregion

    private void showAppContent() {
        if (mInSplash) {
            mInSplash = false;
            Login.Output login = store().getState().client().login.data;
            boolean logined = login != null && login.result == Login.Output.Result.Success;
            mShowingMain = logined;
            mRouter.replaceTopController(RouterTransaction.with(logined ? new MainScreen() : new LoginScreen()));
            ViewAction.setTitle(store(), "Hello World");
        }
    }

    //region Redux
    //---------------------------------------------------------------
    private final Function<AppState,ClientState.APIState<Login.Output>>
            mapLoginState = (state) -> state.client().login;
    private final Consumer<ClientState.APIState<Login.Output>> consumeLoginState = (mapped) -> {
        if (mInSplash) return;  // skip if in splash screen
        Login.Output login = mapped.data;
        boolean logined = login != null && login.result == Login.Output.Result.Success;
        if (mShowingMain != logined) {
            mShowingMain = logined;
            mRouter.replaceTopController(RouterTransaction.with(logined ? new MainScreen() : new LoginScreen()));
        }
    };
    //---------------------------------------------------------------
    //endregion
}
