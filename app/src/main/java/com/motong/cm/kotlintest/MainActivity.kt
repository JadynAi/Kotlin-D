package com.motong.cm.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.motong.cm.kotlintest.rdsll.AcrobatAdapter
import com.motong.cm.kotlintest.rdsll.AcrobatItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_test.view.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        val l = ArrayList<Int>()
        for (i in 0 until 500) {
            l.add(i)
        }

        val acrobatAdapter = AcrobatAdapter<Int> {

            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = "ttt: " + d
                }
                isMeetData { d, pos ->
                    pos == 1
                }
            }

            itemDSL {
                resId(R.layout.item_test1)
                showItem { d, pos, view ->
                    view.item_tv.text = "cece: " + d
                }
                isMeetData { d, pos -> pos != 1 }
            }

        }.bindEvent {
            onClick {
                toast("test" + adapterPosition)
            }
        }
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = acrobatAdapter

        acrobatAdapter.setDataWithDiff(l)

        change_tv.setOnClickListener {
            acrobatAdapter.setDataWithDiff(arrayListOf(1, 2, 3, 4, 5, 5, 44, 444, 55))
        }
    }
}

class Test : AcrobatItem<Int> {
    override fun getResId(): Int = R.layout.item_test

    override fun showItem(d: Int?, pos: Int, view: View) {
        view.item_tv.text = "test" + d
    }

    override fun isMeetData(d: Int?, pos: Int): Boolean {
        return pos == 3
    }
}







