package com.jadyn.ai.kotlind.function.asyncview

import android.content.Context
import android.content.ContextWrapper
import java.lang.ref.WeakReference

/**
 *JadynAi since 2021/4/29
 */
class ViewContext(context: Context) : ContextWrapper(context) {

    var curContextRef: WeakReference<Context> = WeakReference(context)
    
    
}