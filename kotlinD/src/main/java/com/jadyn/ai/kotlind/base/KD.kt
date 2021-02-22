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
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-09-19
 *@ChangeList:
 */
class KD {

    companion object {
        private lateinit var application: Application

        fun init(a: Application) {
            if (!::application.isInitialized) {
                application = a
                initUI(a)
            }
        }

        fun applicationWrapper() = application

        private fun initUI(app: Application) {
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
                    return activity.onCreateView(parent, name, context, attrs)
                }

                override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
                    Log.d("KD", "onCreateView: name string")
                    return activity.onCreateView(name, context, attrs)
                }
            })
        }
    }
} 