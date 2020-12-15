package com.jadynai.kotlindiary

import android.app.Application
import com.jadyn.ai.kotlind.base.KD

/**
 *JadynAi since 2020/12/15
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KD.init(this)
    }
}