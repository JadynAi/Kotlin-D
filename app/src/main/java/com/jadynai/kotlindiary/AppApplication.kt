package com.jadynai.kotlindiary

import android.app.Application
import com.airbnb.mvrx.Mavericks
import com.jadyn.ai.kotlind.base.KD
import com.jadynai.svg.sample.drawables.SVGLoader

/**
 *JadynAi since 2020/12/15
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KD.init(this)
        SVGLoader.load(this)
        Mavericks.initialize(this)
    }
}