package com.bael

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bael.pin.base.Pin
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var object1: Int? by Pin("key1", -1)
    private var object2: String by Pin("key2", "", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.text = "$object1"
        button1.setOnClickListener {
            object1 = 1
        }
        button2.text = object2
        button2.setOnClickListener {
            object2 = "Hello World"
        }
    }
}
