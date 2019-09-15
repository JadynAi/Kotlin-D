package com.jadynai.kotlindiary.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Choreographer
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
import com.jadynai.cm.kotlintest.R
import kotlinx.android.synthetic.main.activity_view.*
import org.jetbrains.anko.toast

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-13
 *@ChangeList:
 */
class ViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)
        

//        view_w.click {
//            Thread {
//                for (i in 0..300 step 20) {
//                    view_w.scrollBy(0, -i)
//                    SystemClock.sleep(100)
//                    Log.d("cece", "view scroll ${view_w.scrollY}")
//                }
//            }.start()
//        }


//        view_w.setOnTouchListener { v, event -> 
//            true
//        }

        text_one.click { 
            Choreographer.getInstance().postFrameCallback { 
                toast("this is $it")
            }
            parent_3.invalidate()
        }

        recycler_1.layoutManager = CuManager()
        val data = arrayListOf<String>()
        for (i in 0..100) {
            data.add(i.toString())
        }
        val data1 = AcrobatAdapter<String> {
            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = d
                }
            }
        }.setData(data)
        recycler_1.adapter = data1

        recycler_1.postDelayed({
            data.add(0, "ffff")
            data1.notifyItemInserted(0)
        }, 3000)
    }
}