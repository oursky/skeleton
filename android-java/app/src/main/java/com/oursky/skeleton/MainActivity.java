package com.oursky.skeleton;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.support.v7.app.AppCompatActivity;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.facebook.drawee.backends.pipeline.Fresco;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import com.oursky.skeleton.design.Font;
import com.oursky.skeleton.redux.ViewAction;
import com.oursky.skeleton.ui.MainScreen;
import com.oursky.skeleton.ui.SplashScreen;

import static com.oursky.skeleton.MainApplication.store;

public class MainActivity extends AppCompatActivity {
    private boolean mStoreRetained = false;
    private Router mRouter;
    private boolean mInSplash;

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(Font.BARLOW_THIN)
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Fresco.initialize(this);
        if (!mStoreRetained) {
            mStoreRetained = true;
            MainApplication.retainStore(this);
        }
        FrameLayout layout = new FrameLayout(this);
        setContentView(layout);
        mRouter = Conductor.attachRouter(this, layout, savedInstanceState);
        mRouter.setRoot(RouterTransaction.with(new SplashScreen()));
        mInSplash = true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (!mStoreRetained) {
            mStoreRetained = true;
            MainApplication.retainStore(this);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Show main screen after a delay, can also do things like login to server
        getWindow().getDecorView().postDelayed(() -> showAppContent(), 3000);
    }
    @Override
    protected void onPause() {
        super.onPause();
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
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
            mRouter.replaceTopController(RouterTransaction.with(new MainScreen()));
            ViewAction.setTitle(store(), "Hello World");
        }
    }
}
