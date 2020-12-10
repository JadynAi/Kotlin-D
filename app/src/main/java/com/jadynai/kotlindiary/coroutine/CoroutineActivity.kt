package com.jadynai.kotlindiary.coroutine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.kotlinp.coroutine.printWithThreadName
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*

/**
 *JadynAi since 2020/10/9
 */
@ExperimentalCoroutinesApi
class CoroutineActivity : AppCompatActivity(), CoroutineScope by TestScope() {

    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        textView2.click {
            lifecycleScope.launch() {
                go(this)
            }
        }
        textView5.click {
            job?.cancel("asdad", Throwable("sdadasd"))
        }
    }

    suspend fun go(coroutineScope: CoroutineScope) {
        printWithThreadName("go start")
        job?.cancelAndJoin()
        printWithThreadName("go end join")
        job = lifecycleScope.launch(CoroutineExceptionHandler { coroutineContext, throwable ->
            printWithThreadName("inner job catch")
        }) {
            val test1 = test1()
            test1.awaitAll()
            printWithThreadName("end")
        }
        job?.invokeOnCompletion {
            printWithThreadName("complete ${it?.javaClass?.simpleName} ${it?.message ?: "NaN"}")
        }
    }

    suspend fun test1() = coroutineScope {
        val d = arrayListOf<Deferred<Int>>()
        for (i in 0..2) {
            val async = async(Dispatchers.IO) {
                printWithThreadName("async run start")
                delay(2000)
                if (i == 2) {
                    throw Exception("nothing on you")
                }
                printWithThreadName("async run end")
                i
            }
            d.add(async)
        }
        d
    }
}