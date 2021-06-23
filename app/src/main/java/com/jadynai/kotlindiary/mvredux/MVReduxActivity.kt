package com.jadynai.kotlindiary.mvredux

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.viewModel
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.designMode.Test
import com.jadynai.kotlindiary.mvredux.redux.StateViewModel
import kotlinx.android.synthetic.main.activity_redux.*

/**
 *JadynAi since 2021/6/22
 */
class MVReduxActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_redux)
        getState().go(redux_root)
    }

    private fun getState() = Test1("sdasdasd")
}

interface TT {

    fun go(view: View)
}

sealed class TestSealed : TT {

}

class Test1(private val string: String) : TestSealed() {
    override fun go(view: View) {
        view.findViewById<TextView>(R.id.textView7).setText(string)
    }
}