package com.motong.cm.kotlintest

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment(){

    protected var TAG = this.javaClass.name

    lateinit var mRootView: View

    private var isVisibleToUsers = false
    private var isOnCreateView = false
    private var isSetUserVisibleHint = false
    private var isByStop = false
    private var isPostSetVisibleTask = false
    private var isHiddenChanged = false
    private val setVisibleTask = Runnable {
        isPostSetVisibleTask = false
        setVisibleToUser(true)
    }

    val pageName: String
        get() = javaClass.simpleName

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView : ")
        isOnCreateView = true
        mRootView = LayoutInflater.from(activity).inflate(getResId(), null, false)
        return mRootView
    }

    abstract fun getResId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated : ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume : ")

        if (!isSetUserVisibleHint && !isHiddenChanged) {
            setVisibleToUser(true)
        } else if (isByStop) {
            isPostSetVisibleTask = true
        }
        isByStop = false
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause : ")
        isHiddenChanged = false
        if (isVisibleToUsers) {
            isByStop = true
        }
        setVisibleToUser(false)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d(TAG, "setUserVisibleHint")
        removeSetVisibleTask()
        isSetUserVisibleHint = true
        setVisibleToUser(isVisibleToUser)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.d(TAG, "onHiddenChanged : " + hidden)
        removeSetVisibleTask()
        isHiddenChanged = true
        setVisibleToUser(!hidden)
    }

    private fun removeSetVisibleTask() {
        if (isPostSetVisibleTask) {
            isPostSetVisibleTask = false
        }
    }

    private fun setVisibleToUser(isVisibleToUser: Boolean) {
        if (!isOnCreateView) {
            return
        }
        if (isVisibleToUser == isVisibleToUsers && !isHiddenChanged) {
            return
        }
        isVisibleToUsers = isVisibleToUser
        onVisibleToUserChanged(isVisibleToUsers)
    }

    protected open fun onVisibleToUserChanged(isVisibleToUser: Boolean) {
        Log.d(TAG, "##onVisibleToUserChanged() isVisibleToUser = " + isVisibleToUser)
    }
}
