package com.motong.cm.kotlintest.rdsll

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/12
 *@ChangeList:
 */
class AcrobatAdapter<D>(private var click: AcroViewHolder.() -> Unit = {}, create: AcrobatMgr<D>.() -> Unit) : RecyclerView.Adapter<AcrobatAdapter.AcroViewHolder>() {

    private val acrobatMgr by lazy {
        AcrobatMgr<D>()
    }

    init {
        acrobatMgr.create()
    }

    override fun onCreateViewHolder(parent: ViewGroup, redId: Int): AcroViewHolder {
        Log.d("cece", "onCreateViewHolder ")
        val viewHolder = AcroViewHolder(LayoutInflater.from(parent.context).inflate(redId, parent, false))
        viewHolder.click()
        return viewHolder
    }

    override fun getItemCount(): Int = acrobatMgr.items.size

    override fun onBindViewHolder(holder: AcroViewHolder, position: Int) {
        Log.d("cece", "onBindViewHolder ")
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

    fun bindClick(click: AcroViewHolder.() -> Unit):AcrobatAdapter<D> {
        this.click = click
        return this
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

    class AcroViewHolder(val containerView: View?) : RecyclerView.ViewHolder(containerView) {

        fun onClick(click: (View) -> Unit) {
            containerView?.setOnClickListener {
                click(it)
            }
        }
    }
}