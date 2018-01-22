package com.oursky.skeleton.client;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.oursky.skeleton.model.MyLoginSession;

@SuppressWarnings({"WeakerAccess", "unused"})
public class WebClient {
    //region Singleton
    private static WebClient sClient;
    public static WebClient client() {
        if (sClient == null) {
            sClient = new WebClient();
        } return sClient;
    }
    //endregion

    //region Callback Interface
    public enum Result { OK, ServerUnreachable, PayloadError }
    public interface Callback<T> {
        void onComplete(@NonNull Result result, @Nullable T data);
    }
    //endregion

    //region Client State
    private @NonNull String mAuthToken = "";
    //endregion

    //region Mock Facility
    private static final int MOCK_CALLBACK_DELAY = 3000;
    private final Handler mMockHandler = new Handler();

    //region Auth Functions
    public void login(@NonNull Login.Input input, @NonNull Callback<Login.Output> cb) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed(() -> {
            mAuthToken = "1234";
            Login.Output output = new Login.Output();
            output.result = Login.Output.Result.Success;
            output.me = new MyLoginSession("My Name");
            cb.onComplete(Result.OK, output);
        }, MOCK_CALLBACK_DELAY);
    }
    public void logout(@NonNull Callback<Void> cb) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed(() -> {
            mAuthToken = "";
            cb.onComplete(Result.OK, null);
        }, MOCK_CALLBACK_DELAY);
    }
    public void register(@NonNull Callback<Void> cb) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed(() -> {
            cb.onComplete(Result.ServerUnreachable, null);
        }, MOCK_CALLBACK_DELAY);
    }
    //endregion
}
