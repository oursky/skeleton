package com.oursky.skeleton.client

import com.oursky.skeleton.model.MyLoginSession

class Login {
    data class Input(
        val name: String,
        val pass: String
    )
    data class Output(
        var result: Result = Result.Success,
        var me: MyLoginSession? = null
    ) {
        enum class Result { Success, IncorrectPassword, Suspended }
    }
}
