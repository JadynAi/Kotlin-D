package com.jadynai.kotlindiary

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jadynai.cm.kotlintest.R
import com.jadynai.kotlindiary.function.ui.event
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_tv.event(click = { startActivity(Intent(this, RecyclerViewActivity::class.java)) })
    }
}








