package com.jadynai.kotlindiary.coroutine

import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import com.jadynai.kotlindiary.R
import kotlinx.coroutines.*

private const val KEY_VIEW_SCOPE: Int = R.id.view_scope

/**
 *Jairett since 2022/5/9
 */
val View.viewScope: CoroutineScope
    get() {
        getTag(KEY_VIEW_SCOPE)?.let {
            if (it is CoroutineScope)
                return it
            else
                Log.e("viewScope", "check why the value of KEY_VIEW_SCOPE is ${it.javaClass.name}")
        }

        val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())
        setTag(KEY_VIEW_SCOPE, scope)
        if (!ViewCompat.isAttachedToWindow(this)) {
            Log.w("viewScope",
                "Creating a CoroutineScope before ${javaClass.name} attaches to a window. " +
                        "Coroutine jobs won't be canceled if the view has never been attached to a window.")
        }
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {}

            override fun onViewDetachedFromWindow(view: View) {
                Log.d("viewScope", "view is detached")
                removeOnAttachStateChangeListener(this)
                setTag(KEY_VIEW_SCOPE, null)
                scope.cancel(CancellationException("view detached from window"))
            }
        })
        return scope
    }