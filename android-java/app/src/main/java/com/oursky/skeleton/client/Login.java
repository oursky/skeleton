package com.oursky.skeleton.client;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oursky.skeleton.model.MyLoginSession;

import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Login {
    public static class Input {
        public final @NonNull String email;
        public final @NonNull String pass;
        public Input(@NonNull String email, @NonNull String pass) {
            this.email = email;
            this.pass = pass;
        }
        public @NonNull String toJson() {
            try {
                return new JSONObject()
                        .put("email", email)
                        .put("pass", pass)
                        .toString(0);
            } catch (JSONException e) {
                return "";
            }
        }
    }
    public static class Output {
        public enum Result {
            Success(0),
            Suspended(1),
            InvalidAccount(2),
            IncorrectPassword(3),;
            public final int code;

            Result(int code) {
                this.code = code;
            }
            public static Result from(int code) {
                for (Result r: Result.values()) {
                    if (r.code == code) return r;
                } return null;
            }
        }
        public @NonNull Result result = Result.InvalidAccount;
        public @Nullable MyLoginSession me;

        public Output(@NonNull Result result, @NonNull MyLoginSession me) {
            this.result = result;
            this.me = me;
        }
        public static @NonNull Output from(@NonNull JSONObject json) throws JSONException, NullPointerException {
            Result result = Result.from(json.getInt("result"));
            if (result == null) throw new NullPointerException();
            return new Output(
                    result,
                    MyLoginSession.from(json.getJSONObject("user"))
            );
        }
    }
}
