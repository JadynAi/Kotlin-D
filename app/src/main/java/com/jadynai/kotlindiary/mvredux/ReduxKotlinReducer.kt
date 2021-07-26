package com.jadynai.kotlindiary.mvredux

import org.reduxkotlin.Reducer

/**
 *JadynAi since 2021/7/26
 */

class Increment
class Decrement

val reducer: Reducer<Int> = { state, action ->
    when (action) {
        is Increment -> {
            state + 1
        }
        is Decrement -> {
            state - 1
        }
        else -> state
    }
}

