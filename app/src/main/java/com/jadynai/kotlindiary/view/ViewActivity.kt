package com.jadynai.kotlindiary.view

import android.graphics.*
import android.os.*
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.FloatValueHolder
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.jadyn.ai.kotlind.function.ui.StrokeGradientLRDrawable
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_view.*
import kotlinx.android.synthetic.main.activity_view_restore_test.*

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
        setContentView(R.layout.activity_view_restore_test)
        val fragment = GroupFragment()
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.view_root_layout, fragment)
        beginTransaction.commit()

        test_fragment.setOnClickListener {
            fragment.childFragmentManager.apply {
                fragments.forEach {
                    beginTransaction().detach(it).commit()
                    beginTransaction().attach(it).commit()
                }
            }
        }

        val sa = SpringAnimation(FloatValueHolder(1f))
//        sa.setStartValue(0f)
        sa.setSpring(SpringForce(1f))
        sa.setStartValue(0f)
        sa.spring.setDampingRatio(0.25f).stiffness = SpringForce.STIFFNESS_LOW
        sa.addUpdateListener { animation, value, velocity ->
            Log.d("cecece", "onCreate: $value")
        }
        sa.start()
//        testRoundRectDrawable()
//        ActivityPathTransitionBinding.inflate(layoutInflater)
//        findViewById<View>(R.id.touch_region).setOnClickListener {
//            Log.w("ViewActivity", "onCreate: click ooooo")
//        }
//        findViewById<View>(R.id.touch_region1).setOnClickListener {
//            Log.w("ViewActivity", "onCreate: click GRAY")
//        }
//        val seekBar = findViewById<View>(R.id.seek_bar) as SeekBar
//        val pathTransitionView = findViewById<PathTransitionRotateView>(R.id.path_view)
//        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
//                val ratio = progress.toFloat() / 100f
//                pathTransitionView.setProgress(ratio)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar) {}
//            override fun onStopTrackingTouch(seekBar: SeekBar) {}
//        })
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
//        var p = 0f
//        view_wwwww.setProgressColor(Color.RED)
////        view_wwwww.setRound(30f)
//        view_wwwww.click {
//            p += 10f
//            view_wwwww.setProgress(p)
//            Thread {
//                view_wwwww.buildDrawingCache()
//                val bitmap = view_wwwww.drawingCache
//                bitmap
//            }.start()
//        }
//        findViewById<LineAnimView>(R.id.vector_anim).apply {
//            setOnClickListener {
//                toggle()
//            }
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
//        text_res_drawable_tv.roundHeight(Color.RED)
    }

    private fun testRoundRectDrawable() {
        text_one.background = StrokeGradientLRDrawable(intArrayOf(Color.RED, Color.BLUE),
            50f, 5f)
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