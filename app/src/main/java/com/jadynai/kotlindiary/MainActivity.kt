package com.jadynai.kotlindiary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.viewModel
import com.jadyn.ai.kotlind.function.start
import com.jadyn.ai.kotlind.function.startActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.function.ui.event
import com.jadynai.kotlindiary.coroutine.CoroutineActivity
import com.jadynai.kotlindiary.coroutine.FlowActivity
import com.jadynai.kotlindiary.data.DataCodeActivity
import com.jadynai.kotlindiary.designMode.DesignModeActivity
import com.jadynai.kotlindiary.mvredux.ReduxKotlinActivity
import com.jadynai.kotlindiary.pdf.PdfViewActivity
import com.jadynai.kotlindiary.show.ShowActivity
import com.jadynai.kotlindiary.svg.SVGActivity
import com.jadynai.kotlindiary.thread.ThreadActivity
import com.jadynai.kotlindiary.thread.ThreadJavaActivity
import com.jadynai.kotlindiary.video.VideoPlayerCompatActivity
import com.jadynai.kotlindiary.view.DIYBezierActivity
import com.jadynai.kotlindiary.view.ViewActivity
import com.jadynai.kotlindiary.websocket.WebActivity
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_tv.event(click = { startActivity(Intent(this, StickyHeaderActivity::class.java)) })


        show_tv.click {
            start<ShowActivity>()
        }

        thread_tv.event(click = {
            start<ThreadActivity>()
        }, doubleTap = {
            start<ThreadJavaActivity>()
        })

        view_tv.click {
            start<ViewActivity>()
        }

        fun test() {
            View(this)
            test()
        }

        design_mode_tv.click {
            start<DesignModeActivity>()
        }

        coroutine_tv.click {
            start<CoroutineActivity>()
        }
        data_code_tv.click {
            start<DataCodeActivity>()
        }
        redux_tv.click {
            start<ReduxKotlinActivity>()
        }
        bezier_tv.click {
            start<DIYBezierActivity>()
        }
        svg_text_tv.click {
            start<SVGActivity>()
        }
        pdf_go.setOnClickListener {
            start<PdfViewActivity>()
        }
        video_go.setOnClickListener {
            start<VideoPlayerCompatActivity>()
        }
        web_go.setOnClickListener {
            start<WebActivity>()
        }
//        testRxJavaInternal()

        testCoroutineTimeout()
    }

    private fun testCoroutineTimeout() {
        val channel = Channel<Int>(4)
        lifecycleScope.launch(Dispatchers.IO) {
            var i = 0
            while (true) {
                delay(1000)
                channel.send(i++)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            val list = withTimeout(30 * 1000) {
                val l = arrayListOf<Int>()
                channel.consumeAsFlow().collect {
                    l.add(it)
                }
                l
            }
            Log.d("cece", "testCoroutineTimeout: $list")
        }
    }

    private fun testRxJavaInternal() {
        val infoArray = arrayOf("1", "2", "3")
        val subscribe = Observable.interval(15, 3, TimeUnit.SECONDS)
            .scan(0, { total: Int, _: Long? -> total + 1 })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { x: Int ->
                if (x < infoArray.size) {
                    val p: String = infoArray[x % infoArray.size]
                    Log.d("cecece", "testRxJavaInternal: $p")
                }
            }
    }

    private fun testRxJavaRetry() {
        thread {
            Log.d("cece", "onCreate start: ${System.currentTimeMillis()} ")
            var s = 0
            var b: Int? = null
            try {
                b = Observable.fromCallable {
                    Log.d("cece", "callable: $s")
                    s++
                    if (s <= 2) {
                        throw Exception("sdsds")
                    }
                    s
                }.retryWhen(object : Function<Observable<Throwable>, ObservableSource<*>> {
                    var ret = 0
                    override fun apply(t: Observable<Throwable>): ObservableSource<*> {
                        return t.flatMap { t ->
                            ret++
                            if (ret >= 4) {
                                Observable.error(t)
                            } else {
                                Observable.timer(2, TimeUnit.SECONDS)
                            }
                        }
                    }
                }).delay(2, TimeUnit.SECONDS)
                    .blockingLast()
            } catch (e: Exception) {

            }
            Log.d("cece", "onCreate: s${b ?: "nan"}")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("cece", "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d("cece", "main : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("cece", "main : onStop")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.d("cece", "onSaveInstanceState: double params")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("cece", "onRestoreInstanceState: ")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        Log.d("cece", "onRestoreInstanceState: double params")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("cece", "onDestroy: ")
    }
}








