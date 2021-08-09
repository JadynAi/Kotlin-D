package com.jadyn.kotlinp

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.InstrumentationConnection
import androidx.test.platform.app.InstrumentationRegistry
import com.jadyn.kotlinp.unittest.GalleryLoader
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.Matcher
import org.hamcrest.MatcherAssert
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

/**
 *JadynAi since 2021/7/19
 */
@RunWith(AndroidJUnit4::class)
class GalleryTest {

    @Test
    fun useAppContext() {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
//        val applicationContext = ApplicationProvider.getApplicationContext<Context>()
//        Assert.assertEquals(targetContext.packageName::class.java, String::class.java)
    }
} 