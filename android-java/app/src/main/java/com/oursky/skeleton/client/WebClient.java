package com.oursky.skeleton.client;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import com.oursky.skeleton.AppConfig;
import com.oursky.skeleton.helper.Logger;
import com.oursky.skeleton.iface.SerializableToJson;

@SuppressWarnings({"WeakerAccess", "unused", "UnusedReturnValue"})
public class WebClient {
    private static final String TAG = "WebClient";
    //region Singleton
    private static WebClient sClient;
    public static WebClient client() {
        if (sClient == null) {
            sClient = new WebClient();
        } return sClient;
    }
    //endregion

    //region Client State
    private @NonNull OkHttpClient mHttpClient= new OkHttpClient();
    private @NonNull String mAuthToken = "";
    //endregion

    //region Callback Interface
    public enum Result {
        Success, ServerUnreachable, PayloadError
    }
    public interface Callback<T> {
        void onComplete(@NonNull Result result, @Nullable T data);
    }
    //endregion

    //region Mock Facility
    private static final long MOCK_CALLBACK_DELAY = 3000;
    private final Handler mMockHandler = new Handler();

    //region ApiBuilder
    private interface ApiCallback {
        void onComplete(@NonNull Result result, @Nullable Response response, @Nullable String body);
    }
    private static class ApiBuilder {
        private enum RequestType { GET, POST, PUT, DELETE }
        private MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        private @NonNull String mUrl;
        private @NonNull RequestType mType;
        private @Nullable RequestBody mBody;
        private @Nullable String mToken;
        ApiBuilder(@NonNull String url) {
            mType = RequestType.GET;
            mUrl = url;
        }
        ApiBuilder post(@NonNull RequestBody body) {
            mType = RequestType.POST;
            mBody = body;
            return this;
        }
        ApiBuilder put(@NonNull RequestBody body) {
            mType = RequestType.PUT;
            mBody = body;
            return this;
        }
        ApiBuilder delete(@Nullable RequestBody body) {
            mType = RequestType.DELETE;
            mBody = body;
            return this;
        }
        ApiBuilder post(@NonNull SerializableToJson data) {
            try {
                post(RequestBody.create(JSON, data.toJson().toString(0)));
            } catch (JSONException e) {
                Logger.e("WebClient", "Json Exception on input: " + e.getMessage());
            }
            return this;
        }
        ApiBuilder put(@NonNull SerializableToJson data) {
            try {
                put(RequestBody.create(JSON, data.toJson().toString(0)));
            } catch (JSONException e) {
                Logger.e("WebClient", "Json Exception on input: " + e.getMessage());
            }
            return this;
        }
        ApiBuilder delete(@NonNull SerializableToJson data) {
            try {
                delete(RequestBody.create(JSON, data.toJson().toString(0)));
            } catch (JSONException e) {
                Logger.e("WebClient", "Json Exception on input: " + e.getMessage());
            }
            return this;
        }
        void execute(OkHttpClient http, @Nullable ApiCallback cb) {
            Request.Builder req = new Request.Builder().url(mUrl);
            switch (mType) {
                case GET:    break;
                case POST:   if (mBody!=null) req.post(mBody); break;
                case PUT:    if (mBody!=null) req.put(mBody); break;
                case DELETE: req.delete(mBody); break;
            }
            // Insert token if needed
            if (mToken != null) {
                req.addHeader("Authorization", "Bearer " + mToken);
            }
            http.newCall(req.build()).enqueue(new okhttp3.Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    if (cb != null) cb.onComplete(Result.ServerUnreachable, null, null);
                }
                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    final ResponseBody body = response.body();
                    final String bodystr = body!=null ? body.string() : null;
                    if (cb != null) cb.onComplete(Result.Success, response, bodystr);
                }
            });
        }
    }

    //region Auth Functions
    public void login(@NonNull Login.Input input, @NonNull Callback<Login.Output> cb) {
//        mMockHandler.postDelayed(() -> {
//            mAuthToken = "1234";
//            Login.Output output = new Login.Output();
//            output.result = Login.Output.Result.Success;
//            output.me = new MyLoginSession("My Name");
//            cb.onComplete(Result.OK, output);
//        }, MOCK_CALLBACK_DELAY);
        new ApiBuilder(AppConfig.SERVER_BASE + "login")
//                .post(FormBody.Builder()
//                        .add("email", input.email)
//                        .add("password", input.pass)
//                        .build())
                .post(input)
                .execute(mHttpClient, (result, response, body) -> {
                    Login.Output output = null;
                    try {
                        if (result == Result.Success && response!=null && response.isSuccessful()) {
                            output = Login.Output.from(new JSONObject(body));
                        }
                    } catch (Exception e) {
                        Logger.e(TAG, "login: " + result + ", " + body + " - " + e.getMessage());
                        result = Result.PayloadError;
                    }
                    Logger.d(TAG, "login: " + (output!=null?output.result:"failed") + ", " + body);
                    cb.onComplete(result, output);
                });
    }
    public void logout(@NonNull Callback<Void> cb) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed(() -> {
            mAuthToken = "";
            cb.onComplete(Result.Success, null);
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
