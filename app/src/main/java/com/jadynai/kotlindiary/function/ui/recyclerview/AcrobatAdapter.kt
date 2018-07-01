package com.jadynai.kotlindiary.function.ui.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
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
class AcrobatAdapter<D>(create: AcrobatMgr<D>.() -> Unit) : RecyclerView.Adapter<AcrobatAdapter.AcroViewHolder<D>>() {

    private val acrobatMgr by lazy {
        AcrobatMgr<D>()
    }

    init {
        acrobatMgr.create()
    }

    private var bind: AcroViewHolder<D>.() -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcroViewHolder<D> {
        val acrobatItem = acrobatMgr.items[viewType]
        val view = LayoutInflater.from(parent.context).inflate(acrobatItem.getResId(), parent, false)
        acrobatItem.onViewCreate(parent, view)
        val viewHolder = AcroViewHolder(view, acrobatItem)
        acrobatItem.click?.apply {
            viewHolder.itemView.setOnClickListener {
                this(viewHolder.adapterPosition)
            }
        }
        viewHolder.bind()
        return viewHolder
    }

    override fun getItemCount(): Int = acrobatMgr.data.size

    override fun onBindViewHolder(holder: AcroViewHolder<D>, position: Int) {
        holder.acrobatItem.showItem(acrobatMgr.data[position], position, holder.itemView)
    }

    override fun onBindViewHolder(holder: AcroViewHolder<D>, position: Int, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            holder.acrobatItem.showItem(acrobatMgr.data[position], position, holder.itemView, payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return acrobatMgr.getItemConfig(position)
    }

    fun setData(dataList: ArrayList<D>) : AcrobatAdapter<D> {
        val diffCallBack = DiffCallback()
        diffCallBack.setData(acrobatMgr.data, dataList)
        val calculateDiff = DiffUtil.calculateDiff(diffCallBack)
        calculateDiff.dispatchUpdatesTo(this)
        acrobatMgr.setData(dataList)
        return this
    }

    fun bindEvent(click: AcroViewHolder<D>.() -> Unit): AcrobatAdapter<D> {
        this.bind = click
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

    class AcroViewHolder<D>(view: View, val acrobatItem: AcrobatItem<D>) : RecyclerView.ViewHolder(view) {

        fun onClick(click: (View) -> Unit) {
            itemView?.setOnClickListener {
                click(it)
            }
        }
    }
}