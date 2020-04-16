package com.bael.pin.ext

import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
fun KProperty<*>.isNullableType(): Boolean {
    return returnType.isMarkedNullable
}

fun KProperty<*>.type(): Class<out Any> {
    return (returnType.classifier as KClass<*>).java
}
