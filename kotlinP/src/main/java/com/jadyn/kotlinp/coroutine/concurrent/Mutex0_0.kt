package com.jadyn.kotlinp.coroutine.concurrent

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

/**
 *JadynAi since 2020/12/10
 */
class Mutex0_0 {

    private val mutex by lazy { Mutex() }

    private var result: String = ""

    suspend fun testAsync(): String {
        Log.d("cece", "testAsync: ${mutex.isLocked}")
        if (mutex.isLocked) {
            return mutex.withLock { result }
        }
        return runRealInner()
    }

    private suspend fun runRealInner() = withContext(Dispatchers.IO) {
        mutex.lock()
        delay(6000)
        mutex.unlock()
        val s = "finall ahhahhahsd"
        result = s
        s
    }
} 