package com.oursky.skeleton.redux;

import com.yheriatovych.reductor.Cancelable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import static com.oursky.skeleton.MainApplication.store;

public class AppStateObservable {
    public static Observable<AppState> create() {
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
}
