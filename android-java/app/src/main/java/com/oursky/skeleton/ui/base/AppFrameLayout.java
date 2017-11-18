package com.oursky.skeleton.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.yheriatovych.reductor.Cancelable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import com.oursky.skeleton.redux.AppState;

import static com.oursky.skeleton.MainApplication.store;

public class AppFrameLayout extends FrameLayout {
    // delegate
    protected void onAttach(@NonNull View view) {}
    protected void onDetach(@NonNull View view) {}

    //region Lifecycle
    //---------------------------------------------------------------
    public AppFrameLayout(Context context) {
        super(context);
    }
    public AppFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public AppFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    //---------------------------------------------------------------
    //endregion

    //region Attach / Detach
    //---------------------------------------------------------------
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        onAttach(this);
    }
    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        onDetach(this);
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux
    //---------------------------------------------------------------
    protected Observable<AppState> createAppStateObservable() {
        return Observable.create(new ObservableOnSubscribe<AppState>() {
            private Cancelable mSubscribe;
            @Override
            public void subscribe(final ObservableEmitter<AppState> emitter) throws Exception {
                mSubscribe = store().subscribe((state) -> emitter.onNext(state));
                emitter.setCancellable(() -> {
                    if (mSubscribe!=null) {
                        mSubscribe.cancel();
                        mSubscribe = null;
                    }
                });
                // Sync initial state
                emitter.onNext(store().getState());
            }
        });
    }
    //---------------------------------------------------------------
    //endregion
}
