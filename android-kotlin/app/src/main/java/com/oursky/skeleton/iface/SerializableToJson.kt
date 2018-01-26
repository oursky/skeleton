package com.oursky.skeleton.iface

import org.json.JSONException

interface SerializableToJson {
    @Throws(JSONException::class)
    fun toJson(): String
}
