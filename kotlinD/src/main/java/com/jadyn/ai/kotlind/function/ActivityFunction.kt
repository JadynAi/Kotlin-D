package com.jadyn.ai.kotlind.function

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.core.os.bundleOf

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/11/26
 *@ChangeList:
 */

inline fun <reified T : Activity> Context.start(vararg params: Pair<String, Any?>) {
    val intent = Intent(this, T::class.java)
    if (params.isNotEmpty()) {
        intent.putExtras(bundleOf(*params))
    }
    startActivity(this, intent)
}

var beforeStartAct: ((Context) -> Unit)? = null
var afterStartAct: ((Context) -> Unit)? = null

fun startActivity(context: Context, intent: Intent) {
    beforeStartAct?.invoke(context)
    context.startActivity(intent)
    afterStartAct?.invoke(context)
}