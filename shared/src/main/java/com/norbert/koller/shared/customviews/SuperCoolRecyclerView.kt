package com.norbert.koller.shared.customviews

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
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
import com.norbert.koller.shared.databinding.ViewSuperCoolRecyclerBinding
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter


@SuppressLint("ClickableViewAccessibility")
class SuperCoolRecyclerView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    var appBar : AppBarLayout? = null
    val binding = ViewSuperCoolRecyclerBinding.inflate(LayoutInflater.from(context), this)

    fun getRecyclerAdapter() : PagingDataAdapter<*, *>{
        return binding.recyclerView.adapter as PagingDataAdapter<*, *>
    }

    fun setRecyclerAdapter(adapter : PagingDataAdapter<*, *>){
        binding.recyclerView.adapter = adapter
    }


    init {
        binding.textTitle.visibility = GONE

        var previousWidth = 0;
        viewTreeObserver.addOnGlobalLayoutListener {
            val width = width

            if (width != previousWidth) {
                val tabletMaxWidth = resources.getDimensionPixelSize(R.dimen.tablet_max_width_with_text_container)
                val fullCardPadding = resources.getDimensionPixelSize(R.dimen.full_card_padding)
                if (binding.recyclerView.measuredWidth - fullCardPadding * 2 > tabletMaxWidth) {
                    Log.d("TAGHELLO", "AJAJAJAJAJ")
                    val correctPadding = (binding.recyclerView.measuredWidth - tabletMaxWidth) / 2
                    binding.recyclerView.setPadding(
                        correctPadding,
                        binding.recyclerView.paddingTop,
                        correctPadding,
                        binding.recyclerView.paddingBottom
                    )
                }
                else{
                    binding.recyclerView.setPadding(
                        fullCardPadding,
                        binding.recyclerView.paddingTop,
                        fullCardPadding,
                        binding.recyclerView.paddingBottom
                    )
                }
                previousWidth = width

            }
        }

        binding.fabScrollToTop.setOnClickListener{

            binding.recyclerView.smoothScrollToPosition(0)

            binding.recyclerView.clearOnScrollListeners()
            binding.recyclerView.setOnTouchListener(null)

            binding.recyclerView.setOnTouchListener { v, event ->

                binding.recyclerView.clearOnScrollListeners()
                binding.recyclerView.setOnTouchListener(null)

                v.onTouchEvent(event)
            }

            binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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

        binding.recyclerView.layoutManager = LinearLayoutManager(context)


        /*getRecyclerAdapter().addOnPagesUpdatedListener {
            binding.recyclerView.post {
                updateTitle()
            }
        }*/

        updateTitle()
        binding.recyclerView.setOnScrollChangeListener { _: View, _: Int, _: Int, _: Int, _: Int ->
            updateTitle()
        }
    }

    fun updateTitle(){
        val layoutManager = (binding.recyclerView.layoutManager as LinearLayoutManager)
        val firstVisibleItemIndex: Int = layoutManager.findFirstVisibleItemPosition()
        val firstCompletelyVisibleIndex: Int = layoutManager.findFirstCompletelyVisibleItemPosition()

        if (firstVisibleItemIndex != -1) {
            val firstData = getRecyclerAdapter().getItemViewType(firstVisibleItemIndex)
            val list = getRecyclerAdapter().snapshot()
            if (firstData == 1) {

                binding.textTitle.text = list[firstVisibleItemIndex] as String
            } else {

                for (index in (0 until firstVisibleItemIndex).reversed()) {
                    if (list[index] is String) {
                        binding.textTitle.text = list[index] as String
                        break
                    }
                }

            }
        }

        if (firstCompletelyVisibleIndex != -1) {
            val firstData = getRecyclerAdapter().getItemViewType(firstCompletelyVisibleIndex)
            if(firstCompletelyVisibleIndex != 0) {
                binding.textTitle.visibility = VISIBLE
                if (firstData == 1) {

                    val firstCompletelyVisibleItem: View = layoutManager.getChildAt(1)!!
                    val top = firstCompletelyVisibleItem.top
                    val y = top - firstCompletelyVisibleItem.marginTop
                    val fullFirstViewHeight =
                        firstCompletelyVisibleItem.height + firstCompletelyVisibleItem.marginTop * 2
                    if (y < fullFirstViewHeight) {
                        binding.textTitle.translationY = (y - fullFirstViewHeight).toFloat()
                    }
                } else {
                    binding.textTitle.translationY = 0f
                }
            }
            else{
                binding.textTitle.translationY = 0f
                binding.textTitle.visibility = GONE
            }
        }
    }
}