package com.oursky.skeleton.client

import android.os.Handler
import com.oursky.skeleton.AppConfig
import com.oursky.skeleton.helper.Logger
import com.oursky.skeleton.iface.SerializableToJson
import java.io.IOException
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.MediaType
import org.json.JSONException
import org.json.JSONObject

@Suppress("unused", "PrivatePropertyName", "UNUSED_PARAMETER", "MemberVisibilityCanBePrivate")
class WebClient {
    private val TAG = "WebClient"

    //region Singleton
    companion object {
        private val sClient = WebClient()
        fun client(): WebClient {
            return sClient
        }
    }
    //endregion

    //region Client State
    private val mHttpClient = OkHttpClient()
    private var mAuthToken = ""
    //endregion

    //region Callback Status
    enum class Result {
        Success, ServerUnreachable, PayloadError
    }
    //endregion

    //region Mock Up Facility
    private val MOCK_CALLBACK_DELAY = 3000L
    private val mMockHandler = Handler()
    //endregion

    //region ApiBuilder
    class ApiBuilder internal constructor(url: String) {
        private enum class RequestType { GET, POST, PUT, DELETE }
        private val JSON = MediaType.parse("application/json; charset=utf-8")
        private val mUrl = url
        private var mType = RequestType.GET
        private var mBody: RequestBody? = null
        private var mToken: String? = null
        fun post(body: RequestBody): ApiBuilder {
            mType = RequestType.POST
            mBody = body
            return this
        }
        fun put(body: RequestBody): ApiBuilder {
            mType = RequestType.PUT
            mBody = body
            return this
        }
        fun delete(body: RequestBody?): ApiBuilder {
            mType = RequestType.DELETE
            mBody = body
            return this
        }
        fun post(data: SerializableToJson): ApiBuilder {
            try {
                post(RequestBody.create(JSON, data.toJson()))
            } catch (e: JSONException) {
                Logger.e("WebClient", "Json Exception on input: ${e.message}")
            }
            return this
        }
        fun put(data: SerializableToJson): ApiBuilder {
            try {
                post(RequestBody.create(JSON, data.toJson()))
            } catch (e: JSONException) {
                Logger.e("WebClient", "Json Exception on input: ${e.message}")
            }
            return this
        }
        fun delete(data: SerializableToJson): ApiBuilder {
            try {
                delete(RequestBody.create(JSON, data.toJson()))
            } catch (e: JSONException) {
                Logger.e("WebClient", "Json Exception on input: ${e.message}")
            }
            return this
        }
        fun token(token: String): ApiBuilder {
            mToken = token
            return this
        }
        fun execute(http: OkHttpClient, cb: ((Result, Boolean, String?) -> Unit)?) {
            val req = Request.Builder().url(mUrl)
            when (mType) {
                RequestType.GET -> { }
                RequestType.POST -> { mBody?.let { req.post(it) } }
                RequestType.PUT -> { mBody?.let { req.put(it) } }
                RequestType.DELETE -> { req.delete(mBody) }
            }
            // Insert token if needed
            mToken?.let { req.addHeader("Authorization", "Bearer " + it) }
            http.newCall(req.build()).enqueue(object : okhttp3.Callback {
                override fun onFailure(call: Call, e: IOException) {
                    cb?.invoke(Result.ServerUnreachable, false, null)
                }
                @Throws(IOException::class)
                override fun onResponse(call: Call, response: Response) {
                    cb?.invoke(Result.Success, response.isSuccessful, response.body()?.string())
                }
            })
        }
    }
    //endregion

    //region Auth Functions
    fun login(input: Login.Input, cb: (Result, Login.Output?) -> Unit) {
//        mMockHandler.postDelayed({
//            mAuthToken = "1234"
//            val output = Login.Output(
//                    result = Login.Output.Result.Success,
//                    me = MyLoginSession(1, "My Name")
//            )
//            cb.invoke(Result.Success, output)
//        }, MOCK_CALLBACK_DELAY)
        ApiBuilder(AppConfig.SERVER_BASE + "login")
//                .post(FormBody.Builder()
//                        .add("email", input.email)
//                        .add("password", input.pass)
//                        .build())
                .post(input)
                .execute(mHttpClient, { result, isHttpSuccess, body ->
                    var r = result
                    val output = try {
                        if (result == Result.Success && isHttpSuccess) {
                            Login.Output.from(JSONObject(body))
                        } else null
                    } catch (e: Exception) {
                        Logger.e(TAG, "login: $result, $isHttpSuccess, $body - ${e.message}")
                        r = Result.PayloadError
                        null
                    }
                    Logger.d(TAG, "login: ${output?.result} - $result, $isHttpSuccess, $body")
                    cb.invoke(r, output)
                })
    }
    fun logout(cb: (Result) -> Unit) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed({
            mAuthToken = ""
            cb.invoke(Result.Success)
        }, MOCK_CALLBACK_DELAY)
    }

    fun register(cb: (Result) -> Unit) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed({
            cb.invoke(Result.ServerUnreachable)
        }, MOCK_CALLBACK_DELAY)
    }
    //endregion
}
