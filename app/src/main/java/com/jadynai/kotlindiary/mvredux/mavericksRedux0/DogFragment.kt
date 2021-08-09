package com.jadynai.kotlindiary.mvredux.mavericksRedux0

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.jadynai.kotlindiary.R

/**
 *JadynAi since 2021/7/28
 */
class DogFragment : Fragment(R.layout.fragment_dog), MavericksView {

    private val dogViewModel: DogViewModel by fragmentViewModel()


    override fun invalidate() {
    }
}