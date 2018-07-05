package com.jadynai.kotlindiary

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.jadynai.cm.kotlintest.R
import com.jadynai.kotlindiary.function.ui.recyclerview.AcrobatAdapter
import com.jadynai.kotlindiary.function.ui.recyclerview.AcrobatItem
import com.jadynai.kotlindiary.function.ui.recyclerview.linear
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.item_test.view.*
import kotlinx.android.synthetic.main.item_test1.view.*

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val data = ArrayList<Int>()
        for (i in 0 until 500) {
            data.add(i)
        }

        recycler_view.linear()

        val acrobatAdapter = AcrobatAdapter<Int> {
            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = "数据Item: " + d
                }
                isMeetData { d, pos -> pos != 1 }
            }

            itemDSL {
                resId(R.layout.item_test1)
                showItem { d, pos, view ->
                    view.item_tv1.text = "另一种样式" + d
                }
                isMeetData { d, pos -> pos == 1 }
            }
        }.setData(data)
        recycler_view.adapter = acrobatAdapter

        change_tv.setOnClickListener {
            acrobatAdapter.setData(arrayListOf(1, 2, 3, 4, 5, 5, 44, 444, 55))
        }
    }
}


class Test : AcrobatItem<Int>() {
    override fun showItem(d: Int, pos: Int, view: View) {
        view.item_tv.text = "共用Item" + d
    }

    override fun getResId(): Int = R.layout.item_test
}