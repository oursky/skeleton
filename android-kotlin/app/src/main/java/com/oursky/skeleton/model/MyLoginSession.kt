package com.oursky.skeleton.model

import org.json.JSONException
import org.json.JSONObject
import com.oursky.skeleton.iface.SerializableToJson

data class MyLoginSession(
        val id: Int = 0,
        val name: String = ""
) : SerializableToJson {
    @Throws(JSONException::class)
    override fun toJson(): JSONObject = JSONObject()
            .put("id", id)
            .put("name", name)

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
