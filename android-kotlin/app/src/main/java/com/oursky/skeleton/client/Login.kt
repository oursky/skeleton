package com.oursky.skeleton.client

import android.support.annotation.Keep
import com.beust.klaxon.Json
import com.oursky.skeleton.model.MyLoginSession

class Login {
    @Keep
    data class Input(
            @Json(name = "email") val email: String,
            @Json(name = "pass") val pass: String
    )
    @Keep
    data class Output(
            @Json(ignored = true) var result: Result = Result.Success,
            @Json(name = "result") var _result: Int = 0,
            @Json(name = "user") var me: MyLoginSession? = null
    ) {
        enum class Result { Success, InvalidAccount, IncorrectPassword, Suspended }
    }
}
