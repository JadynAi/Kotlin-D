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
                KUI.initUI(a)
            }
        }

        fun applicationWrapper() = application

    }
} 