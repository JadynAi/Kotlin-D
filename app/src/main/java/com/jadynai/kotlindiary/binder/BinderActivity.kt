package com.jadynai.kotlindiary.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import androidx.appcompat.app.AppCompatActivity
import com.jadynai.cm.kotlintest.R

class BinderActivity : AppCompatActivity() {

    private lateinit var mess: Messenger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binder)

        bindService(Intent(this, MessengerService::class.java)
                , object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                mess = Messenger(service)
                val msg = Message.obtain(null, 521)
                val data = Bundle()
                data.putString("cece", "test binder client")
                msg.data = data
                
                mess.send(msg)
            }
        }, Context.BIND_AUTO_CREATE)
    }
}
