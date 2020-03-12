package com.bael.pin.implementation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.bael.pin.base.Pin.Companion.context
import com.bael.pin.base.Pin.Companion.fileName
import com.bael.pin.contract.PinProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
internal object PinPropertyImpl : PinProperty {

    override val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(fileName, MODE_PRIVATE)
    }
}
