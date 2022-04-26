package com.jadynai.kotlindiary.pdf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.whenResumed
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_pdf_view.*

class PdfViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)
        pdf_view.fromAsset("designMode.pdf")
            .pageFitPolicy(FitPolicy.WIDTH)
            .load()
    }
}