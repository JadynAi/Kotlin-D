package com.jadyn.kotlinp.leetcode.linknode

import org.w3c.dom.Node
import kotlin.random.Random

/**
 *JadynAi since 4/5/21
 */
fun main() {
    val first = ListNode(1)
    val random = Random(3)
    var next = first
    val set = hashSetOf<Int>()
    for (i in 0..6) {
        var v = random.nextInt(0, 66)
        while (set.contains(v)) {
            v = random.nextInt(0, 66)
        }
        set.add(v)
        val node = ListNode(v)
        next.next = node
        next = node
    }
    printNode(first)
//    printNode(reverseNode1(first))
//    printNode(reverseBetween(first, 1, 4))
    println("find mid ${findMidNode(first).vv}")
}

/**
 * 快慢指针找中心
 * */
fun findMidNode(head: ListNode?): ListNode? {
    var fast = head
    var slow = head
    while (fast?.next != null && fast.next?.next != null) {
        println("fast ${fast.vv} slow ${slow.vv}")
        fast = fast.next?.next
        slow = slow?.next
    }
    return slow
}

/**
 * 反转部分链表
 * 这个关键思想是 首先添加一个虚假的头链表，这个是为了防止越界
 *
 * 先找到left的前一个节点
 * 保留left的节点
 * 然后反转left -> end 的链表
 * 然后拿到pre
 * 将left的前一个节点的next指向pre
 * 然后用之前保留的left的节点指向最后一个end的节点
 * */
fun reverseBetween(head: ListNode?, left: Int, right: Int): ListNode? {
    val fake = ListNode(-1)
    fake.next = head
    var startNode: ListNode? = fake
    var index = 1
    while (startNode != null && index < left) {
        startNode = startNode.next
        index++
    }
    var cur = startNode?.next
    val last = cur
    var next = cur
    var pre: ListNode? = null
    var starIndex = left
    while (cur != null && starIndex <= right) {
        next = cur.next
        cur.next = pre
        pre = cur
        cur = next
        starIndex++
    }
    startNode?.next = pre
    last?.next = next
    return fake.next
}

// 反转链表
fun reverseNode(f: ListNode?): ListNode? {
    if (f?.next == null) return f
    val last = reverseNode(f.next)
    f.next?.next = f
    f.next = null
    return last
}

// 迭代思想解决链表反转问题
fun reverseNode1(f: ListNode?): ListNode? {
    var pre: ListNode? = null
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
val ListNode?.vv: Int
    get() = this?.v ?: -1

fun printNode(f: ListNode?) {
    var n: ListNode? = f
    val s = StringBuilder()
    while (n != null) {
        s.append("${",".takeIf { s.isNotBlank() } ?: ""}${n.v}")
        n = n.next
    }
    println(s)
}

class ListNode(val v: Int) {
    var next: ListNode? = null
}
