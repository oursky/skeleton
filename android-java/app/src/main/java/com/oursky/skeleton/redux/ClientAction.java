package com.oursky.skeleton.redux;

import android.support.annotation.NonNull;

import com.yheriatovych.reductor.Action;
import com.yheriatovych.reductor.Actions;
import com.yheriatovych.reductor.Store;
import com.yheriatovych.reductor.annotations.ActionCreator;

import com.oursky.skeleton.client.Login;
import com.oursky.skeleton.client.WebClient;
import static com.oursky.skeleton.client.WebClient.client;

public class ClientAction {
    private static _Actions _ACTIONS = Actions.from(_Actions.class);
    // ---------------------------------------------------------------------------------------------
    public static boolean login(@NonNull Store<AppState> store, @NonNull Login.Input input) {
        if (store.getState().client().login.inprogress) return false;
        store.dispatch(_ACTIONS.loginBegin());
        client().login(input, (result, output) -> {
            store.dispatch(_ACTIONS.loginComplete(result, output));
        });
        return true;
    }
    // Internal
    // ---------------------------------------------------------------------------------------------
    @ActionCreator
    interface _Actions {
        String LOGIN_BEGIN = "CLIENT_LOGIN_BEGIN";
        String LOGIN_COMPLETE  = "CLIENT_LOGIN_COMPLETE";
        @ActionCreator.Action(LOGIN_BEGIN) Action loginBegin();
        @ActionCreator.Action(LOGIN_COMPLETE) Action loginComplete(WebClient.Result result, Login.Output output);
    }
}
