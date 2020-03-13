package com.bael.pin.util

import com.bael.pin.base.Pin.Companion.context

/**
 * Created by ericksumargo on 10/03/20.
 */
internal object Util {
    val appName: String
        get() = context.applicationInfo.loadLabel(context.packageManager).toString()
}
