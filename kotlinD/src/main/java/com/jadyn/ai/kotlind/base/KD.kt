package com.jadyn.ai.kotlind.base

import android.app.Application

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
            }
        }

        fun applicationWrapper() = application
    }
} 