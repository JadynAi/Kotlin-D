package com.jadynai.kotlindiary

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.text.TextPaint
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
import com.jadyn.ai.acrobat.recyclerview.itemdecoration.GroupInfo
import com.jadyn.ai.acrobat.recyclerview.itemdecoration.StickyHeaderDecor
import com.jadyn.ai.kotlind.utils.toastS
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.item_test.view.*


/**
 *@version:
 *@FileDescription:
 *@Author:jing
 *@Since:2018/7/4
 *@ChangeList:
 */
class StickyHeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val womens = getData()
        val acrobatAdapter = AcrobatAdapter<Girl> {
            itemDSL {
                resId(R.layout.item_test)
                showItem { d, pos, view ->
                    view.item_tv.text = "Item文本${d.name}"
                }
                onClick { d, pos ->
                    toastS("cece$d")
                }

            }
        }.setData(womens)

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("cece", "onScrolled: $dy")
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.d("cece", "onScrollStateChanged: $newState")
            }
        })

        recycler_view.apply {
            layoutManager = GridLayoutManager(this@StickyHeaderActivity, 3)
//                    .let {
//                it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
//                    override fun getSpanSize(position: Int): Int {
//                        return womens[position].info.getSpanSize(it)
//                    }
//                }
//                it
//            }
            setHasFixedSize(true)
            addItemDecoration(StickyHeaderDecor(this, {
                getData()[it].info
            }, 20, false, 50, { canvas, info, l, t, r, b ->
                canvas.drawRect(Rect(l, t, r, b), Paint().apply {
                    color = Color.BLUE
                })
                val textPaint = TextPaint().apply {
                    color = Color.WHITE
                }
                val titleX = l + 10
                val titleY = b - textPaint.fontMetrics.descent
                //绘制Title
                canvas.drawText("test${info.data}", titleX.toFloat(),
                        titleY, textPaint)
            }))
            adapter = acrobatAdapter
        }

        change_tv.setOnClickListener {
            recycler_view.smoothScrollBy(0, 2)
            recycler_view.smoothScrollBy(0, -2)
        }
    }
}

class Girl(var name: String, var info: GroupInfo)

fun getData(): ArrayList<Girl> {
    val list = arrayListOf<Girl>()
    for (i in 0 until 2) {
        list.add(Girl("刘诗诗", GroupInfo(i, 4, "刘诗诗")))
    }
//    for (i in 0 until 11) {
//        list.add(Girl("李冰冰", GroupInfo(i, 11, "李冰冰")))
//    }
//    for (i in 0 until 9) {
//        list.add(Girl("新垣结衣", GroupInfo(i, 9, "新垣结衣")))
//    }
//    for (i in 0 until 5) {
//        list.add(Girl("石原里美", GroupInfo(i, 5, "石原里美")))
//    }
    return list
}
