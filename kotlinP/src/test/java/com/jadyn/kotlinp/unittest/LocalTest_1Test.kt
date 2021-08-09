package com.jadyn.kotlinp.unittest

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * JadynAi since 2021/7/25
 */
@RunWith(AndroidJUnit4::class)
class LocalTest_1Test {

    @Test
    fun convertString() {
        val convertString = LocalTest_1().convertString(",1,223,233,哈哈")
        assertThat(convertString)
            .endsWith("]")
    }

    val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun testContext() {
        context.packageName
    }

    @Test
    fun testStringAlgorithm() {
        val i = 'A'
        println("${i.toInt()}")
    }
}