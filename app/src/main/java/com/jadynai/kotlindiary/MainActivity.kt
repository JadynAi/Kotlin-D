package com.jadynai.kotlindiary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jadynai.cm.kotlintest.R
import com.jadynai.kotlindiary.function.img.RoundCorner
import com.jadynai.kotlindiary.function.ui.recyclerview.AcrobatItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_test.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val l = ArrayList<Int>()
//        for (i in 0 until 500) {
//            l.add(i)
//        }
//
//        val acrobatAdapter = AcrobatAdapter<Int> {
//
//            itemDSL {
//                resId(R.layout.item_test)
//                showItem { d, pos, view ->
//                    view.item_tv.text = "ttt: " + d
//                }
//                isMeetData { d, pos ->
//                    pos == 1
//                }
//            }
//
//            itemDSL {
//                resId(R.layout.item_test1)
//                showItem { d, pos, view ->
//                    view.item_tv.text = "cece: " + d
//                }
//                isMeetData { d, pos -> pos != 1 }
//
//                onClick {
//                    toast("test" + it)
//                }
//            }
//
//        }
//        recycler_view.layoutManager = LinearLayoutManager(this)
//        recycler_view.adapter = acrobatAdapter
//
//        acrobatAdapter.setData(l)
//
//        change_tv.setOnClickListener {
//            acrobatAdapter.setData(arrayListOf(1, 2, 3, 4, 5, 5, 44, 444, 55))
//        }


        Glide.with(this).load("http://p15.qhimg.com/bdm/720_444_0/t01b12dfd7f42342197.jpg")
                .apply(RequestOptions.bitmapTransform(RoundCorner(leftTop = 20f)))
                .into(img)
    }
}

class Test : AcrobatItem<Int>() {
    override fun showItem(d: Int, pos: Int, view: View) {
        view.item_tv.text = "test" + d
    }

    override fun getResId(): Int = R.layout.item_test


    override fun isMeetData(d: Int, pos: Int): Boolean {
        return pos == 3
    }
}







