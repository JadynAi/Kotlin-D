package com.jadynai.kotlindiary.mvredux.mavericksRedux0

import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelConfigFactory
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import com.jadynai.kotlindiary.mvredux.mavericksRedux0.data.DogRepository

/**
 *JadynAi since 2021/7/28
 */
class DogViewModel(
    initState: DogState,
    dogRepository: DogRepository
) : MavericksViewModel<DogState>(initState) {

    companion object : MavericksViewModelFactory<DogViewModel, DogState> {

        override fun create(viewModelContext: ViewModelContext, state: DogState): DogViewModel? {
            return DogViewModel(state, DogRepository)
        }
    }
}