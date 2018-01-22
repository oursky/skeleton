package com.oursky.skeleton.helper;

import android.support.annotation.Nullable;

@SuppressWarnings({"WeakerAccess", "unused"})
public class StringHelper {
    public static boolean equals(@Nullable String s1, @Nullable String s2) {
        if (s1 == null) {
            return s2==null;
        } else {
            return s2!=null && s1.equals(s2);
        }
    }
}
