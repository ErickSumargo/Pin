package com.bael.pin.base

import android.util.Log
import com.bael.pin.contract.PinProperty
import com.bael.pin.gson.GsonConverter.deserialize
import com.bael.pin.gson.GsonConverter.serialize
import com.bael.pin.util.Constant.PIN_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by ericksumargo on 10/03/20.
 */
internal class PinPreferences(property: PinProperty) : PinProperty by property, CoroutineScope {

    override val coroutineContext: CoroutineContext get() = IO

    @Suppress("UNCHECKED_CAST")
    fun <T> read(data: Pair<String, T?>, type: Class<out Any>): T? {
        val (key, defaultValue) = data
        if (!preferences.contains(key)) return defaultValue
        else {
            val value = preferences.run {
                try {
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
                            val json = getString(key, serialize(defaultValue))
                            deserialize(json.orEmpty(), type)
                        }
                    }
                } catch (e: Exception) {
                    Log.d(PIN_NAME, "Read: ${e.message.orEmpty()} (key: $key)")
                    defaultValue
                }
            }
            return value as T?
        }
    }

    fun <T> write(data: Pair<String, T?>) {
        launch {
            val (key, value) = data
            editor.run {
                try {
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
                } catch (e: Exception) {
                    Log.d(PIN_NAME, "Write: ${e.message.orEmpty()} (key: $key)")
                }
            }
        }
    }

    fun clear(key: String) {
        launch {
            editor.run {
                try {
                    if (key.isNotBlank()) remove(key)
                    else clear()
                    commit()
                } catch (e: Exception) {
                    Log.d(PIN_NAME, "Clear: ${e.message.orEmpty()} (key: $key)")
                }
            }
        }
    }
}
