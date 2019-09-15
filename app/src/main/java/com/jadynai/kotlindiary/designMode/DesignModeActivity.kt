package com.jadynai.kotlindiary.designMode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadynai.cm.kotlintest.R
import kotlinx.android.synthetic.main.activity_design_mode.*

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
            createProxy<FFGo>().test("测试")
        }
    }
}