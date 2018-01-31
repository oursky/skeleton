package com.oursky.skeleton.iface;

import android.support.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;

public interface SerializableToJson {
    @NonNull JSONObject toJson() throws JSONException;
}
