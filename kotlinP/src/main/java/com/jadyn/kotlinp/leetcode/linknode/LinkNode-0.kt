package com.jadyn.kotlinp.leetcode.linknode

import kotlin.random.Random

/**
 *JadynAi since 4/5/21
 */
fun main() {
    val first = Node(1)
    val random = Random(3)
    var next = first
    val set = hashSetOf<Int>()
    for (i in 0..6) {
        var v = random.nextInt(0, 66)
        while (set.contains(v)) {
            v = random.nextInt(0, 66)
        }
        set.add(v)
        val node = Node(v)
        next.next = node
        next = node
    }
    printNode(first)
    printNode(reverseNode1(first))
}

fun reverseNode(f: Node?): Node? {
    if (f?.next == null) return f
    val last = reverseNode(f.next)
    f.next?.next = f
    f.next = null
    return last
}

// 迭代思想解决链表反转问题
fun reverseNode1(f: Node?): Node? {
    var pre :Node? = null
    var cur = f
    var nxt = f
    while (cur != null) {
        nxt = cur.next
        cur.next = pre
        pre = cur
        cur = nxt
    }
    return pre
}

//--------------base function-----------------------
val Node?.vv: Int
    get() = this?.v ?: -1

fun printNode(f: Node?) {
    var n: Node? = f
    val s = StringBuilder()
    while (n != null) {
        s.append("${",".takeIf { s.isNotBlank() } ?: ""}${n.v}")
        n = n.next
    }
    println(s)
}

class Node(val v: Int) {
    var next: Node? = null
}
