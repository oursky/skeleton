package com.oursky.skeleton.client;

import com.oursky.skeleton.model.MyLoginSession;

@SuppressWarnings({"WeakerAccess", "unused"})
public class Login {
    public static class Input {
        public final String name;
        public final String pass;
        public Input(String name, String pass) {
            this.name = name;
            this.pass = pass;
        }
    }
    public static class Output {
        public enum Result { Success, IncorrectPassword, Suspended }
        public Result result;
        public MyLoginSession me;
    }
}
