package com.jadyn.kotlinp.coroutine.flow

import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadyn.kotlinp.coroutine.singleExecutors
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
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
                    delay(3000)
                    printWithThreadName("emit go")
                    emit(it)
                }
            }.flowOn(singleExecutors.asCoroutineDispatcher())
            printWithThreadName("wait start")
//            flow.firstOrNull()
            printWithThreadName("end")
        }
    }
}