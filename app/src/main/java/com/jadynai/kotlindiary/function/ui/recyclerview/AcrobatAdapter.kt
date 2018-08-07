package com.jadynai.kotlindiary.function.ui.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jadynai.kotlindiary.function.ui.event

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

    var emptyEvent: (() -> Unit)? = null

    private var bind: AcroViewHolder<D>.() -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcroViewHolder<D> {
        val acrobatItem = acrobatMgr.items[viewType]
        val view = LayoutInflater.from(parent.context).inflate(acrobatItem.getResId(), parent, false)
        acrobatItem.onViewCreate(parent, view)
        val viewHolder = AcroViewHolder(view, acrobatItem)
        if (acrobatItem.hasEvent()) {
            view.event({ acrobatItem.click?.apply { this(acrobatMgr.data[viewHolder.adapterPosition], viewHolder.adapterPosition) } },
                    { acrobatItem.doubleTap?.apply { this(acrobatMgr.data[viewHolder.adapterPosition], viewHolder.adapterPosition) } },
                    { acrobatItem.longPress?.apply { this(acrobatMgr.data[viewHolder.adapterPosition], viewHolder.adapterPosition) } })
        }
        viewHolder.bind()
        viewHolder.doBindEvent()
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

    fun setData(dataList: ArrayList<D>): AcrobatAdapter<D> {
        if (dataList.isEmpty()) {
            emptyEvent?.apply {
                this()
            }
        }
        val diffCallBack = DiffCallback()
        diffCallBack.setData(acrobatMgr.data, dataList)
        val calculateDiff = DiffUtil.calculateDiff(diffCallBack)
        calculateDiff.dispatchUpdatesTo(this)
        acrobatMgr.setData(dataList)
        return this
    }

    fun getData() = acrobatMgr.data.clone() as ArrayList<D>

    fun bindEvent(click: AcroViewHolder<D>.() -> Unit): AcrobatAdapter<D> {
        this.bind = click
        return this
    }

    fun notifyItemRemove(pos: Int) {
        val data = getData()
        if (data.isNotEmpty()) {
            if (pos < 0 || pos > data.lastIndex) {
                return
            }
            data.removeAt(pos)
            setData(data)
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
            return mOldData?.size ?: 0
        }

        override fun getNewListSize(): Int {
            return mNewData?.size ?: 0
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldData?.get(oldItemPosition)?.equals(mNewData?.get(newItemPosition)) ?: false
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return mOldData?.get(oldItemPosition)?.equals(mNewData?.get(newItemPosition)) ?: false
        }
    }

    class AcroViewHolder<D>(view: View, val acrobatItem: AcrobatItem<D>) : RecyclerView.ViewHolder(view) {
        private var click: ((Int) -> Unit)? = null
        private var doubleTap: ((Int) -> Unit)? = null
        private var longPress: ((Int) -> Unit)? = null

        fun onClick(c: (Int) -> Unit) {
            if (acrobatItem.hasEvent()) {
                throw IllegalStateException("item has inner event!!!")
            }
            click = c
        }

        fun onDoubleTap(d: (Int) -> Unit) {
            if (acrobatItem.hasEvent()) {
                throw IllegalStateException("item has inner event!!!")
            }
            doubleTap = d
        }

        fun longPress(l: (Int) -> Unit) {
            if (acrobatItem.hasEvent()) {
                throw IllegalStateException("item has inner event!!!")
            }
            longPress = l
        }

        internal fun doBindEvent() {
            if (click != null || doubleTap != null || longPress != null) {
                itemView.event({ click?.apply { this((adapterPosition)) } },
                        { doubleTap?.apply { this(adapterPosition) } },
                        { longPress?.apply { this(adapterPosition) } })
            }
        }
    }
}