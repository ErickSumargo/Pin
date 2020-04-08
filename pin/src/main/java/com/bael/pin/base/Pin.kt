package com.bael.pin.base

import android.app.Application
import android.content.Context
import com.bael.pin.implementation.PinPropertyEncryptedImpl
import com.bael.pin.implementation.PinPropertyImpl
import com.bael.pin.type.PinNonNull
import com.bael.pin.type.PinNullable
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
class Pin<T> constructor(
    private val key: String,
    private val defaultValue: T
) {
    @Suppress("UNCHECKED_CAST")
    operator fun provideDelegate(
        thisRef: Any,
        property: KProperty<*>
    ): ReadWriteProperty<Any, T> {
        val pinType = if (property.returnType.isMarkedNullable) {
            PinNullable(key, defaultValue, preferences)
        } else {
            PinNonNull(key, defaultValue, preferences)
        }
        return pinType as ReadWriteProperty<Any, T>
    }

    companion object {
        internal lateinit var context: Context
        internal lateinit var fileName: String

        private var useEncryptedMode: Boolean by Delegates.notNull()
        private lateinit var preferences: PinPreferences

        fun init(context: Application, fileName: String, useEncryptedMode: Boolean = true) {
            this.context = context
            this.fileName = fileName
            this.useEncryptedMode = useEncryptedMode

            initPreferences()
        }

        private fun initPreferences() {
            preferences = if (useEncryptedMode) PinPreferences(PinPropertyEncryptedImpl)
            else PinPreferences(PinPropertyImpl)
        }

        /**
         * if key is set, remove the associated value along with the key
         * else clear all data
         */
        fun clear(key: String = "") {
            preferences.editor.run {
                if (key.isNotBlank()) remove(key)
                else clear()
                commit()
            }
        }
    }
}
