package com.jadynai.kotlindiary.pdf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_pdf_web.*

class PdfWebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_web)
        val pdfPath = "file:///android_asset/designMode.pdf"
//        val pdfPath = "https://www.gjtool.cn/pdfh5/git.pdf"
        val webPdf = findViewById<WebView>(R.id.web_pdf)
        val settings: WebSettings? = webPdf?.settings
        settings?.pluginState = WebSettings.PluginState.ON
        settings?.javaScriptEnabled = true
        settings?.allowFileAccess = true
        settings?.allowFileAccessFromFileURLs = true
        settings?.allowUniversalAccessFromFileURLs = true
//
//        settings?.builtInZoomControls = true
//        settings?.setSupportZoom(true)
//        settings?.displayZoomControls = false

        webPdf.loadUrl("file:///android_asset/pdfweb/web/viewer.html?file=$pdfPath")
//        webPdf.loadUrl("file:///android_asset/index.html?$pdfPath")
    }
}