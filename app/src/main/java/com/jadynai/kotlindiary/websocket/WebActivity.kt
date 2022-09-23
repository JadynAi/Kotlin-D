package com.jadynai.kotlindiary.websocket

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.jadynai.kotlindiary.R

/**
 *Jairett since 2022/8/30
 */
class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val webView = findViewById<WebView>(R.id.web_view)
        webView.loadUrl("https://www.baidu.com")
    }
}