package com.jadyn.kotlinp.unittest

import java.util.*

/**
 *JadynAi since 2021/7/25
 */
class LocalTest_1 {

    fun convertString(s: String?): String {
        if (s.isNullOrBlank()) {
            return "Nau"
        }
        return Arrays.toString(s.split(",").toTypedArray())
    }
}