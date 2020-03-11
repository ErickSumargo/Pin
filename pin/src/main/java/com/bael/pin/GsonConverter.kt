package com.bael.pin

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by ericksumargo on 10/03/20.
 */
object GsonConverter {

    private val gson: Gson by lazy { Gson() }

    fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

    fun <T> deserialize(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }
}
