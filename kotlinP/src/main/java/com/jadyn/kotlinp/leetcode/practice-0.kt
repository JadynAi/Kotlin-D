package com.jadyn.kotlinp.leetcode

/**
 *JadynAi since 3/4/21
 */
fun main() {
    val p = isPalindrome(".P")
    print("result $p")
}

fun isPalindrome(s: String): Boolean {
    if (s.length <= 1) {
        return true
    }
    var i = 0
    var j = s.length - 1
    while (j - i >= 1) {
        val iC = s[i]
        val jC = s[j]
        if (iC.isAlphabet()) {
            if (jC.isAlphabet()) {
                i++
                j--
                if (!iC.equals(jC, true)) {
                    return false
                }
            } else {
                j--
            }
        } else {
            i++
            if (!jC.isAlphabet()) {
                j--
            }
        }
    }
    return true
}

fun Char.isAlphabet(): Boolean {
    return this in 'a'..'z' || this in 'A'..'Z' || this in '0'..'9'
}