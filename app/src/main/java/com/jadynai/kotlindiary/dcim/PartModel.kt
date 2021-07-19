package com.jadynai.kotlindiary.dcim

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException

/**
 *JadynAi since 2021/7/16
 */
class PartModel : ViewModel() {

    suspend fun loadData(): List<File> {
        return withContext(Dispatchers.IO) { readFile("DCIM") }
    }

    private fun readFile(dir: String): List<File> {
        return readRecursion(File(dir))
    }

    private fun readRecursion(dir: File): List<File> {
        if (!dir.exists()) {
            throw FileNotFoundException("not find!!!")
        }
        val list = ArrayList<File>()
        val listFiles = dir.listFiles()
        listFiles?.forEach {
            if (it.isDirectory) {
                list.addAll(readRecursion(it))
            } else {
                list.add(it)
            }
        }
        return list
    }


}