package com.jadyn.kotlinp.coroutine.select

import android.util.ArrayMap
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.jadyn.kotlinp.coroutine.A
import com.jadyn.kotlinp.coroutine.BaseMainTest
import com.jadyn.kotlinp.coroutine.printWithThreadName
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import java.io.File
import java.lang.reflect.Type

/**
 *JadynAi since 2020/12/7
 */
fun main() {
    SelectTest().run()
}

class SelectTest() : BaseMainTest() {

    override fun run() {
        launch {
            val select = select<Int> {
                test1().onAwait { it + 10 }
                test2().onAwait { it + 222 }
            }
            printWithThreadName("select $select")
        }
//        val g = GsonBuilder()
//                .registerTypeAdapter(AAAAAA::class.java, Fuck())
//                .serializeSpecialFloatingPointValues()
//                .create()
//        val b = g.fromJson<AAAAAA>("{\"a\":16,\"b\":true}", AAAAAA::class.java)
//        print(b.c)
    }

    private fun CoroutineScope.test1() = async(Dispatchers.IO) {
        delay(1000)
        1
    }

    private fun CoroutineScope.test2() = async(Dispatchers.IO) {
        delay(3000)
        22
    }

    suspend fun test3() = suspendCancellableCoroutine<Int> { }
}

class AAAAAA constructor(val a: Int, val b: Boolean, val c: Array<Long> = emptyArray())

class Fuck : JsonDeserializer<AAAAAA> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): AAAAAA? {
        val j = json?.asJsonObject ?: return null
        j.get("c") ?: return null
        return GsonBuilder().serializeSpecialFloatingPointValues()
                .create().fromJson(json, AAAAAA::class.java)
    }

}