package com.oursky.skeleton.helper;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

@SuppressWarnings({"WeakerAccess", "unused"})
public class KeyboardHelper {
    public static void showAndFocus(final Activity activity, View view) {
        // Uncomment below if you want to skip with hardware keyboard
        // if (activity.getResources().getConfiguration().keyboard == Configuration.KEYBOARD_QWERTY) return;

        view.requestFocus();
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                View focused = activity.getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm!=null) imm.showSoftInput(focused, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
        // NOTE: We add a delay here to resolve a race condition which transit from A screen to B screen,
        // where A's onDetach() dismiss keyboard and B's onAttach() want to show keyboard
        // Since we cannot assume the lifecycle managed by Conductor, we add a delay here to make sure A is gone and the keyboard will be shown.
    }
    public static void hide(final Activity activity) {
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = activity.getWindow().getDecorView();
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm!=null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void setAdjustMode(Activity activity, int mode) {
        activity.getWindow().setSoftInputMode(mode);
    }
}
