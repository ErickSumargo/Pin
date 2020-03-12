package com.bael.pin.implementation

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV
import androidx.security.crypto.EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
import androidx.security.crypto.MasterKeys
import androidx.security.crypto.MasterKeys.AES256_GCM_SPEC
import com.bael.pin.base.Pin.Companion.context
import com.bael.pin.base.Pin.Companion.fileName
import com.bael.pin.contract.PinProperty

/**
 * Created by ericksumargo on 10/03/20.
 */
internal object PinPropertyEncryptedImpl : PinProperty {

    override val preferences: SharedPreferences by lazy {
        val masterKey = MasterKeys.getOrCreate(AES256_GCM_SPEC)
        EncryptedSharedPreferences.create(
            fileName,
            masterKey,
            context,
            AES256_SIV,
            AES256_GCM
        )
    }
}
