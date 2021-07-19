package com.jadyn.kotlinp.unittest

import android.os.Environment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.util.ArrayList

/**
 *JadynAi since 2021/7/19
 */
class GalleryLoader {

    suspend fun loadDCIMCatalog() = withContext(Dispatchers.IO) {
        val rootFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        rootFile ?: return@withContext
        if (rootFile.listFiles().isNullOrEmpty()) {
            return@withContext
        }
        val allList = rootFile.listRecursionFilesMapNotNull {
            it
        }
        allList.sort()
    }

    private fun <T> File.listRecursionFilesMapNotNull(transform: (File) -> T?): ArrayList<T> {
        val files = ArrayList<T>()
        forEachFile { f ->
            if (f.isDirectory) {
                files.addAll(f.listRecursionFilesMapNotNull(transform))
            } else {
                transform.invoke(f)?.let { files.add(it) }
            }
        }
        return files
    }

    private fun File.forEachFile(fileCallback: (File) -> Unit): Array<File>? {
        val ss = list() ?: return null
        val n = ss.size
        val fs = arrayOfNulls<File>(n)
        for (i in 0 until n) {
            val f = File(this, ss[i])
            fs[i] = f
            fileCallback.invoke(f)
        }
        @Suppress("UNCHECKED_CAST")
        return fs as Array<File>
    }
}