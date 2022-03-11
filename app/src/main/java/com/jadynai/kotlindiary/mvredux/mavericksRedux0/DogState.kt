package com.jadynai.kotlindiary.mvredux.mavericksRedux0

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.jadynai.kotlindiary.mvredux.mavericksRedux0.data.Dog

/**
 *JadynAi since 2021/7/28
 */
data class DogState(
    val ss:Int,
    val dogs: Async<List<Dog>> = Uninitialized,
    val loveDogId: Long? = null,
    val adoptionRequest: Async<Dog> = Uninitialized
) : MavericksState {

    val loveDog = dog(loveDogId)

    fun dog(dogId: Long?): Dog? = dogs.invoke()?.firstOrNull { it.id == dogId }
}