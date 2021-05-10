package com.jadynai.kotlindiary

import android.os.Bundle
import android.util.Log
import android.util.SizeF
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jadyn.ai.acrobat.recyclerview.AcrobatAdapter
import com.jadyn.ai.acrobat.recyclerview.AsyncViewHolderView
import com.jadyn.ai.acrobat.recyclerview.itemdecoration.DividerGrid
import com.jadyn.ai.acrobat.recyclerview.itemdecoration.GroupInfo
import com.jadyn.ai.kotlind.function.ui.click
import com.jadyn.ai.kotlind.utils.dp2px
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

    val array = arrayListOf<(View) -> Unit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val womens = getData()
        val acrobatAdapter = AcrobatAdapter<Girl> {
            itemDSL {
                getView {
                    AsyncViewHolderView(this@StickyHeaderActivity, R.layout.item_test, SizeF(1f, 1f))
                }
                showItem { d, pos, view ->
                    Log.d("ceceItem", "showItem: pos $pos")
                    (view as AsyncViewHolderView).doWhenTargetViewInflated {
                        view.item_tv.text = "Item文本${d.name}"
                    }
                }
                onViewCreate { parent, view, viewHolder ->
                    Log.d("ceceItem", "onCreate: oldPosition: ${viewHolder.oldPosition} pos :${viewHolder.adapterPosition}")
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

        recycler_view.layoutManager = GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        (recycler_view.layoutManager as GridLayoutManager).initialPrefetchItemCount = 10
        recycler_view.setItemViewCacheSize(3)
        recycler_view.addItemDecoration(DividerGrid(dp2px(5f), false))
        recycler_view.adapter = acrobatAdapter

        change_tv.setOnClickListener {
            acrobatAdapter.setDataForceNotify(getData())
        }
        change_tv2.click {
            acrobatAdapter.notifyItemChanged(10)
        }
        val d = 1000
        change_tv1.click {
            acrobatAdapter.setData(getData())
        }
    }
}

class Girl(var name: String, var info: GroupInfo)

fun getData(): ArrayList<Girl> {
    val list = arrayListOf<Girl>()
    for (i in 0 until 2) {
        list.add(Girl("刘诗诗", GroupInfo(i, 4, "刘诗诗")))
    }
    for (i in 0 until 11) {
        list.add(Girl("李冰冰", GroupInfo(i, 11, "李冰冰")))
    }
    for (i in 0 until 9) {
        list.add(Girl("新垣结衣", GroupInfo(i, 9, "新垣结衣")))
    }
    for (i in 0 until 5) {
        list.add(Girl("石原里美", GroupInfo(i, 5, "石原里美")))
    }
    for (i in 0 until 5) {
        list.add(Girl("测测不了", GroupInfo(i, 5, "安静的骄傲啊")))
    }
    return list
}
