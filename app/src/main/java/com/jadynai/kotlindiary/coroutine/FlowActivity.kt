package com.jadynai.kotlindiary.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jadynai.kotlindiary.databinding.ActivityFlowBinding
import com.jadynai.kotlindiary.thread.A.Companion.test
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FlowActivity : AppCompatActivity() {

    private val stateStore = MutableSharedFlow<Int>(
        replay = 2,
        extraBufferCapacity = 63,
        onBufferOverflow = BufferOverflow.SUSPEND
    )
        .apply { tryEmit(1) }

    private val stateFlow = MutableStateFlow<TestData>(TestData(0))
    private var ss: TestData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            test0()
        }
        binding.flowAdd.setOnClickListener {
        }
        binding.flowCollect.setOnClickListener {
        }
    }

    private suspend fun test0(): List<Int> {
        val test1 = test1()
        val ha = test1.mapNotNull {
            val test2 = try {
                test2(it)
            } catch (e: Exception) {
                Log.d("cece", "test0: $e")
                null
            }
            test2
        }
        Log.d("cece", "test0: $ha")
        return ha
    }

    suspend fun test1() = suspendCoroutine<List<Int>> {
        it.resume(arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))
    }

    suspend fun test2(i: Int) = suspendCoroutine<Int> {
        if (i > 3) {
            it.resumeWithException(Throwable("sdsds"))
        } else {
            it.resume(i * 9)
        }
    }

    suspend fun test3() = suspendCoroutine<List<Int>> {
        arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
}

class TestData(val v: Int) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestData

        if (v != other.v) return false

        return true
    }

    override fun hashCode(): Int {
        return v
    }
}
