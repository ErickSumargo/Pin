package com.bael.pin.base

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.bael.pin.gson.GsonConverter.deserialize
import com.bael.pin.gson.GsonConverter.serialize
import kotlinx.coroutines.GlobalScope

/**
 * Created by ericksumargo on 10/03/20.
 */
internal object PinPreferences {

    private lateinit var preferences: SharedPreferences
    private val editor: Editor by lazy { preferences.edit() }

    fun setPreferences(preferences: SharedPreferences) {
        this.preferences = preferences
    }

    fun <T> read(data: Pair<String, T?>, type: Class<out Any>): T? {
        val (key, defaultValue) = data
        if (!preferences.contains(key)) return defaultValue

        val serialized = serialize(defaultValue)
        return deserialize(preferences.getString(key, serialized).orEmpty(), type) as T?
    }

    fun <T> write(data: Pair<String, T?>) {
        val (key, value) = data
        editor.putString(key, serialize(value))
        editor.commit()
    }
}
