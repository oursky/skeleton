package com.oursky.skeleton.helper;

import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Touchable {
    public static void make(@NonNull View view) {
        view.setOnTouchListener((v,ev) -> {
            switch(ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (!v.isEnabled()) break;
                    v.animate().cancel();
                    v.animate().alpha(0.2f).setDuration(150);
                    return true;
                case MotionEvent.ACTION_CANCEL:
                    if (!v.isEnabled()) break;
                    v.animate().cancel();
                    v.animate().alpha(1.0f).setDuration(150);
                    break;
                case MotionEvent.ACTION_UP:
                    if (!v.isEnabled()) break;
                    v.animate().cancel();
                    v.animate().alpha(1.0f).setDuration(150);
                    v.performClick();
                    break;
            } return false;
        });
    }
}
