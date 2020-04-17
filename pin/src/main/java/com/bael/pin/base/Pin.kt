package com.bael.pin.base

import android.app.Application
import android.content.Context
import com.bael.pin.ext.isNullableType
import com.bael.pin.implementation.PinPropertyEncryptedImpl
import com.bael.pin.implementation.PinPropertyImpl
import com.bael.pin.type.PinNonNull
import com.bael.pin.type.PinNullable
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
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
    init {
        check(key.isNotBlank()) { "key cannot be empty" }
    }

    @Suppress("UNCHECKED_CAST")
    operator fun provideDelegate(
        thisRef: Any,
        property: KProperty<*>
    ): ReadWriteProperty<Any, T> {
        val pinType = pinTypes.getOrPut(key) {
            if (property.isNullableType()) {
                PinNullable(key, defaultValue)
            } else {
                PinNonNull(key, defaultValue)
            }
        }
        return pinType as ReadWriteProperty<Any, T>
    }

    companion object {
        internal lateinit var context: Context
        internal lateinit var fileName: String
        internal var useEncryptedMode: Boolean by Delegates.notNull()

        internal val coroutineContext: CoroutineContext by lazy { IO + Job() }

        internal val pin: PinPreferences by lazy {
            if (useEncryptedMode) PinPreferences(PinPropertyEncryptedImpl, coroutineContext)
            else PinPreferences(PinPropertyImpl, coroutineContext)
        }

        internal val pinTypes: HashMap<String, ReadWriteProperty<Any, *>> by lazy {
            hashMapOf<String, ReadWriteProperty<Any, *>>()
        }

        fun init(context: Application, fileName: String, useEncryptedMode: Boolean = true) {
            this.context = context
            this.fileName = fileName
            this.useEncryptedMode = useEncryptedMode

            check(fileName.isNotBlank()) { "file name cannot be empty" }
        }

        /**
         * if key is set, remove the associated value along with the key.
         * else, clear all data
         */
        fun clear(key: String = "") {
            pinTypes.run {
                if (key.isNotBlank()) remove(key)
                else clear()
            }
            pin.clear(key)
        }
    }
}
