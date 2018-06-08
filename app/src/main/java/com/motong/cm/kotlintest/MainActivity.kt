package com.motong.cm.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.motong.cm.kotlintest.adapter.SimpleAdapter
import com.motong.cm.kotlintest.adapter.onClick
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_view.adapter = SimpleAdapter(arrayListOf("ss", "ss", "sss"), R.layout.item_test){
            onClick(itemView){
                
            }
        }
    }
}


