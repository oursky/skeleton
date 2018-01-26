package com.oursky.skeleton.model

import com.oursky.skeleton.iface.SerializableToJson
import org.json.JSONException
import org.json.JSONObject

data class MyLoginSession(
        val id: Int = 0,
        val name: String = ""
) : SerializableToJson {
    @Throws(JSONException::class)
    override fun toJson(): String = JSONObject()
            .put("id", id)
            .put("name", name)
            .toString(0)

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
