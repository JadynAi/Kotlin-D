package com.jadyn.kotlinp.coroutine.channel

import com.jadyn.kotlinp.coroutine.BaseMainTest
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 *JadynAi since 2021/3/11
 */
fun main() {
    Channel4().run()
}

class Channel4 : BaseMainTest() {

    override fun run() {
        launch { 
            val ticker = ticker(100, 0)
            ticker.receiveAsFlow().collectIndexed { index, value -> 
                print("index $index $value")
            }
        }
    }
}