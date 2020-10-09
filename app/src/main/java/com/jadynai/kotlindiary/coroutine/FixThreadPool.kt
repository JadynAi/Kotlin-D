package com.jadynai.kotlindiary.coroutine

import android.util.Log
import java.util.concurrent.*

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-12-31
 *@ChangeList:
 */
class FixThreadPool(cores: Int, factory: ThreadFactory = Executors.defaultThreadFactory()) :
        ThreadPoolExecutor(judgeCores(cores), judgeCores(cores), 0L, TimeUnit.MILLISECONDS, LinkedBlockingQueue<Runnable>(), factory) {

    constructor(name: String) : this(1, ThreadFactoryCreator.createThreadFactory(name))

    constructor(cores: Int, name: String) : this(cores, ThreadFactoryCreator.createThreadFactory(name))

    private val workersFutureTask by lazy { arrayListOf<RunnableFuture<*>>() }

    override fun execute(command: Runnable?) {
        if (command is RunnableFuture<*>) {
            workersFutureTask.add(command)
        }
        val ftask = newTaskFor<Void>(command, null)
        workersFutureTask.add(ftask)
        super.execute(ftask)
    }

    fun release() {
        queue.clear()
        cancelCurFutureTask()
    }

    private fun cancelCurFutureTask() {
        workersFutureTask.forEach {
            it.cancel(true)
        }
    }
}

fun judgeCores(core: Int): Int {
    val i = Runtime.getRuntime().availableProcessors() + 1
    Log.d("cores", "judgeCores: $i")
    return i.takeIf { core > i } ?: core
}