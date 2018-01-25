package com.oursky.skeleton.model;

import android.support.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public class MyLoginSession {
    public @NonNull String name;

    public MyLoginSession(@NonNull String name) {
        this.name = name;
    }
    public MyLoginSession(@NonNull MyLoginSession o) {
        name = o.name;
    }

    public static @NonNull MyLoginSession from(@NonNull JSONObject json) throws JSONException, NullPointerException {
        return new MyLoginSession(
                json.getString("name")
        );
    }
}
