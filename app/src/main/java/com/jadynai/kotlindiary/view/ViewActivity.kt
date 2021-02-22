package com.jadynai.kotlindiary.view

import android.graphics.Color
import android.os.Bundle
import android.view.Choreographer
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.start
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.function.ui.roundHeight
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.coroutine.CoroutineActivity
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.activity_view_gaussian_blur_anim.*

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
        setContentView(R.layout.activity_view_gaussian_blur_anim)

        ddddd.click {
        }
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

//        text_one.click { 
//            Choreographer.getInstance().postFrameCallback { 
//            }
//            parent_3.invalidate()
//        }
//        text_res_drawable_tv.setCompoundDrawablesWithIntrinsicBounds(null,
//                ResourceCircleDrawable(30f, Color.YELLOW, R.drawable.adasd), null, null)
        text_res_drawable_tv.roundHeight(Color.RED)
    }
}