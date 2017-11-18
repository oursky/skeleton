package com.oursky.skeleton.redux;

import android.support.annotation.NonNull;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.annotations.ActionCreator;

public class ViewAction {
    private static _Actions _ACTIONS = Actions.from(_Actions.class);
    // ---------------------------------------------------------------------------------------------
    public static void setTitle(@NonNull Store<AppState> store, @NonNull String title) {
        store.dispatch(_ACTIONS.setTitle(title));
    }
    // Internal
    // ---------------------------------------------------------------------------------------------
    @ActionCreator
    interface _Actions {
        String SETTITLE = "VIEW_SETTITLE";
        @ActionCreator.Action(SETTITLE) Action setTitle(String title);
    }
}
