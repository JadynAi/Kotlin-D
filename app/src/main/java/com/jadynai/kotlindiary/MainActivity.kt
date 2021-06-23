package com.jadynai.kotlindiary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.base.KD
import com.jadyn.ai.kotlind.function.start
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.function.ui.event
import com.jadynai.kotlindiary.coroutine.CoroutineActivity
import com.jadynai.kotlindiary.designMode.DesignModeActivity
import com.jadynai.kotlindiary.mvredux.MVReduxActivity
import com.jadynai.kotlindiary.show.ShowActivity
import com.jadynai.kotlindiary.thread.ThreadActivity
import com.jadynai.kotlindiary.thread.ThreadJavaActivity
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
        web_socket_tv.click {
            start<WebSocketClientActivity>()
        }
        redux_tv.click {
            start<MVReduxActivity>()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("cece", "onSaveInstanceState: ")
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








