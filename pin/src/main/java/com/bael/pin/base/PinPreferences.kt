package com.bael.pin.base

import android.util.Log
import com.bael.pin.contract.PinProperty
import com.bael.pin.gson.GsonConverter.deserialize
import com.bael.pin.gson.GsonConverter.serialize
import com.bael.pin.util.Constant.PIN_NAME

/**
 * Created by ericksumargo on 10/03/20.
 */
internal class PinPreferences(property: PinProperty) : PinProperty by property {

    @Suppress("UNCHECKED_CAST")
    fun <T> read(data: Pair<String, T?>, type: Class<out Any>): T? {
        val (key, defaultValue) = data
        if (!preferences.contains(key) || defaultValue == null) {
            return defaultValue
        } else {
            val value = preferences.run {
                when (defaultValue) {
                    is String -> getString(key, defaultValue)
                    is Int -> getInt(key, defaultValue)
                    is Long -> getLong(key, defaultValue)
                    is Float -> getFloat(key, defaultValue)
                    is Boolean -> getBoolean(key, defaultValue)
                    /**
                     * Complex object
                     */
                    else -> {
                        val serialized = serialize(defaultValue)
                        try {
                            deserialize(
                                preferences.getString(key, serialized).orEmpty(),
                                type
                            ) as T?
                        } catch (e: Exception) {
                            Log.d(PIN_NAME, e.message.orEmpty())
                            defaultValue
                        }
                    }
                }
            }
            return value as T?
        }
    }

    fun <T> write(data: Pair<String, T?>) {
        val (key, value) = data
        editor.run {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                /**
                 * Complex object
                 */
                else -> putString(key, serialize(value))
            }
            commit()
        }
    }
}
