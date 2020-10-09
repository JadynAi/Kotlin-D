package com.jadynai.kotlindiary.coroutine

import java.util.concurrent.ThreadFactory
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author wangfei 2020.07.09
 *
 * 获取线程池中的ThreadFactory
 */

class ThreadFactoryCreator private constructor() {

    companion object {
        @JvmStatic
        fun createThreadFactory(namePrefix: String): ThreadFactory {
            return NameThreadFactory(namePrefix)
        }
    }
}

/**
 * 可以命名的线程的工厂类
 */
private class NameThreadFactory constructor() : ThreadFactory {
    private var group: ThreadGroup? = null
    private val threadNumber = AtomicInteger(1)
    private lateinit var namePrefix: String

    constructor(namePrefix: String) : this() {
        val s = System.getSecurityManager()
        this.group = if (s != null) {
            s.threadGroup
        } else {
            Thread.currentThread().threadGroup
        }
        this.namePrefix = namePrefix +
                "-thread-"
    }

    override fun newThread(r: Runnable): Thread {
        val t = Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0)
        if (t.isDaemon) {
            t.isDaemon = false
        }
        if (t.priority != Thread.NORM_PRIORITY) {
            t.priority = Thread.NORM_PRIORITY
        }
        return t
    }
}