package com.jadynai.kotlindiary.designMode.authentication

/**
 *JadynAi since 2021/8/11
 */

/**
 * 一个提醒功能
 * */
class AlertOld {

    private val alertRule = AlertRule()
    private val notification = Notification()

    fun check(apiStaInfo: ApiStaInfo) {
//        if (A) {
//        }else if(B)
    }
}

class Alert {

    private val alertHandlers = arrayListOf<AlertHandler>()

    fun addHandler(alertHandler: AlertHandler) {
        alertHandlers.add(alertHandler)
    }

    fun check(apiStaInfo: ApiStaInfo) {
        alertHandlers.forEach { it.check(apiStaInfo) }
    }
}

class ApiStaInfo {

}

abstract class AlertHandler(
    protected val rule: AlertRule,
    protected val notification: Notification
) {

    abstract fun check(apiStaInfo: ApiStaInfo)
}

class AlertRule
class Notification
