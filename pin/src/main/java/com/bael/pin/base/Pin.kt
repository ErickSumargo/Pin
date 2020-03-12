package com.bael.pin.base

import android.app.Application
import android.content.Context
import com.bael.pin.implementation.PinPropertyImpl.preferences
import com.bael.pin.type.PinNonNull
import com.bael.pin.type.PinNullable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import com.bael.pin.implementation.PinPropertyEncryptedImpl.preferences as encryptedPreferences

/**
 * Created by ericksumargo on 10/03/20.
 */
class Pin<T> constructor(
    private val key: String,
    private val defaultValue: T,
    private val isEncrypted: Boolean = true
) {
    @Suppress("UNCHECKED_CAST")
    operator fun provideDelegate(
        thisRef: Any,
        property: KProperty<*>
    ): ReadWriteProperty<Any, T> {
        val pinType = if (property.returnType.isMarkedNullable) {
            PinNullable(key, defaultValue, getPreferences())
        } else {
            PinNonNull(key, defaultValue, getPreferences())
        }
        return pinType as ReadWriteProperty<Any, T>
    }

    private fun getPreferences(): PinPreferences {
        return PinPreferences.apply {
            if (isEncrypted) setPreferences(encryptedPreferences)
            else setPreferences(preferences)
        }
    }

    companion object {
        lateinit var context: Context
        lateinit var fileName: String

        fun init(context: Application, fileName: String) {
            this.context = context
            this.fileName = fileName
        }
    }
}
