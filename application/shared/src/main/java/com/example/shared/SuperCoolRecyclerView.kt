package com.example.shared

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.shared.activities.MainActivity
import com.example.shared.data.CrossingData
import com.example.shared.data.UserData
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SuperCoolRecyclerView(context: Context, attrs: AttributeSet) : SwipeRefreshLayout(context, attrs) {

    var appBar : AppBarLayout? = null
    private val FABScrollUp : FloatingActionButton
    private val textHeader: TextView
    val recyclerView: RecyclerView

    init {
        View.inflate(context, R.layout.super_cool_recycler_view, this)
        FABScrollUp = findViewById(R.id.fab_scroll_to_top)
        textHeader = findViewById(R.id.text_view_recycler_view_header)
        recyclerView = findViewById(R.id.recycler_view)

        FABScrollUp.setOnClickListener{
            recyclerView.smoothScrollToPosition(0)
            appBar?.setExpanded(true)
        }


        recyclerView.setOnScrollChangeListener(){ view: View, i: Int, i1: Int, i2: Int, i3: Int ->

            val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
            val firstVisibleItemIndex : Int = layoutManager.findFirstVisibleItemPosition()
            val firstCompletelyVisibleIndex : Int = layoutManager.findFirstCompletelyVisibleItemPosition()

            val pagingAdapter = (recyclerView.adapter as PagingDataAdapter<*, *>)

            if(firstVisibleItemIndex != -1) {
                val firstData = pagingAdapter.getItemViewType(firstVisibleItemIndex)
                val list = pagingAdapter.snapshot()
                if (firstData == 1) {

                    textHeader.text = list[firstVisibleItemIndex] as String
                }
                else{

                    for (index in (0 until firstVisibleItemIndex).reversed()) {
                        if(list[index] is String){
                            textHeader.text = list[index] as String
                            break
                        }
                    }

                }
            }

            if(firstCompletelyVisibleIndex != -1){
                val firstData = pagingAdapter.getItemViewType(firstCompletelyVisibleIndex)
                if(firstData == 1 && firstCompletelyVisibleIndex != 0){
                    val firstCompletelyVisibleItem : View = layoutManager.getChildAt(1)!!
                    val top = firstCompletelyVisibleItem.top
                    val y = top - firstCompletelyVisibleItem.marginTop
                    val fullFirstViewHeight = firstCompletelyVisibleItem.height + firstCompletelyVisibleItem.marginTop * 2
                    if(y < fullFirstViewHeight){
                        textHeader.translationY = (y - fullFirstViewHeight).toFloat()
                    }
                }
                else{
                    textHeader.translationY = 0f
                }
            }
        }
    }
}