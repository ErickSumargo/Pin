package com.bael.pin.gson

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created by ericksumargo on 10/03/20.
 */
internal object GsonConverter {

    private val gson: Gson by lazy { Gson() }

    fun <T> serialize(value: T): String {
        return gson.toJson(value)
    }

    fun <T> deserialize(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }
}
