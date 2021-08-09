package com.jadynai.kotlindiary

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


/**
 *JadynAi since 2021/7/30
 */
@RunWith(RobolectricTestRunner::class)
class RobolectricTest {

    @Test
    fun useContext() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        
    }
}