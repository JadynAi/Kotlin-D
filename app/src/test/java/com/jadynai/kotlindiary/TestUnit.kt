package com.jadynai.kotlindiary

import android.content.Context
import com.google.common.truth.Truth
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

/**
 *JadynAi since 2021/7/16
 */
@RunWith(MockitoJUnitRunner::class)
class TestUnit {

    @Mock
    private lateinit var mockContext: Context

    @Test
    fun test1() {
        `when`(mockContext.getString(R.string.app_name))
            .thenReturn("fake")
    }
}