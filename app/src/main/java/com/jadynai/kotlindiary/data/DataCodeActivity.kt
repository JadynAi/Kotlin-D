package com.jadynai.kotlindiary.data

import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jadynai.kotlindiary.R
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

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
        getAllFiles(File(Environment.getExternalStorageDirectory().absolutePath + "/DCIM/")).forEach {
            Log.d("cece", "onCreate: ${it.name}")
        }
    }

    private fun getAllFiles(file: File): ArrayList<File> {
        val files = ArrayList<File>()
        val stack = LinkedList<File>()
        stack.addLast(file)
        while (stack.isNotEmpty()) {
            val poll = stack.poll()!!
            val listFiles = poll.listFiles()
            listFiles?.forEach {
                if (it.isDirectory) {
                    stack.addLast(it)
                } else {
                    files.add(it)
                }
            }
        }
        return files
    }
}