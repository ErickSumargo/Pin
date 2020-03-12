package com.bael

import android.app.Application
import com.bael.pin.base.Pin
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.leakcanary.LeakCanaryFlipperPlugin
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin
import com.facebook.soloader.SoLoader

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Pin.init(this, "pin")

        SoLoader.init(this, false)
        if (FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(LeakCanaryFlipperPlugin())
                addPlugin(SharedPreferencesFlipperPlugin(this@App))
            }.start()
        }
    }
}
