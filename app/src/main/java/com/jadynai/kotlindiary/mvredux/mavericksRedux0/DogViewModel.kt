package com.jadynai.kotlindiary.mvredux.mavericksRedux0

import com.airbnb.mvrx.*
import com.jadynai.kotlindiary.mvredux.mavericksRedux0.data.DogRepository
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

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

    init {
        val subscribe = dogRepository.getDogs().subscribeOn(Schedulers.io())
            .subscribe { dogs ->
                setState {
                    copy(dogs = Success(dogs))
                }
            }
    }
}