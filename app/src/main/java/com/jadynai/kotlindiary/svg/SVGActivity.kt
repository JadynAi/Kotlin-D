package com.jadynai.kotlindiary.svg

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.utils.dp
import com.jadynai.kotlindiary.databinding.ActivitySvgactivityBinding
import java.util.concurrent.Executors
import java.util.concurrent.ThreadPoolExecutor

class SVGActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySvgactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //        mFillPaint.setColor(applyAlpha(-14934492, 1.0f));
//        mFillPaint.setColorFilter(filter);
        val shapeDrawable = ShapeDrawable()
        val shape = RectShape()
        shapeDrawable.shape = shape
        val ints = intArrayOf(Color.parseColor("#FFBDBECE"),
            Color.parseColor("#FFD5CFDC"),
            Color.parseColor("#FFC9CDDA"),
            Color.parseColor("#FFB7C3D3"),
            Color.parseColor("#FF94A0B9"),
            Color.parseColor("#FF96A3BD")
        )
        shapeDrawable.shaderFactory = object : ShapeDrawable.ShaderFactory() {
            override fun resize(width: Int, height: Int): Shader {
                return LinearGradient(2.32258f, 2.41128f, width.toFloat(), height.toFloat(),
                    ints, floatArrayOf(0f, 0.140925f, 0.230471f,
                        0.537202f, 0.794276f, 0.991628f), Shader.TileMode.CLAMP)
            }
        }
//        val testSvgDrawable = DashboardStartDrawable()
//        testSvgDrawable.setBounds(0, 0, 131f.dp, 151f.dp)

//        val gradientDrawable = GradientDrawable()
//        gradientDrawable.setColors(ints, floatArrayOf(0f, 0.140925f, 0.230471f,
//            0.537202f, 0.794276f, 0.991628f))
//        gradientDrawable.gradientType = GradientDrawable.LINEAR_GRADIENT
//        gradientDrawable.setSize(131f.dp, 151f.dp)
//        gradientDrawable.setBounds(0, 0, 131f.dp, 151f.dp)
//        gradientDrawable.orientation = GradientDrawable.Orientation.TL_BR
//        testSvgDrawable.progress = 1f
//        binding.svgDrawable.setImageDrawable(testSvgDrawable)
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//                testSvgDrawable.progress = progress / 100f
                binding.svgDrawable.db.progress = progress / 100f
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }
}