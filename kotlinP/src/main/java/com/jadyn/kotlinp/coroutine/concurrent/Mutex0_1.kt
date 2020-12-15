package com.jadyn.kotlinp.coroutine.concurrent

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import kotlin.concurrent.thread

/**
 *JadynAi since 2020/12/10
 */
class Mutex0_1 {

    private val mutex by lazy { Mutex() }

    private var result: String = ""

    private val semaphore by lazy { Semaphore(1) }

    suspend fun testAsync(): String {
        if (semaphore.availablePermits == 0) {
            Log.d("cece", "testAsync: acquire")
            semaphore.acquire()
            Log.d("cece", "testAsync: acquire succeed")
            semaphore.release()
            Log.d("cece", "testAsync: release end")
            return result
        }
        return runRealInner()
    }

    private suspend fun runRealInner() = withContext(Dispatchers.IO) {
        Log.d("cece", "runRealInner: start")
        semaphore.acquire()
        Log.d("cece", "runRealInner: acquire succeed")
        delay(3000)
        Log.d("cece", "runRealInner: end")
        semaphore.release()
        Log.d("cece", "runRealInner: release succeed")
        val s = "finall ahhahhahsd"
        result = s
        s
    }
} 