package com.jadynai.kotlindiary.binder

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Messenger

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-07-18
 *@ChangeList:
 */
class MessengerService : Service() {

    private val m by lazy {
        Messenger(MessengerHandle())
    }

    override fun onBind(intent: Intent?): IBinder {
        return m.binder
    }

}

class MessengerHandle : Handler() {

}