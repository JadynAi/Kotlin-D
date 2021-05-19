package com.jadynai.kotlindiary.designMode

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 *JadynAi since 2021/5/19
 */
interface Test {

    fun testGO()
}

class TestImpl : Test {
    override fun testGO() {
        println("test impl go go go !")
    }

}

class TestInvocationHandler(private val target: Any) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        return method?.invoke(target, *(args ?: emptyArray()))
    }
}

