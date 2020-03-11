package com.bael.pin

import android.annotation.SuppressLint
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import com.bael.pin.GsonConverter.deserialize
import com.bael.pin.GsonConverter.serialize
import com.bael.pin.Pin.Companion.context
import com.bael.pin.Pin.Companion.fileName

/**
 * Created by ericksumargo on 10/03/20.
 */
@SuppressLint("CommitPrefEdits")
object PinPreferences {

    private val preferences by lazy {
        val masterKey = MasterKeys.getOrCreate(AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            fileName,
            masterKey,
            context,
            AES256_SIV,
            AES256_GCM
        )
    }
    private val editor by lazy { preferences.edit() }

    fun <T> read(key: String, defaultValue: T?, type: Class<out Any>): T? {
        if (!preferences.contains(key)) return defaultValue

        val serialized = serialize(defaultValue)
        return deserialize(preferences.getString(key, serialized).orEmpty(), type) as T?
    }

    fun <T> write(key: String, value: T?) {
        editor.putString(key, serialize(value))
        editor.commit()
    }
}

