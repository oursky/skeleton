package com.oursky.skeleton.redux;

import android.support.annotation.NonNull;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.annotations.ActionCreator;

public class ClientAction {
    private static _Actions _ACTIONS = Actions.from(_Actions.class);
    // ---------------------------------------------------------------------------------------------
    public static void addValue(@NonNull Store<AppState> store, int v) {
        store.dispatch(_ACTIONS.addValue(v));
    }
    // Internal
    // ---------------------------------------------------------------------------------------------
    @ActionCreator
    interface _Actions {
        String ADDVALUE = "CLIENT_ADDVALUE";
        @ActionCreator.Action(ADDVALUE) Action addValue(int v);
    }
}
