package com.oursky.skeleton.model

import org.json.JSONException
import org.json.JSONObject

data class MyLoginSession(
        val id: Int = 0,
        val name: String = ""
) {
    companion object {
        @Throws(JSONException::class, NullPointerException::class)
        fun from(json: JSONObject): MyLoginSession {
            return MyLoginSession(
                    id = json.getInt("id"),
                    name = json.getString("name")
            )
        }
    }
}
