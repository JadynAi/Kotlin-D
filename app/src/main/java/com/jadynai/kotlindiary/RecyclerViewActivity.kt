package com.jadynai.kotlindiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
import com.jadyn.ai.acrobat.recyclerview.linear
import com.jadyn.ai.kotlind.utils.toastS
import com.jadynai.cm.kotlintest.R
import kotlinx.android.synthetic.main.activity_recycler_view.*

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
        for (i in 10 until 20) {
            data.add(i)
        }

        val newData = ArrayList<Int>()
        for (i in 0 until 20) {
            newData.add(i)
        }

        recycler_view.linear()

        val acrobatAdapter = AcrobatAdapter<Int> {
            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = "Item文本$d"
                }
                onClick { d, pos ->
                    toastS("cece$d")
                }

            }
        }.setData(data)
        recycler_view.adapter = acrobatAdapter

        change_tv.setOnClickListener {
            acrobatAdapter.setData(newData)
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
