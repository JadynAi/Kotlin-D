package com.jadynai.kotlindiary.mvredux

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel

/**
 *JadynAi since 2021/6/22
 */
class MVReduxViewModel(state: ReState) : MavericksViewModel<ReState>(state) {

    fun test() = suspend { }
}