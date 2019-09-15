package com.jadynai.kotlindiary.designMode

/**
 *@version:
 *@FileDescription:
 *@Author:Jing
 *@Since:2019-09-14
 *@ChangeList:
 */
class Strategy {
    private val absOneStrategy: OneStrategy? = null
}

interface OneStrategy {

    fun buildProduct()
}

class WanjuStrategy : OneStrategy {
    
    override fun buildProduct() {
    }

}