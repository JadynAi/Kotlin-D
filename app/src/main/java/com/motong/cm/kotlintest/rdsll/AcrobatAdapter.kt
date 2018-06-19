package com.motong.cm.kotlintest.rdsll

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
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
class AcrobatAdapter<D>(create: AcrobatMgr<D>.() -> Unit) : RecyclerView.Adapter<AcrobatAdapter.AcroViewHolder>() {

    private val acrobatMgr by lazy {
        AcrobatMgr<D>()
    }

    init {
        acrobatMgr.create()
    }

    override fun onCreateViewHolder(parent: ViewGroup, redId: Int): AcroViewHolder {
        return AcroViewHolder(LayoutInflater.from(parent.context).inflate(redId, parent, false))
    }

    override fun getItemCount(): Int = acrobatMgr.items.size

    override fun onBindViewHolder(holder: AcroViewHolder, position: Int) {
        acrobatMgr.items[position].showItem(acrobatMgr.data[position], position, holder.itemView)
    }

    override fun onBindViewHolder(holder: AcroViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads?.isNotEmpty()) {
            acrobatMgr.items[position].showItem(acrobatMgr.data[position], position, holder.itemView, payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return acrobatMgr.items[position].getResId()
    }

    fun setDataWithDiff(dataList: ArrayList<D>) {
        val diffCallBack = DiffCallback()
        val updateData = acrobatMgr.newData(dataList)
        diffCallBack.setData(acrobatMgr.items, updateData)
        val calculateDiff = DiffUtil.calculateDiff(diffCallBack)
        calculateDiff.dispatchUpdatesTo(this)
        acrobatMgr.refreshData(dataList, updateData)
    }

    override fun onViewAttachedToWindow(holder: AcroViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            acrobatMgr.items[holder.adapterPosition].onItemAttachWindow(holder.adapterPosition, holder.itemView)
        }
    }

    private class DiffCallback : DiffUtil.Callback() {

        private var mOldData: List<*>? = null
        private var mNewData: List<*>? = null

        fun setData(oldData: List<*>, newData: List<*>) {
            mOldData = oldData
            mNewData = newData
        }

        override fun getOldListSize(): Int {
            mOldData?.apply {
                return size
            }
            return 0
        }

        override fun getNewListSize(): Int {
            mNewData?.apply {
                return size
            }
            return 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldData?.get(oldItemPosition) === mNewData?.get(newItemPosition)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldData?.get(oldItemPosition) == mNewData?.get(newItemPosition)
        }
    }

    class AcroViewHolder(override val containerView: View?) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    }
}