package com.bael.pin.type

import com.bael.pin.base.PinPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
internal class PinNonNull<T> constructor(
    private val key: String,
    private val defaultValue: T,
    private val preferences: PinPreferences
) : ReadWriteProperty<Any, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val data = Pair(key, defaultValue)
        val value = preferences.read(data, (property.returnType.classifier as KClass<*>).java)
        return value as T
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val data = Pair(key, value)
        preferences.write(data)
    }
}
