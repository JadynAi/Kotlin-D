package com.jadynai.kotlindiary.view

import android.os.Bundle
import android.view.Choreographer
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadynai.kotlindiary.R
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
    }
}