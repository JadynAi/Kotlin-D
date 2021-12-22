package com.jadynai.kotlindiary.view

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.VectorDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.animation.PathInterpolator
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.jadyn.ai.kotlind.function.ui.getResDrawable
import com.jadynai.kotlindiary.R
import kotlin.properties.Delegates

/**
 *Jairett since 2021/12/3
 */
class LineAnimView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private val path by lazy { Path() }
    private val paint by lazy {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p
    }

    private val openAnim by lazy {
        val d = getResDrawable(R.drawable.animstart) as? AnimatedVectorDrawable
        d
    }
    private val closeAnim by lazy { getResDrawable(R.drawable.animend) as? AnimatedVectorDrawable }
    private val logo by lazy {
        getResDrawable(R.drawable.ic_logo)
    }
    private val close by lazy {
        getResDrawable(R.drawable.ic_logo_close)
    }
    private var curDrawable: AnimatedVectorDrawable? = null

    private var openState by Delegates.observable(false) { property, oldValue, newValue ->
        curDrawable?.stop()
        if (newValue) {
            setImageDrawable(openAnim)
        } else {
            setImageDrawable(closeAnim)
        }
        curDrawable?.stop()
        curDrawable?.start()
    }

    init {
        setImageDrawable(openAnim)
    }

    fun open() {
        openState = true
    }

    fun close() {
        openState = false
    }

    fun toggle() {
        openState = !openState
    }

    override fun setImageDrawable(drawable: Drawable?) {
        super.setImageDrawable(drawable)
        curDrawable = drawable as? AnimatedVectorDrawable
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (openState) {
            close?.draw(canvas)
        } else {
            logo?.draw(canvas)
        }
    }

}