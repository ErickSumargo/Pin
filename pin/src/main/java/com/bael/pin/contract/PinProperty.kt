package com.bael.pin.contract

import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

/**
 * Created by ericksumargo on 10/03/20.
 */
internal interface PinProperty {
    val preferences: SharedPreferences
    val editor: Editor
}
