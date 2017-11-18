package com.oursky.skeleton.redux;

import android.support.annotation.NonNull;

@SuppressWarnings("WeakerAccess")
public class ViewState {
    public @NonNull String title;

    public ViewState() {
        title = "";
    }
    public ViewState(ViewState o) {
        title = o.title;
    }
}
