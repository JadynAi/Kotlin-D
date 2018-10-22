package com.jadynai.kotlindiary.function.ui.recyclerview

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jadynai.kotlindiary.function.ui.event
import com.jadynai.kotlindiary.utils.TooFastChecker

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

    var itemCompare: ((D, D) -> Boolean)? = null

    private var bind: AcroViewHolder<D>.() -> Unit = {}

    private val tooFastChecker by lazy {
        TooFastChecker()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcroViewHolder<D> {
        val acrobatItem = acrobatMgr.items[viewType]
        val view = LayoutInflater.from(parent.context).inflate(acrobatItem.getResId(), parent, false)
        val viewHolder = AcroViewHolder(view, acrobatItem)
        if (acrobatItem.hasEvent()) {
            view.event({
                acrobatItem.click?.apply {
                    if (viewHolder.adapterPosition >= 0 && !tooFastChecker.isTooFast) {
                        invoke(acrobatMgr.data[viewHolder.adapterPosition], viewHolder.adapterPosition)
                    }
                }
            }, {
                acrobatItem.doubleTap?.apply {
                    if (viewHolder.adapterPosition >= 0 && !tooFastChecker.isTooFast) {
                        invoke(acrobatMgr.data[viewHolder.adapterPosition], viewHolder.adapterPosition)
                    }
                }
            }, {
                acrobatItem.longPress?.apply {
                    if (viewHolder.adapterPosition >= 0 && !tooFastChecker.isTooFast) {
                        invoke(acrobatMgr.data[viewHolder.adapterPosition], viewHolder.adapterPosition)
                    }
                }
            })
        }
        viewHolder.bind()
        viewHolder.doBindEvent()
        acrobatItem.onViewCreate(parent, view, viewHolder)
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
        val diffCallBack = DiffCallback(acrobatMgr.data, dataList)
        val calculateDiff = DiffUtil.calculateDiff(diffCallBack)
        calculateDiff.dispatchUpdatesTo(this)
        acrobatMgr.setData(dataList)
        return this
    }

    fun getData(): ArrayList<D> {
        val list = arrayListOf<D>()
        list.addAll(acrobatMgr.data)
        return list
    }

    fun bindEvent(click: AcroViewHolder<D>.() -> Unit): AcrobatAdapter<D> {
        this.bind = click
        return this
    }

    fun itemCompareRule(rule: (D, D) -> Boolean): AcrobatAdapter<D> {
        this.itemCompare = rule
        return this
    }

    fun notifyItemRemove(pos: Int) {
        val data = acrobatMgr.data
        if (data.isNotEmpty()) {
            if (pos < 0 || pos > data.lastIndex) {
                return
            }
            data.removeAt(pos)
            notifyDataSetChanged()
            if (data.isEmpty()) {
                emptyEvent?.invoke()
            }
        }
    }

    private inner class DiffCallback(private var mOldData: List<D>,
                                     private var mNewData: List<D>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return mOldData.size
        }

        override fun getNewListSize(): Int {
            return mNewData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = mOldData.get(oldItemPosition)
            val new = mNewData.get(newItemPosition)
            itemCompare?.apply {
                return invoke(old, new)
            }
            return old.toString().equals(new.toString())
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = mOldData.get(oldItemPosition)
            val new = mNewData.get(newItemPosition)
            return old.toString().equals(new.toString())
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