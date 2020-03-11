package com.bael.pin

import android.content.Context
import com.bael.pin.PinPreferences.read
import com.bael.pin.PinPreferences.write
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
@Suppress("UNCHECKED_CAST")
class Pin<T>(val key: String, val defaultValue: T) {

    operator fun provideDelegate(thisRef: Any, property: KProperty<*>): ReadWriteProperty<Any, T> {
        val propertyType = if (property.returnType.isMarkedNullable) {
            NullableProperty()
        } else {
            NotNullProperty()
        }
        return propertyType as ReadWriteProperty<Any, T>
    }

    private inner class NullableProperty : ReadWriteProperty<Any, T?> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            return read(key, defaultValue, (property.returnType.classifier as KClass<*>).java)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) {
            write(key, value)
        }
    }

    private inner class NotNullProperty : ReadWriteProperty<Any, T> {

        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            return read(key, defaultValue, (property.returnType.classifier as KClass<*>).java) as T
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
            write(key, value)
        }
    }

    companion object {
        lateinit var context: Context
        lateinit var fileName: String

        fun init(context: Context, fileName: String) {
            this.context = context
            this.fileName = fileName
        }
    }
}
