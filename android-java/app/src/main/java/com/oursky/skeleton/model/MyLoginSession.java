package com.oursky.skeleton.model;

import android.support.annotation.NonNull;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MyLoginSession {
    public @NonNull String name;

    public MyLoginSession(@NonNull String name) {
        this.name = name;
    }
    public MyLoginSession(@NonNull MyLoginSession o) {
        name = o.name;
    }
}
