package com.motong.cm.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.motong.cm.kotlintest.rdsll.AcroLayoutItem
import com.motong.cm.kotlintest.rdsll.AcrobatAdapter
import com.motong.cm.kotlintest.rdsll.AcrobatDSL
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_test.view.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recycler_view.layoutManager = LinearLayoutManager(this)

        val acrobatAdapter = AcrobatAdapter<String> {

            itemDSL {
                resId(R.layout.item_test)
                showItem { pos, view ->
                    view.item_tv.text = "头布局"
                }
            }

            itemDSL {
                resId(R.layout.item_test)
                showItem { pos, view ->
                    view.item_tv.text = "头布局"
                }
            }
            itemDSL {
                resId(R.layout.item_test)
                showItem { pos, view ->
                    view.item_tv.text = "头布局"
                }
            }
            itemDSL {
                resId(R.layout.item_test)
                showItem { pos, view ->
                    view.item_tv.text = "头布局"
                }
            }

            itemConfig {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = d
                }

                onViewAttach { pos, view ->
                    view?.item_tv?.setOnClickListener {
                        Toast.makeText(this@MainActivity, "cecee", Toast.LENGTH_LONG).show()
                    }
                }

                isMeetData { d, pos -> pos != 2 }
            }

            itemConfig {
                resId(R.layout.item_test1)
                showItem { d, pos, view ->
                    view.item_tv.text = "test1" + d
                }

                isMeetData { d, pos ->
                    pos == 2
                }
            }
        }

        recycler_view.adapter = acrobatAdapter
        acrobatAdapter.setDataWithDiff(arrayListOf("1", "2", "3", "4"))

        change_tv.setOnClickListener {
            Toast.makeText(this@MainActivity, "Change", Toast.LENGTH_LONG).show()
            acrobatAdapter.setDataWithDiff(arrayListOf("1", "2"))
        }
    }
}

class TestDSL : AcroLayoutItem<String> {
    override fun showItem(pos: Int, view: View) {
        view.item_tv.text = "hahah"
    }

    override fun getResId(): Int {
        return R.layout.item_test
    }
}

class ADsl : AcrobatDSL<String>() {
    init {
        resId = R.layout.item_test
    }

}




