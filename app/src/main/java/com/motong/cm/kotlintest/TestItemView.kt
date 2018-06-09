package com.motong.cm.kotlintest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laihua.framework.ui.adapter.kotlinAdapter.AbsItemViewK
import com.laihua.framework.ui.adapter.kotlinAdapter.inflate
import kotlinx.android.synthetic.main.item_test.*

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/9
 *@ChangeList:
 */
class TestItemView : AbsItemViewK<String>() {
    override fun onCreateView(ctx: Context, parent: ViewGroup?): View {
        return LayoutInflater.from(ctx).inflate(R.layout.item_test, parent, false)
    }

    override fun showItem(pos: Int, d: String?) {
        item_tv.text = d
    }

}

class TestItemView1 : AbsItemViewK<Int>() {
    override fun onCreateView(ctx: Context, parent: ViewGroup?): View {
        return inflate(ctx, R.layout.item_test, parent)
    }

    override fun showItem(pos: Int, d: Int?) {
        item_tv.text = d.toString()
    }

}