package com.jadynai.kotlindiary.pdf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.annotation.NonNull
import androidx.lifecycle.Observer
import com.jadynai.kotlindiary.R
import com.jadynai.kotlindiary.databinding.ActivityPdfBinding

class PdfActivity : AppCompatActivity() {
    private lateinit var pdfBinding: ActivityPdfBinding

    private val viewModel: PdfRendererBasicViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pdfBinding = ActivityPdfBinding.inflate(layoutInflater)
        setContentView(pdfBinding.root)
        val image: ImageView = pdfBinding.image
        val buttonPrevious: Button = pdfBinding.previous
        val buttonNext: Button = pdfBinding.next

        // Bind data.
        viewModel.pageInfo.observe(this, Observer { (index, count) ->
            pdfBinding.title.text = "${index + 1} count: $count"
        })
        viewModel.pageBitmap.observe(this, Observer { image.setImageBitmap(it) })
        viewModel.previousEnabled.observe(this, Observer {
            buttonPrevious.isEnabled = it
        })
        viewModel.nextEnabled.observe(this, Observer {
            buttonNext.isEnabled = it
        })

        // Bind events.
        buttonPrevious.setOnClickListener { viewModel.showPrevious() }
        buttonNext.setOnClickListener { viewModel.showNext() }
    }
}