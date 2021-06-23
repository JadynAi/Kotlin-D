package com.jadynai.kotlindiary.designMode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_design_mode.*
import java.io.IOException
import java.lang.reflect.Proxy

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-16
 *@ChangeList:
 */
class DesignModeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_design_mode)

        proxy_tv.click {
            (Proxy.newProxyInstance(Test::class.java.classLoader, arrayOf(Test::class.java),
                    TestInvocationHandler(TestImpl())) as Test).testGO()
        }
    }

    inline fun test1() {

    }

}