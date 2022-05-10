package com.jadynai.kotlindiary.view

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jadynai.kotlindiary.R

/**
 *Jairett since 2022/5/5
 */
class ViewTextViewSaveInstanceStateFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return layoutInflater.inflate(R.layout.fragment_view_save_instance, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view as RecyclerView
        view.layoutManager = LinearLayoutManager(view.context)
        view.adapter = ViewTextViewSaveInstanceAdapter().apply {
            setData(arrayListOf("1", "2", "3", "4", "5", "6"))
        }
    }
}

internal class ViewTextViewSaveInstanceAdapter(private val itemClickCallback: (view: View, itemData: String) -> Unit = { _, _ -> Unit }
) : RecyclerView.Adapter<ViewTextViewSaveInstanceAdapterViewHolder>() {
    private val data = ArrayList<String>(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewTextViewSaveInstanceAdapterViewHolder {
        val binding = TestTextView(parent.context)
        val viewHolder = ViewTextViewSaveInstanceAdapterViewHolder(binding)
        binding.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition >= 0) {
                itemClickCallback.invoke(it, data[adapterPosition])
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewTextViewSaveInstanceAdapterViewHolder, position: Int) {
        data.getOrNull(position)?.let { holder.bindData(it) }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(d: List<String>) {
        val calculateDiff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return data.size
            }

            override fun getNewListSize(): Int {
                return d.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return true
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return data[oldItemPosition] == d[newItemPosition]
            }
        })
        calculateDiff.dispatchUpdatesTo(this)
        data.clear()
        data.addAll(d)
    }

}

internal class ViewTextViewSaveInstanceAdapterViewHolder(private val binding: TextView) : RecyclerView.ViewHolder(binding) {
    fun bindData(it: String) {
        binding.text = it
    }
}

class TestTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    override fun onSaveInstanceState(): Parcelable? {
        Log.d("TestTextView", "onSaveInstanceState: ")
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        Log.d("TestTextView", "onRestoreInstanceState: ")
        super.onRestoreInstanceState(state)
    }
    
    override fun setText(text: CharSequence?, type: BufferType?) {
        Log.d("TestTextView", "setText: ")
        super.setText(text, type)
    }
}