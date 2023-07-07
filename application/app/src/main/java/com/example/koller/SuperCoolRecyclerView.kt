package com.example.koller

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
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
    }
}