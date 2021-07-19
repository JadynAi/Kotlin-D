package com.jadynai.kotlindiary.dcim

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * JadynAi since 2021/7/16
 */
class PartModelTest {

    @Test
    fun loadData() {
        runBlocking {
            val partModel = PartModel()
            partModel.loadData()
        }
    }
}