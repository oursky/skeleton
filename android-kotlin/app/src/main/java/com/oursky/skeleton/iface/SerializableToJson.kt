package com.oursky.skeleton.iface

import org.json.JSONException
import org.json.JSONObject

interface SerializableToJson {
    @Throws(JSONException::class)
    fun toJson(): JSONObject
}
