package com.bael.pin.type

import com.bael.pin.base.Pin.Companion.pin
import com.bael.pin.contract.PinBuffer
import com.bael.pin.ext.type
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
internal class PinNullable<T> constructor(
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any, T?>, PinBuffer<T?> {

    override var bufferValue: T? = defaultValue
    override var isRead: Boolean = false

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        if (!isRead) {
            bufferValue = readPin(property)
            isRead = true
        }
        return bufferValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
        if (bufferValue != value) {
            bufferValue = value
            writePin(value)
        }
    }

    private fun readPin(property: KProperty<*>): T? {
        val data = Pair(key, defaultValue)
        return pin.read(data, property.type())
    }

    private fun writePin(value: T?) {
        val data = Pair(key, value)
        pin.write(data)
    }
}
