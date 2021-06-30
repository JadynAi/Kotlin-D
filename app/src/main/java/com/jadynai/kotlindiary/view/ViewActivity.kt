package com.jadynai.kotlindiary.view

import android.graphics.Color
import android.os.*
import android.util.Log
import android.util.SparseArray
import android.view.Choreographer
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.start
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.function.ui.filterViewIsInstanceOnce
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

    private val testNoParentView by lazy { ViewP(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MeasureFlow", "onCreate: ")
        setContentView(R.layout.activity_view_gaussian_blur_anim)
//        ddddd.click {
//            val sparseArray = SparseArray<String>(7)
//            val s = arrayListOf(1, 3, 5, 7, 2, 10, 11, 20, 15, 9)
//            s.shuffle()
//            print("shuffle s $s")
//            s.forEach {
//                sparseArray.put(it, "sss$it")
//            }
//            s
//        }
        var p = 0f
        view_wwwww.setProgressColor(Color.RED)
//        view_wwwww.setRound(30f)
        view_wwwww.click {
            p += 10f
            view_wwwww.setProgress(p)
        }
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
//        text_res_drawable_tv.roundHeight(Color.RED)
    }

    override fun onStart() {
        super.onStart()
        Log.d("MeasureFlow", "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("MeasureFlow", "onResume: ")
    }
}