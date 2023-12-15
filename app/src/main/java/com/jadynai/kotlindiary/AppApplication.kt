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
//        DexposedBridge.hookAllMethods(Activity::class.java, "", object : XC_MethodHook() {
//            override fun beforeHookedMethod(param: MethodHookParam?) {
//                super.beforeHookedMethod(param)
//                Log.d("AppApplication", "beforeHookedMethod: ${param?.method?.name ?: "Nau"}")
//            }
//
//            override fun afterHookedMethod(param: MethodHookParam?) {
//                super.afterHookedMethod(param)
//                Log.d("AppApplication", "afterHookedMethod: ")
//            }
//        })
    }
}