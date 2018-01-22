package com.oursky.skeleton.client

import android.os.Handler
import com.oursky.skeleton.model.MyLoginSession

@Suppress("unused", "PrivatePropertyName", "UNUSED_PARAMETER")
class WebClient {
    private val MOCK_CALLBACK_DELAY = 3000L

    //region Singleton
    companion object {
        private val sClient = WebClient()
        fun client(): WebClient {
            return sClient
        }
    }
    //endregion

    //region Client State
    private var mAuthToken = ""
    private val mMockHandler = Handler()
    //endregion

    //region Callback Interface
    enum class Result {
        OK, ServerUnreachable, PayloadError
    }

    //region Auth Functions
    fun login(input: Login.Input, cb: (Result, Login.Output?) -> Unit) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed({
            mAuthToken = "1234"
            val output = Login.Output()
            output.result = Login.Output.Result.Success
            output.me = MyLoginSession("My Name")
            cb.invoke(Result.OK, output)
        }, MOCK_CALLBACK_DELAY)
    }

    fun logout(cb: (Result) -> Unit) {
        //TODO: Replace this with a real HTTP request
        mMockHandler.postDelayed({
            mAuthToken = ""
            cb.invoke(Result.OK)
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
