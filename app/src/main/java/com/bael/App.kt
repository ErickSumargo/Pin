package com.bael

import android.app.Application
import com.bael.pin.base.Pin
import com.bael.util.Constant.APP_NAME

/**
 * Created by ericksumargo on 10/03/20.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Pin.init(this, APP_NAME)
    }
}
