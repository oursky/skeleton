package com.oursky.skeleton;

import android.app.Application;
import android.content.Context;

import com.yheriatovych.reductor.Store;

import com.oursky.skeleton.helper.ResourceHelper;
import com.oursky.skeleton.redux.AppState;
import com.oursky.skeleton.redux.AppStateImpl;
import com.oursky.skeleton.redux.AppStateReducer;
import com.oursky.skeleton.redux.ClientReducer;
import com.oursky.skeleton.redux.ClientState;
import com.oursky.skeleton.redux.ReduxPersistence;
import com.oursky.skeleton.redux.ViewReducer;
import com.oursky.skeleton.redux.ViewState;

public class MainApplication extends Application {
    private static Store<AppState> sStore = null;
    private static int sStoreRefCount = 0;

    //region Lifecycle
    //---------------------------------------------------------------
    @Override
    public void onCreate() {
        super.onCreate();
        ResourceHelper.setContext(getApplicationContext());
    }
    //---------------------------------------------------------------
    //endregion

    //region Redux Store
    //---------------------------------------------------------------
    public static Store<AppState> store() {
        return sStore;
    }
    public static void retainStore(Context context) {
        if (sStoreRefCount == 0) {
            sStore = Store.create(
                    AppStateReducer.builder()
                            .viewReducer(ViewReducer.create())
                            .clientReducer(ClientReducer.create())
                            .build(),
                    new AppStateImpl(
                            new ViewState(),
                            new ClientState())
            );
            ReduxPersistence.load();
        }
        sStoreRefCount ++;
    }
    public static void releaseStore(Context context) {
        sStoreRefCount --;
        if (sStoreRefCount == 0) {
            ReduxPersistence.save();
            sStore = null;
        }
    }
    //---------------------------------------------------------------
    //endregion
}
