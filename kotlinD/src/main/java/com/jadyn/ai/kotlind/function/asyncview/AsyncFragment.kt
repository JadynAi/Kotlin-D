package com.jadyn.ai.kotlind.function.asyncview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

/**
 *Jairett since 2022/1/24
 */
abstract class AsyncFragment : Fragment() {

    private val asyncLayoutInflater by lazy {
        AsyncLayoutInflater(requireContext())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val frameLayout = FrameLayout(requireContext())
        asyncLayoutInflater.inflate(getLayoutId(), frameLayout) { view, resId, parent ->
            frameLayout.addView(view)
        }
        return frameLayout
    }

    @LayoutRes
    abstract fun getLayoutId(): Int
}