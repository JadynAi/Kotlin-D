package com.jadynai.kotlindiary

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.text.TextPaint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
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
class RecyclerViewActivity : AppCompatActivity() {

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
            layoutManager = GridLayoutManager(this@RecyclerViewActivity, 3)
        }

        change_tv.setOnClickListener {
            //            acrobatAdapter.setData(newData)
        }
    }
}
