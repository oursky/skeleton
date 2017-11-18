package com.oursky.skeleton.helper;

import android.util.Log;
import com.oursky.skeleton.BuildConfig;

public class Logger {
    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) Log.d(tag, msg);
    }
    public static void e(String tag, String msg) {
        // TODO: integrate sentry
        Log.e(tag, msg);
    }
}
