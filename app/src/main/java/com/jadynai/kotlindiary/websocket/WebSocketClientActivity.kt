package com.jadynai.kotlindiary.websocket

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.utils.getReal
import com.jadynai.kotlindiary.R
//import com.koushikdutta.async.http.AsyncHttpClient
//import com.koushikdutta.async.http.WebSocket
import kotlinx.android.synthetic.main.activity_websocket.*
import java.lang.Exception

/**
 *JadynAi since 2020/12/25
 */
class WebSocketClientActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_websocket)
        textView6.click {
//            AsyncHttpClient.getDefaultInstance().websocket("http://10.0.57.88:2333/eavesdrop", null
//            ) { ex, webSocket ->
//                if (ex == null) {
//                    webSocket.send("this is from client")
//                    webSocket.setStringCallback {
//                        Log.d("cece", "onCreate: client string callback valid ${it.getReal("NaN")}")
//                    }
//                }
//            }
        }
    }
}