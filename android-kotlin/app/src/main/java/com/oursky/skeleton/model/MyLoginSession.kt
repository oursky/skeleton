package com.oursky.skeleton.model

import android.support.annotation.Keep
import com.beust.klaxon.Json

@Keep
data class MyLoginSession(
        @Json(name = "no") val id: Int,
        @Json(name = "name") val name: String
)
