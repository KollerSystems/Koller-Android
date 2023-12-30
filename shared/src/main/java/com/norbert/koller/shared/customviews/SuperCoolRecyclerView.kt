package com.norbert.koller.shared.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.norbert.koller.shared.R
import com.norbert.koller.shared.recycleradapters.BaseRecycleAdapter


@SuppressLint("ClickableViewAccessibility")
class SuperCoolRecyclerView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    var appBar : AppBarLayout? = null
    private val FABScrollUp : FloatingActionButton
    private val textHeader: TextView
    val recyclerView: RecyclerView
    val swipeToRefresh : SwipeRefreshLayout

    private lateinit var recyclerAdapter : BaseRecycleAdapter


    init {

        View.inflate(context, R.layout.super_cool_recycler_view, this)
        FABScrollUp = findViewById(R.id.fab_scroll_to_top)
        textHeader = findViewById(R.id.text_view_recycler_view_header)
        recyclerView = findViewById(R.id.recycler_view)
        swipeToRefresh = findViewById(R.id.swipe_to_refresh)

        textHeader.visibility = GONE


        FABScrollUp.setOnClickListener{

            recyclerView.smoothScrollToPosition(0)

            recyclerView.clearOnScrollListeners()
            recyclerView.setOnTouchListener(null)

            recyclerView.setOnTouchListener { v, event ->

                recyclerView.clearOnScrollListeners()
                recyclerView.setOnTouchListener(null)

                v.onTouchEvent(event)
            }

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        appBar?.setExpanded(true)
                        recyclerView.clearOnScrollListeners()
                        recyclerView.setOnTouchListener(null)
                    }
                }
            })
        }



        //TODO: ennek a törlése, ha minden recycler view fasza
        recyclerView.post {
            if (recyclerView.adapter is BaseRecycleAdapter) {

                recyclerAdapter = (recyclerView.adapter as BaseRecycleAdapter)
                recyclerAdapter.addOnPagesUpdatedListener {
                    swipeToRefresh.isRefreshing = false
                    recyclerView.post {
                        updateTitle()
                    }
                }


                swipeToRefresh.setOnRefreshListener {

                    recyclerAdapter.withLoadingAnim = false
                    recyclerAdapter.seemlessRefresh()
                }



                updateTitle()
                recyclerView.setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->

                    updateTitle()

                }
            }
        }
    }

    fun updateTitle(){
        val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
        val firstVisibleItemIndex: Int = layoutManager.findFirstVisibleItemPosition()
        val firstCompletelyVisibleIndex: Int = layoutManager.findFirstCompletelyVisibleItemPosition()

        val pagingAdapter = (recyclerView.adapter as PagingDataAdapter<*, *>)

        if (firstVisibleItemIndex != -1) {
            val firstData = pagingAdapter.getItemViewType(firstVisibleItemIndex)
            val list = pagingAdapter.snapshot()
            if (firstData == 1) {

                textHeader.text = list[firstVisibleItemIndex] as String
            } else {

                for (index in (0 until firstVisibleItemIndex).reversed()) {
                    if (list[index] is String) {
                        textHeader.text = list[index] as String
                        break
                    }
                }

            }
        }

        if (firstCompletelyVisibleIndex != -1) {
            val firstData = pagingAdapter.getItemViewType(firstCompletelyVisibleIndex)
            if(firstCompletelyVisibleIndex != 0) {
                textHeader.visibility = VISIBLE
                if (firstData == 1) {

                    val firstCompletelyVisibleItem: View = layoutManager.getChildAt(1)!!
                    val top = firstCompletelyVisibleItem.top
                    val y = top - firstCompletelyVisibleItem.marginTop
                    val fullFirstViewHeight =
                        firstCompletelyVisibleItem.height + firstCompletelyVisibleItem.marginTop * 2
                    if (y < fullFirstViewHeight) {
                        textHeader.translationY = (y - fullFirstViewHeight).toFloat()
                    }
                } else {
                    textHeader.translationY = 0f
                }
            }
            else{
                textHeader.translationY = 0f
                textHeader.visibility = GONE
            }
        }
    }
}