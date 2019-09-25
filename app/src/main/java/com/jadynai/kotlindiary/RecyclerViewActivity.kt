package com.jadynai.kotlindiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
import com.jadyn.ai.kotlind.utils.toastS
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

        val womens = getData()
        val acrobatAdapter = AcrobatAdapter<Girl> {
            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = "Item文本${d.name}"
                }
                onClick { d, pos ->
                    toastS("cece$d")
                }

            }
        }.setData(womens)

        recycler_view.apply {
            layoutManager = GridLayoutManager(this@RecyclerViewActivity, 3)
            adapter = acrobatAdapter
        }

        change_tv.setOnClickListener {
            //            acrobatAdapter.setData(newData)
        }
    }
}
