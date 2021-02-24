package com.jadyn.ai.kotlind.base

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.LayoutInflaterCompat

/**
 *JadynAi since 2021/2/22
 */
class KUI {

    companion object {
        fun initUI(app: Application) {
            app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
                override fun onActivityPaused(activity: Activity) {}
                override fun onActivityStarted(activity: Activity) {}
                override fun onActivityDestroyed(activity: Activity) {}
                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
                override fun onActivityStopped(activity: Activity) {}
                override fun onActivityResumed(activity: Activity) {}
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    registerKUI(activity, savedInstanceState)
                }
            })
        }

        private fun registerKUI(activity: Activity, savedInstanceState: Bundle?) {
            val forceSetFactory2 = LayoutInflaterCompat::class.java.getDeclaredMethod("forceSetFactory2", LayoutInflater::class.java, LayoutInflater.Factory2::class.java)
            val lf = LayoutInflater.from(activity)
            forceSetFactory2.isAccessible = true
            forceSetFactory2.invoke(null, lf, object : LayoutInflater.Factory2 {
                override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
                    Log.d("KD", "onCreateView: parent view")
                    val view = activity.onCreateView(parent, name, context, attrs)
                    tryBindKUIAttrs(view, attrs)
                    return view
                }

                override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                    Log.d("KD", "onCreateView: name string")
                    val view = activity.onCreateView(name, context, attrs)
                    tryBindKUIAttrs(view, attrs)
                    return view
                }
            })
        }
    }
}