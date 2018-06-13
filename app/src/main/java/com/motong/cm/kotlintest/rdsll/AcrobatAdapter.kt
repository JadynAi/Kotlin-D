package com.motong.cm.kotlintest.rdsll

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/12
 *@ChangeList:
 */
class AcrobatAdapter<D>(private val ctx: Context, create: AcrobatMgr<D>.() -> Unit) : RecyclerView.Adapter<AcrobatAdapter.AcroViewHolder>() {

    private val acrobatMgr by lazy {
        AcrobatMgr<D>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcroViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int = acrobatMgr.items.size

    override fun onBindViewHolder(holder: AcroViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    

    class AcroViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }
}