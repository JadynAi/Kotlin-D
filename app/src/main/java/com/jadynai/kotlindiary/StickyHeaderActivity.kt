package com.jadynai.kotlindiary

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
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

        val womens = getWomen()
        val acrobatAdapter = AcrobatAdapter<Women> {
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

        recycler_view.apply {
            addItemDecoration(StickyHeaderDecor({
                getWomen()[it].info
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
            layoutManager = GridLayoutManager(this@StickyHeaderActivity, 3)
        }

        change_tv.setOnClickListener {
            //            acrobatAdapter.setData(newData)
        }
    }
}

class Women(var name: String, var info: GroupInfo)

fun getWomen(): ArrayList<Women> {
    val list = arrayListOf<Women>()
    for (i in 0 until 4) {
        list.add(Women("刘诗诗", GroupInfo(i, 4, "刘诗诗")))
    }
    for (i in 0 until 11) {
        list.add(Women("李冰冰", GroupInfo(i, 11, "李冰冰")))
    }
    for (i in 0 until 9) {
        list.add(Women("新垣结衣", GroupInfo(i, 9, "新垣结衣")))
    }
    for (i in 0 until 5) {
        list.add(Women("石原里美", GroupInfo(i, 5, "石原里美")))
    }
    return list
}
