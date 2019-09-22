package com.jadynai.kotlindiary.thread

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import com.jadyn.ai.kotlind.function.ui.click
import com.jadynai.kotlindiary.R
import kotlinx.android.synthetic.main.activity_thread.*
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.locks.ReentrantLock

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
        Executors.newFixedThreadPool(3)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        "".replace("", "")

        textView.click {
            val consumer = Consumer()
            val producer = Producer()

            producer.start()
            consumer.start()
        }

        textView3.click {
            //            Consumer1().start()
//            Producer1().start()
            Thread({
                Log.d("cece", " runing: ${Thread.currentThread().name}")
                A.test(3000)
            }, "1").start()
            it.postDelayed({
                Thread({
                    Log.d("cece", " runing: ${Thread.currentThread().name}")
                    A.test(500)
                }, "2").start()
            }, 1000)
        }


        val group = "test"

        textView2.click {

            for (i in 0..1) {
//                fixThread.execute {
//                    Log.d("cece", "thread : ${Thread.currentThread().name} + $i")
//                }
                Thread(ThreadGroup(group)) {
                    Log.d("cece", "thread : ${Thread.currentThread().name} + $i")
                }.start()
            }
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