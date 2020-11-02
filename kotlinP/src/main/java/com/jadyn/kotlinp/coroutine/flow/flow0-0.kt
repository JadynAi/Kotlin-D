package com.jadyn.kotlinp.coroutine.flow

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadyn.kotlinp.coroutine.singleExecutors
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

/**
 *JadynAi since 2020/11/2
 */
fun main() {
    FlowBaseTest().run()
}

class FlowBaseTest : BaseMainTest() {
    
    override fun run() {
        launch { 
            val flow = flow<Int> {
                repeat(5) {
                    emit(it)
                    printWithThreadName("emit go")
                    delay(500)
                }
            }.flowOn(singleExecutors.asCoroutineDispatcher())
            flow.collect { 
                printWithThreadName("collect $it")
            }
        }
    }
}