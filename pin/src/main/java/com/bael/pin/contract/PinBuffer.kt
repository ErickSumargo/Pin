package com.bael.pin.contract

/**
 * Created by ericksumargo on 10/03/20.
 */
internal interface PinBuffer<T> {
    /**
     * Store new value set.
     * An output for the consumer.
     */
    var bufferValue: T

    /**
     * A flag indicates if bufferValue is initialized by retrieving through the preferences file.
     */
    var isRead: Boolean
}
