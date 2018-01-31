package com.oursky.skeleton.client

import org.json.JSONException
import org.json.JSONObject
import com.oursky.skeleton.iface.SerializableToJson
import com.oursky.skeleton.model.MyLoginSession

class Login {
    data class Input(
            val email: String,
            val pass: String
    ) : SerializableToJson {
        @Throws(JSONException::class)
        override fun toJson(): JSONObject = JSONObject()
                .put("email", email)
                .put("pass", pass)
    }
    data class Output(
            var result: Result,
            var me: MyLoginSession
    ) {
        enum class Result(val code: Int) {
            Success(0),
            Suspended(1),
            InvalidAccount(2),
            IncorrectPassword(3),
            ;
            companion object {
                fun from(code: Int?): Result? = Result.values().find { it.code == code }
            }
        }
        companion object {
            @Throws(JSONException::class, NullPointerException::class)
            fun from(json: JSONObject): Output {
                return Output(
                        result = Result.from(json.getInt("result"))!!, // Throw NPE if not resolvable
                        me = MyLoginSession.from(json.getJSONObject("user"))
                )
            }
        }
    }
}
