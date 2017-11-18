package com.oursky.skeleton.redux;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class ClientState {
    public @NonNull List<Integer> list;

    public ClientState() {
        list = new ArrayList<>();
    }
    public ClientState(ClientState o) {
        list = o.list;
    }
}
