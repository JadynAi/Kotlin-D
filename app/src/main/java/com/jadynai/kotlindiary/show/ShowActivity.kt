package com.jadynai.kotlindiary.show

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.pressSrc
import com.jadynai.cm.kotlintest.R
import kotlinx.android.synthetic.main.activity_show.*

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-07-18
 *@ChangeList:
 */
class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        iv_show.pressSrc(R.drawable.cat, R.drawable.cat)

        IntArray(0)
        
        arrayListOf<Int>().sort()
    }
}