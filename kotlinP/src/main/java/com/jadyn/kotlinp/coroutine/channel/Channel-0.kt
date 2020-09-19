package com.jadyn.kotlinp.coroutine.channel

import com.jadyn.kotlinp.coroutine.BaseMainTest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 *JadynAi since 2020/9/19
 */
fun main() {
    Channel0().run()
}

class Channel0 : BaseMainTest() {

    override fun run() {
        val channel = Channel<Int>(4)
        launch { 
            repeat(10){
                channel.send(it)
                println("sen $it")
                delay(1000)
            }
        }
        launch { 
            repeat(4){
//                println("receive ${channel.receive()}")
            }
        }
    }

}