package com.jadyn.ai.kotlind.function.ui.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.view.ViewGroup

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/22
 *@ChangeList:
 */
class ViewPagerAdapter(fm: FragmentManager,
                       private val titles: ArrayList<String> = arrayListOf(),
                       private val fragments: ArrayList<Class<out Fragment>>,
                       private var args: ArrayList<Bundle> = arrayListOf()) : FragmentPagerAdapter(fm) {

    var curFragment: Fragment? = null
        private set

    var curPos: Int = 0
        private set

    override fun getItem(position: Int): Fragment? {
        return try {
            val instance = fragments[position].newInstance()
            if (args.isNotEmpty()) {
                instance.arguments = args[position]
            }
            instance
        } catch (e: Exception) {
            null
        }
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? = if (titles.isEmpty()) "" else titles[position]

    override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
        super.setPrimaryItem(container, position, `object`)
        curFragment = `object` as Fragment
        curPos = position
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        // 覆写destroyItem并且空实现,这样每个Fragment中的视图就不会被销毁
        // super.destroyItem(container, position, object);
    }

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE
}