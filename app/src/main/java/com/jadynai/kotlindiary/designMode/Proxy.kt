package com.jadynai.kotlindiary.designMode

/**
 *@version:
 *@FileDescription: 代理模式
 *@Author:Jing
 *@Since:2019-08-16
 *@ChangeList:
 */
interface ProductB {
    fun buildProduct()
}

class ProductBu : ProductB {
    override fun buildProduct() {
    }
}

class Proxy : ProductB {

    private lateinit var productB: ProductB

    override fun buildProduct() {
    }
}