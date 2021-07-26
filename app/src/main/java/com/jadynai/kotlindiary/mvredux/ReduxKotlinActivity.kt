package com.jadynai.kotlindiary.mvredux

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_redux.*
import org.reduxkotlin.createStore
import org.reduxkotlin.createThreadSafeStore

/**
 *JadynAi since 2021/6/22
 */
class ReduxKotlinActivity : AppCompatActivity() {

    private val store by lazy {
        createStore(reducer, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redux)
        initReduxKotlin()
        textView7.click { store.dispatch.invoke(Increment()) }
    }

    private fun initReduxKotlin() {
        store.subscribe {
            val state = store.state
            render(state)
        }
    }

    private fun render(state: Int) {
        textView7.text = state.toString()
    }

}