package com.oursky.skeleton.redux;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oursky.skeleton.client.Login;
import com.oursky.skeleton.client.WebClient;

@SuppressWarnings({"WeakerAccess", "unused"})
public class ClientState {
    public static class APIState<T> {
        public boolean inprogress;
        public @NonNull WebClient.Result result;
        public @Nullable
        T data;
        public APIState() {
            inprogress = false;
            result = WebClient.Result.OK;
            data = null;
        }
        @Keep
        public APIState(@NonNull APIState<T> o) {
            inprogress = o.inprogress;
            result = o.result;
            data = o.data;
        }
    }

    public @NonNull APIState<Login.Output> login;

    public ClientState() {
        login = new APIState<>();
    }
    public ClientState(ClientState o) {
        login = o.login;
    }
}
