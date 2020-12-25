package com.jadynai.kotlindiary

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.function.start
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.function.ui.event
import com.jadynai.kotlindiary.coroutine.CoroutineActivity
import com.jadynai.kotlindiary.designMode.DesignModeActivity
import com.jadynai.kotlindiary.show.ShowActivity
import com.jadynai.kotlindiary.thread.ThreadActivity
import com.jadynai.kotlindiary.view.ViewActivity
import com.jadynai.kotlindiary.websocket.WebSocketClientActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler_tv.event(click = { startActivity(Intent(this, StickyHeaderActivity::class.java)) })

        show_tv.click {
            start<ShowActivity>()
        }

        thread_tv.click {
            start<ThreadActivity>()
        }

        view_tv.click {
            start<ViewActivity>()
        }

        fun test() {
            View(this)
            test()
        }

        design_mode_tv.click {
            for (i in 1..10) {
                Thread.sleep(1000)
            }
            start<DesignModeActivity>()

            Observable.just("")

            AndroidSchedulers.from(Looper.myLooper())
        }

        coroutine_tv.click {
            start<CoroutineActivity>()
        }
        web_socket_tv.click {
            start<WebSocketClientActivity>()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.d("cece", "main : onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("cece", "main : onStop")
    }
}








