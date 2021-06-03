package com.jadynai.kotlindiary.workmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.WorkManager
import com.jadynai.kotlindiary.R

/**
 *JadynAi since 2021/6/3
 */
class WorkManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_work_manager)
        WorkManager.getInstance(this).cancelAllWorkByTag("")
    }
}