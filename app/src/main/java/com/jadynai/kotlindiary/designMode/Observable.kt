package com.jadynai.kotlindiary.designMode

import androidx.lifecycle.Observer

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-08-28
 *@ChangeList:
 */
abstract class Subject {
    private val observers = ArrayList<Observer<String>>()

    fun registerObserver(observer: Observer<String>) {
        observers.add(observer)
    }

    abstract fun notifyChanged()
}

class CreateSubject : Subject() {
    override fun notifyChanged() {
    }
}