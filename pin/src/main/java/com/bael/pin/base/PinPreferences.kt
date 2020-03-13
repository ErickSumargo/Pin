package com.bael.pin.base

import android.content.SharedPreferences.Editor
import android.util.Log
import com.bael.pin.contract.PinProperty
import com.bael.pin.gson.GsonConverter.deserialize
import com.bael.pin.gson.GsonConverter.serialize
import com.bael.pin.util.Util.appName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by ericksumargo on 10/03/20.
 */
internal class PinPreferences(property: PinProperty) : PinProperty by property {

    private val editor: Editor by lazy { preferences.edit() }

    @Suppress("UNCHECKED_CAST")
    fun <T> read(data: Pair<String, T?>, type: Class<out Any>): T? {
        return runBlocking(IO) {
            val (key, defaultValue) = data
            if (!preferences.contains(key) || defaultValue == null) {
                defaultValue
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
                                Log.d(appName, e.message.orEmpty())
                                defaultValue
                            }
                        }
                    }
                }
                value as T?
            }
        }
    }

    fun <T> write(data: Pair<String, T?>) {
        CoroutineScope(IO).launch {
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
}
