package com.jadynai.kotlindiary.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jadynai.kotlindiary.adapter.ViewHolder.Companion.getViewHolder
import kotlinx.android.extensions.LayoutContainer
import java.util.*

/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/6/6
 *@ChangeList:
 */
open class SimpleAdapter<T> protected constructor(dataList: List<T>,
                                                  layout: Int = -1,
                                                  private inline val onBind: ViewHolder<T>.(item: T) -> Unit,
                                                  private inline var onCreate: ViewHolder<T>.(viewType: Int) -> Unit = {},
                                                  private inline val newItemView: (parent: ViewGroup, viewType: Int) -> View = { _, _ ->
                                                      throw RuntimeException("newItemView must be implemented when layout is not set!")
                                                  })
    : RecyclerView.Adapter<ViewHolder<T>>() {

    var data: List<T> = dataList
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    private var mLayoutId: Int = layout

    constructor(data: List<T>, layout: Int, onBind: ViewHolder<T>.(item: T) -> Unit): this(data, layout, onBind, onCreate = {})

    constructor(data: List<T>, newItemView: (parent: ViewGroup, viewType: Int) -> View,
                onBind: ViewHolder<T>.(item: T) -> Unit): this(data, layout = -1, onBind = onBind, newItemView = newItemView)

    constructor(data: Array<T>, layout: Int, onBind: ViewHolder<T>.(item: T) -> Unit): this(Arrays.asList(*data), layout, onBind)

    constructor(data: Array<T>, newItemView: (parent: ViewGroup, viewType: Int) -> View,
                onBind: ViewHolder<T>.(item: T) -> Unit): this(Arrays.asList(*data), newItemView, onBind)

    fun bindEvent(onCreate: ViewHolder<T>.(viewType: Int) -> Unit): SimpleAdapter<T> {
        this.onCreate = onCreate
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        val holder = if (mLayoutId != -1) {
            getViewHolder(parent.context, this,null, parent, mLayoutId)
        } else {
            getViewHolder(newItemView(parent, viewType), this)
        }
        holder.onCreate(viewType)
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

}


@Suppress("UNCHECKED_CAST")
class ViewHolder<T>(override val containerView: View?, val adapter: SimpleAdapter<T>) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    companion object {
        fun <T> getViewHolder(context: Context, adapter: SimpleAdapter<T>, convertView: View?, parent: ViewGroup, layoutId: Int): ViewHolder<T> {
            if (convertView == null) {
                val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
                val holder = ViewHolder(view, adapter)
                view.tag = holder
                return holder
            }
            return convertView.tag as ViewHolder<T>
        }

        fun <T> getViewHolder(view: View, adapter: SimpleAdapter<T>): ViewHolder<T> = ViewHolder(view, adapter)
    }

    private val views = SparseArray<View>()
    private val convertView: View = containerView!!

    @Suppress("UNCHECKED_CAST")
    fun <V : View> view(res: Int): V {
        if (views.get(res) == null) {
            views.put(res, convertView.findViewById(res))
        }
        return views.get(res) as V
    }

}
