package com.jadyn.ai.acrobat.recyclerview

import android.os.SystemClock
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jadyn.ai.kotlind.function.ui.event
import com.jadyn.ai.kotlind.utils.TooFastChecker
import com.jadyn.ai.kotlind.utils.setSafeNoNone

/**
 *@version:
 *@FileDescription: RecyclerView Adapter
 *@Author:AiLo
 *@Since:2018/6/12
 *@ChangeList:
 */
class AcrobatAdapter<D>(create: AcrobatMgr<D>.() -> Unit) :
        RecyclerView.Adapter<AcrobatAdapter.AcroViewHolder<D>>() {

    private val acrobatMgr by lazy {
        AcrobatMgr(this)
    }

    init {
        acrobatMgr.create()
    }

    var emptyEvent: (() -> Unit)? = null

    private var bind: AcroViewHolder<D>.() -> Unit = {}

    private val tooFastChecker by lazy {
        TooFastChecker()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcroViewHolder<D> {
        val startTime = SystemClock.uptimeMillis()
        val acrobatItem = acrobatMgr.items[viewType]
        val view = acrobatItem.getView(parent)
        val viewHolder = AcroViewHolder(view, acrobatItem)
        if (acrobatItem.hasEvent()) {
            view.event({
                acrobatItem.click?.apply {
                    if (viewHolder.adapterPosition >= 0 && !tooFastChecker.isTooFast) {
                        invoke(acrobatMgr.data[viewHolder.adapterPosition], viewHolder)
                    }
                }
            }, {
                acrobatItem.doubleTap?.apply {
                    if (viewHolder.adapterPosition >= 0 && !tooFastChecker.isTooFast) {
                        invoke(acrobatMgr.data[viewHolder.adapterPosition], viewHolder)
                    }
                }
            }, {
                acrobatItem.longPress?.apply {
                    if (viewHolder.adapterPosition >= 0 && !tooFastChecker.isTooFast) {
                        invoke(acrobatMgr.data[viewHolder.adapterPosition], viewHolder)
                    }
                }
            })
        }
        viewHolder.bind()
        viewHolder.doBindEvent()
        viewHolder.onCreateViewHolder(parent, view)
        Log.d("AcrobatAdapter", "onCreateViewHolder cost: ${SystemClock.uptimeMillis() - startTime}")
        return viewHolder
    }

    override fun getItemCount(): Int = acrobatMgr.data.size

    override fun onBindViewHolder(holder: AcroViewHolder<D>, position: Int) {
        holder.acrobatItem.showItem(acrobatMgr.data[position], position, holder.itemView)
    }

    override fun onBindViewHolder(holder: AcroViewHolder<D>, position: Int,
                                  payloads: MutableList<Any>) {
        if (payloads.isNullOrEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            holder.acrobatItem.showItem(acrobatMgr.data[position], position, holder.itemView, payloads)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return acrobatMgr.getItemConfig(position)
    }

    fun setData(dataList: List<D>,
                itemCompare: ((old: D, new: D) -> Boolean)? = null): AcrobatAdapter<D> {
        if (dataList.isEmpty()) {
            emptyEvent?.invoke()
        }
        // 比较数据需要构建两个不同对象的list，否则对比会出问题
        val diffCallBack = DiffCallback(ArrayList(acrobatMgr.data), dataList, itemCompare)
        val calculateDiff = DiffUtil.calculateDiff(diffCallBack)
        acrobatMgr.setData(dataList)
        calculateDiff.dispatchUpdatesTo(this)
        return this
    }

    fun setDataForceNotify(dataList: List<D>) {
        acrobatMgr.setData(dataList)
        notifyDataSetChanged()
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

    fun changItemData(pos: Int, newData: D) {
        acrobatMgr.data.setSafeNoNone(pos, newData)
    }

    private inner class DiffCallback(private var oldData: List<D>, private var newData: List<D>,
                                     private val itemCompare: ((D, D) -> Boolean)? = null) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return acrobatMgr.getItemConfig(oldItemPosition, oldData) == acrobatMgr.getItemConfig(newItemPosition, newData)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldData[oldItemPosition]
            val new = newData[newItemPosition]
            return itemCompare?.invoke(old, new) ?: (old?.equals(new) ?: false)
        }
    }

    class AcroViewHolder<D>(view: View, val acrobatItem: AcrobatItem<D>) :
            RecyclerView.ViewHolder(view) {

        private var click: ((View, pos: Int) -> Unit)? = null
        private var doubleTap: ((View, pos: Int) -> Unit)? = null
        private var longPress: ((View, pos: Int) -> Unit)? = null

        fun onClick(c: (View, pos: Int) -> Unit) {
            check(!acrobatItem.hasEvent()) { "item has inner event!!!" }
            click = c
        }

        fun onDoubleTap(d: (View, pos: Int) -> Unit) {
            check(!acrobatItem.hasEvent()) { "item has inner event!!!" }
            doubleTap = d
        }

        fun longPress(l: (View, pos: Int) -> Unit) {
            check(!acrobatItem.hasEvent()) { "item has inner event!!!" }
            longPress = l
        }

        fun onCreateViewHolder(parent: ViewGroup, view: View) {
            acrobatItem.onViewCreate(parent, view, this)
        }

        internal fun doBindEvent() {
            if (click != null || doubleTap != null || longPress != null) {
                itemView.event({ if (adapterPosition >= 0) click?.invoke(itemView, adapterPosition) },
                        { if (adapterPosition >= 0) doubleTap?.invoke(itemView, adapterPosition) },
                        { if (adapterPosition >= 0) longPress?.invoke(itemView, adapterPosition) })
            }
        }
    }
}