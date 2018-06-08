package com.motong.cm.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.motong.cm.kotlintest.adapter.SimpleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.adapter = SimpleAdapter(arrayListOf(1, 2, 3), R.layout.item_test){
            
        }
    }
}


