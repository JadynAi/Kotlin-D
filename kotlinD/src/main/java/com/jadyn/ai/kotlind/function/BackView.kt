package com.jadyn.ai.kotlind.function

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.jadyn.ai.kotlind.function.ui.click

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-09-18
 *@ChangeList:
 */
class BackView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        click {
            if (context is Activity) {
                context.onBackPressed()
            } else if (context is Fragment) {
                context.activity?.onBackPressed()
            }
        }
    }
}