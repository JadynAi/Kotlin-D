package com.jadyn.kotlinp.unittest

import org.junit.Assert.*
import org.junit.Test
import  com.google.common.truth.Truth.assertThat

/**
 * JadynAi since 2021/7/25
 */
class LocalTest_1Test {

    @Test
    fun convertString() {
        val convertString = LocalTest_1().convertString(",1,223,233,哈哈")
        assertThat(convertString)
            .endsWith("]")
    }

    @Test
    fun testStringAlgorithm() {
        val i = 'A'
        println("${i.toInt()}")
    }
}