package com.jadyn.ai.kotlind.function.asyncview

import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import java.util.concurrent.Executors

/**
 *JadynAi since 2021/4/29
 */
object ViewAsyncManager {

    private var componentCallbacks: ComponentCallbacks? = null
    private val asyncExecutors by lazy { Executors.newFixedThreadPool(2) }

    fun asyncPreLoadView(layoutId: Int) {
        tryInitApplicationMonitor()
        asyncExecutors.execute {
            tryForceSetCurThreadMainLooper()
//            val view = LayoutInflater.from(FrameworksApplication.getInstance()).inflate(layoutId, null)
        }
    }

    fun getView(layoutId: Int): View? {
        return null
    }

    fun recycleView(layoutId: Int) {

    }

    private fun tryInitApplicationMonitor() {
        if (componentCallbacks == null) {
            componentCallbacks = object : ComponentCallbacks {
                override fun onLowMemory() {
                }

                override fun onConfigurationChanged(newConfig: Configuration) {
                }
            }
        }
    }
}