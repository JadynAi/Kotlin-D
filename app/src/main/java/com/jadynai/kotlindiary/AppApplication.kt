package com.jadynai.kotlindiary

import android.app.Activity
import android.app.Application
import android.util.Log
import com.airbnb.mvrx.Mavericks
import com.jadyn.ai.kotlind.base.KD
import com.jadynai.svg.sample.drawables.SVGLoader
import de.robv.android.xposed.DexposedBridge
import de.robv.android.xposed.XC_MethodHook

/**
 *JadynAi since 2020/12/15
 */
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        KD.init(this)
        SVGLoader.load(this)
        Mavericks.initialize(this)
        DexposedBridge.hookAllMethods(Activity::class.java, "", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam?) {
                super.beforeHookedMethod(param)
                Log.d("AppApplication", "beforeHookedMethod: ${param?.method?.name ?: "Nau"}")
            }

            override fun afterHookedMethod(param: MethodHookParam?) {
                super.afterHookedMethod(param)
                Log.d("AppApplication", "afterHookedMethod: ")
            }
        })
    }
}