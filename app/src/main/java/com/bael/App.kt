package com.bael

import android.app.Application
import com.bael.pin.base.Pin
import com.bael.util.Constant.APP_NAME
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

/**
 * Created by ericksumargo on 10/03/20.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Pin.init(this, APP_NAME)

        SoLoader.init(this, false)
        if (FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(InspectorFlipperPlugin(this@App, DescriptorMapping.withDefaults()))
                addPlugin(SharedPreferencesFlipperPlugin(this@App))
            }.start()
        }
    }
}
