package com.jadynai.kotlindiary.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.animation.doOnEnd
import androidx.dynamicanimation.animation.FloatValueHolder
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.jadyn.ai.kotlind.function.ui.RoundGradientDrawable

/**
 *Jairett since 2022/9/2
 */
class RippleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var yValue = 450f
    private val xAnimEngine by lazy {
//        val animator = ValueAnimator.ofFloat(1f, 0.2f).setDuration(1111)
//        animator.addUpdateListener {
//            val v = it.animatedValue as Float
//            yValue = 400f * v
//            invalidate()
//        }
//        animator.doOnEnd { }
//        animator.interpolator = AnticipateOvershootInterpolator(1.2f)
////        animator.interpolator = LinearInterpolator()
//        animator
        val sa = SpringAnimation(FloatValueHolder(20f))
//        sa.setStartValue(0f)
        sa.setSpring(SpringForce(20f))
        sa.setStartValue(100f)
        sa.spring.setDampingRatio(0.2f).stiffness = SpringForce.STIFFNESS_LOW
        sa.addUpdateListener { animation, value, velocity ->
            Log.d("cecece", "onCreate: $value")
            yValue = 450f * (value / 100f)
            invalidate()
        }
        sa.spring
        sa
    }

    private val drawable by lazy {
        val gradientDrawable = RoundGradientDrawable()
        gradientDrawable.shape = GradientDrawable.RECTANGLE
        gradientDrawable.cornerRadius = 10f
        gradientDrawable.setColor(Color.GRAY)
        gradientDrawable
    }

    init {
        setOnClickListener {
            xAnimEngine.cancel()
            xAnimEngine.setStartValue(100f)
            xAnimEngine.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val i = yValue.toInt()
        Log.d("ColumnChatView", "anim : $i")
        drawable.setBounds(100, i, 300, measuredHeight)
        drawable.draw(canvas)
        
    }
}