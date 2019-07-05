package com.jadyn.ai.kotlind.base

import android.app.Application

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/30
 *@ChangeList:
 */
class BaseApplication : Application() {
    
    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}