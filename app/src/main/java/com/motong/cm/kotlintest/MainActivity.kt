package com.motong.cm.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.motong.cm.kotlintest.rdsll.AcrobatAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_test.*
import kotlinx.android.synthetic.main.item_test.view.*
import zlc.season.yaksa.YaksaItem
import zlc.season.yaksa.linear

class MainActivity : AppCompatActivity() {


    val data = arrayListOf(1, 2, 3, 4, 5)

    val d = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.linear {


            data.forEach { each ->

                itemDsl {
                    xml(R.layout.item_test)
                    render {
                        it.item_tv.text = "item DSL" + each
                    }

                }
            }


        }

        AcrobatAdapter<String>(this) {
            
            itemConfig {
                resId(R.layout.item_test)
                showItem { d, pos ->
                    item_tv.text = d
                }
            }
        }

    }
}

class TestDSL : YaksaItem {
    override fun render(position: Int, view: View) {
        view.item_tv.text = "cece"
    }

    override fun xml(): Int {
        return R.layout.item_test
    }

}




