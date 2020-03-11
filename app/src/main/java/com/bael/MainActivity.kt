package com.bael

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bael.pin.Pin
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var object1: Int by Pin("key1", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Pin.init(this, "pin")

        textView.text = "$object1"
        object1 = 5
    }
}
