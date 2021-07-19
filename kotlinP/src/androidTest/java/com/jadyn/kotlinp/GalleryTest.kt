package com.jadyn.kotlinp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jadyn.kotlinp.unittest.GalleryLoader
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.system.measureTimeMillis

/**
 *JadynAi since 2021/7/19
 */
@RunWith(AndroidJUnit4::class)
class GalleryTest {

    @Test
    fun testLoadData() {
        val galleryLoader = GalleryLoader()
        runBlocking {
            val cost = measureTimeMillis { galleryLoader.loadDCIMCatalog() }
            println("load data cost $cost")
        }
    }
} 