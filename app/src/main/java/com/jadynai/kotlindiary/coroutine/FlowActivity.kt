package com.jadynai.kotlindiary.coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.jadynai.kotlindiary.databinding.ActivityFlowBinding
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FlowActivity : AppCompatActivity() {

    private val stateStore = MutableSharedFlow<Int>(replay = 2,
        extraBufferCapacity = 63,
        onBufferOverflow = BufferOverflow.SUSPEND)
        .apply { tryEmit(1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        lifecycleScope.launch {
            stateStore.flowWithLifecycle(lifecycle).collect {
                Log.d("FlowActivity", "onCreate: $it old ${stateStore.replayCache}")
            }
        }
        var ss = 100
        binding.flowAdd.setOnClickListener {
            ss++
            stateStore.tryEmit(ss)
        }
        binding.flowCollect.setOnClickListener {
            lifecycleScope.launch {
                stateStore.flowWithLifecycle(lifecycle).collect {
                    Log.d("FlowActivity", "new collect :$it")
                }
            }
        }
    }
}