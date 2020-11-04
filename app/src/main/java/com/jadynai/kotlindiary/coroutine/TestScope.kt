package com.jadynai.kotlindiary.coroutine

import com.jadyn.kotlinp.coroutine.mainExecutors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlin.coroutines.CoroutineContext

/**
 *JadynAi since 2020/10/9
 */
class TestScope : CoroutineScope {

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.Main

    override fun toString(): String {
        return "test scope"
    }
}