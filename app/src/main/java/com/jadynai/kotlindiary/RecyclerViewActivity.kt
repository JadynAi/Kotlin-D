package com.jadynai.kotlindiary

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import com.jadynai.cm.kotlintest.R
import com.jadynai.kotlindiary.function.ui.recyclerview.AcrobatAdapter
import com.jadynai.kotlindiary.function.ui.recyclerview.linear
import com.jadynai.kotlindiary.utils.toastS
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.item_test.view.*

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
        for (i in 0 until 10) {
            data.add(i)
        }

        var dddd = 0

        recycler_view.linear()

        val acrobatAdapter = AcrobatAdapter<Int> {
            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = "Item文本$d"
                }
                showItemPayload { d, pos, view, payloads ->
                    view.item_progress.progress = dddd
                }

                onClick { d, pos ->
                    toastS("cece$d")
                }

            }
        }.setData(data)
        recycler_view.adapter = acrobatAdapter

        change_tv.setOnClickListener {
            //            acrobatAdapter.setData(arrayListOf(1, 2, 3, 4, 5, 5, 44, 444, 55))

            Thread {
                for (i in 1..1000) {
                    dddd = i
                    SystemClock.sleep(200)
                    this@RecyclerViewActivity.runOnUiThread {
                        acrobatAdapter.notifyItemChanged(1, 2)
                    }
                }
            }.start()
        }
    }
}

//itemDSL {
//    resId(R.layout.item_test1)
//    showItem { d, pos, view ->
//        view.item_tv1.text = "另一种样式" + d
//    }
//    isMeetData { d, pos -> pos == 1 }
//}
