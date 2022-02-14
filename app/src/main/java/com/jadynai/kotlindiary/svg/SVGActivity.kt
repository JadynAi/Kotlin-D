package com.jadynai.kotlindiary.svg

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.databinding.ActivitySvgactivityBinding

class SVGActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySvgactivityBinding.inflate(layoutInflater).root)
    }
}