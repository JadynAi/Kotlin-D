package com.jadynai.kotlindiary.thread

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.jadyn.ai.kotlind.function.ui.click
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_thread.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.Semaphore
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-07-28
 *@ChangeList:
 */
class ThreadActivity : AppCompatActivity() {

    private val lock = Object()

    private val queue = PriorityQueue<Int>(10)

    private val reLock = ReentrantLock()

    private val notFull = reLock.newCondition()
    private val notEmpty = reLock.newCondition()

    private val laOb by lazy {
        Any()
    }

    private val fixThread by lazy {
        Executors.newSingleThreadExecutor(object : ThreadFactory {
            private val group by lazy {
                System.getSecurityManager()?.threadGroup ?: Thread.currentThread().threadGroup
            }
            private val threadNum = AtomicInteger(1)

            override fun newThread(r: Runnable?): Thread {
                Log.d("ThreadActivity", "newThread: ")
                val t = Thread(group, r, "AsyncView-${threadNum.getAndIncrement()}", 0)
                if (t.isDaemon) t.isDaemon = false
                if (t.priority != Thread.NORM_PRIORITY) t.priority = Thread.NORM_PRIORITY
                return t
            }
        })
    }
    private val semaphore = Semaphore(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        textView.click {
            val consumer = Consumer()
            val producer = Producer()

            producer.start()
            consumer.start()
        }

        textView3.click {
            fixThread.shutdown()
        }

        thread_pool_test.click {
            fixThread.execute {
                for (i in 0..3) {
                    Thread.sleep(500)
                    Log.d("ThreadActivity", "onCreate: i$i")
                }
                Log.d("ThreadActivity", "onCreate: finish")
            }
        }

        thread_pool_stop.click {
            val a = Semaphore(1)
            val b = Semaphore(1)
            Thread {
                a.acquire()
                Thread.sleep(3000)
                b.acquire()
                Thread.sleep(9000)
                b.release()
                a.release()
            }.start()
            Thread {
                b.acquire()
                Thread.sleep(1000)
                a.release()
            }.start()
        }

    }

    inner class Consumer1 : Thread() {
        override fun run() {
            super.run()
            while (true) {
                reLock.lock()
                while (queue.size == 0) {
                    notEmpty.await()
                }
                Log.d("cece", "ree lock: ${queue.poll()}")
                sleep(200)
                notFull.signal()
                reLock.unlock()
            }
        }
    }

    inner class Producer1 : Thread() {
        override fun run() {
            super.run()
            while (true) {
                reLock.lock()
                while (queue.size == 10) {
                    notFull.await()
                }
                queue.offer(queue.size + 1)
                notEmpty.signal()
                sleep(100)
                reLock.unlock()
            }
        }
    }


    inner class Consumer : Thread() {
        override fun run() {
            super.run()
            while (true) {
                synchronized(lock) {
                    while (queue.size == 0) {
                        lock.wait()
                    }
                    val poll = queue.poll()
                    Log.d("cece", "poll: $poll")
                    lock.notify()
                }
            }
        }
    }

    inner class Producer : Thread() {
        override fun run() {
            super.run()
            while (true) {
                synchronized(lock) {
                    while (queue.size == 10) {
                        lock.wait()
                    }
                    queue.offer(queue.size + 1)
                    lock.notify()
                }
            }
        }
    }
}

class A {

    companion object {

        fun test(time: Long) {
            Log.d("cece", " execute test : ${Thread.currentThread().name}")
            synchronized(A::class.java) {
                Log.d("cece", " thread start sleep: ${Thread.currentThread().name}")
                Thread.sleep(time)
                Log.d("cece", " thread: ${Thread.currentThread().name}")
            }
        }

    }

}

open class BB {
    private constructor()

    constructor(context: Context) : this() {

    }
}

class CC(context: Context) : BB(context) {

}