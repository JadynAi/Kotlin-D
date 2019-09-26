package com.jadyn.ai.acrobat.recyclerview

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IntRange
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jadyn.ai.kotlind.function.ui.event
import com.jadyn.ai.kotlind.utils.TooFastChecker

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
    internal val tagMap by lazy {
        SparseArray<Any>(1)
    }

    init {
        acrobatMgr.create()
    }

    var emptyEvent: (() -> Unit)? = null

    /**
     *  diff utils item compare
     * */
    private var itemCompare: ((D, D) -> Boolean)? = null

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
        acrobatItem.onViewCreate(parent, view, viewHolder)
        return viewHolder
    }

    override fun getItemCount(): Int = acrobatMgr.data.size

    override fun onBindViewHolder(holder: AcroViewHolder<D>, position: Int) {
        holder.acrobatItem.showItem(acrobatMgr.data[position], position, holder.itemView)
    }

    override fun onBindViewHolder(holder: AcroViewHolder<D>, position: Int,
                                  payloads: MutableList<Any>) {
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

    fun putTag(@IntRange(from = 1, to = 3) id: Int, tag: Any): AcrobatAdapter<D> {
        tagMap.put(id, tag)
        return this
    }

    fun notifyItemChangedSafe(pos: Int) {
        if (pos > -1 && pos < acrobatMgr.data.size) {
            notifyItemChanged(pos)
        }
    }

    private inner class DiffCallback(private var oldData: List<D>,
                                     private var newData: List<D>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldData.size
        }

        override fun getNewListSize(): Int {
            return newData.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return acrobatMgr.getItemConfig(oldItemPosition) == acrobatMgr.getItemConfig(newItemPosition)
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

        internal fun doBindEvent() {
            if (click != null || doubleTap != null || longPress != null) {
                itemView.event({ if (adapterPosition >= 0) click?.invoke(itemView, adapterPosition) },
                        { if (adapterPosition >= 0) doubleTap?.invoke(itemView, adapterPosition) },
                        { if (adapterPosition >= 0) longPress?.invoke(itemView, adapterPosition) })
            }
        }
    }
}