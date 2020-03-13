package com.bael

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bael.model.Movie
import com.bael.pin.base.Pin
import com.bael.util.Constant.OBJECT1_KEY
import com.bael.util.Constant.OBJECT2_KEY
import com.bael.util.Constant.OBJECT3_KEY
import com.bael.util.Constant.OBJECT4_KEY
import com.bael.util.Constant.OBJECT5_KEY

/**
 * Created by ericksumargo on 10/03/20.
 */
class MainActivity : AppCompatActivity() {

    // 1. Less Verbose
    private var object1: Int by Pin(key = OBJECT1_KEY, defaultValue = -1)

    // 2. Support Complex Object
    private var object2: Movie by Pin(key = OBJECT2_KEY, defaultValue = Movie())

    // 3. Support Nullability
    private var object3: Movie? by Pin(key = OBJECT3_KEY, defaultValue = null)

    // 4. Compile Time Safety
    // private var object4: Movie by Pin(key = OBJECT4_KEY, defaultValue = null)

    // 5. Support The Good Old Days Plain Mode
    private val object5: String by Pin(
        key = OBJECT5_KEY,
        defaultValue = "",
        useEncryptedMode = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Read
         * 1. Read it like a plain variable
         * 2. Seriously, that's all!
         */
        println(object1)
        println(object2)
        println(object3)
        println(object5)

        /**
         * Write
         * 1. Assign it like a plain variable
         * 2. No worries, the IO job runs on Worker thread
         * 3. Seriously, that's all!
         */
        object1 = 1
        object2 = Movie()
        object3 = null
        // object5 = "Hello World" => It's immutable, remember?
    }
}
