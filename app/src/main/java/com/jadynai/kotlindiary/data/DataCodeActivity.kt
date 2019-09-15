package com.jadynai.kotlindiary.data

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jadynai.cm.kotlintest.R
import java.util.*

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-08
 *@ChangeList:
 */
class DataCodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_code)

        val stack = Stack<Int>()
        stack.isNotEmpty().apply { }
        val removeAt = arrayListOf("").removeAt(1)

        val arrayList = ArrayList<Int>()
        arrayList.subList(0, 1)
        val intArray = IntArray(2)
        val copyOfRange = Arrays.copyOfRange(intArray, 0, 2)
        if (stack.isEmpty()) {
            val i = stack.size / 2
        }
    }
}