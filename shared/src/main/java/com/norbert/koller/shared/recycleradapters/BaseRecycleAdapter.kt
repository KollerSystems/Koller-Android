package com.norbert.koller.shared.recycleradapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.core.view.iterator
import androidx.core.widget.doBeforeTextChanged
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.search.SearchBar
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.helpers.RecyclerViewHelper

abstract class BaseRecycleAdapter() :
    PagingDataAdapter<Any, RecyclerView.ViewHolder>(BaseComparator) {

    lateinit var RecyclerView: RecyclerView

    var state: Int = STATE_NONE
    var withLoadingAnim: Boolean = true

    var beforeRefresh: (() -> Unit)? = null

    var chipGroupSort: ChipGroup? = null
    var chipGroupFilter: ChipGroup? = null
    var searchBar: SearchView? = null

    abstract fun getViewType(): Int

    abstract fun getDataTag(): String

    override fun onAttachedToRecyclerView(RecyclerView: RecyclerView) {
        this.RecyclerView = RecyclerView

        for (chip in chipGroupFilter!!) {
            chip as Chip
            chip.doBeforeTextChanged { text, start, before, count ->
                fullRefresh()
            }
        }


        if (searchBar != null) {
            searchBar!!.tag = searchBar!!.editTextSearch.text.toString()
            searchBar!!.editTextSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (searchBar!!.tag != searchBar!!.editTextSearch.text.toString()) {
                        fullRefresh()
                    }

                    searchBar!!.tag = searchBar!!.editTextSearch.text.toString()
                }
                false
            }

            searchBar!!.buttonSearchCancel.setOnClickListener {
                searchBar!!.editTextSearch.setText("")
                if (searchBar!!.tag != searchBar!!.editTextSearch.text.toString()) {
                    fullRefresh()
                }


                searchBar!!.tag = ""
            }
        }

        RecyclerView.post {
            chipGroupSort?.setOnCheckedStateChangeListener { chipGroup: ChipGroup, ints: MutableList<Int> ->
                fullRefresh()
            }
        }
    }

    var beingEmptied: Boolean = false

    fun seemlessRefresh() {
        beforeRefresh?.invoke()
        refresh()
    }

    fun fullRefresh() {

        RecyclerView.scrollToPosition(0)

        beingEmptied = true
        Log.d("INFO", "START TO EMPTY")
        seemlessRefresh()

        RecyclerView.scrollToPosition(0)


        beingEmptied = false
        Log.d("INFO", "START TO LOAD")
        seemlessRefresh()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (super.getItemCount() == 0 && state == STATE_NONE) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_list_element_nothing_to_display, parent, false)
            return EmptyViewHolder(view)
        } else {

            return when (viewType) {
                VIEW_TYPE_USER -> {
                    val view =
                        LayoutInflater.from(parent.context).inflate(getViewType(), parent, false)
                    createViewHolder(view)
                }

                VIEW_TYPE_SEPARATOR -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_date, parent, false)
                    DateViewHolder(view)
                }

                VIEW_TYPE_LOADING -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_loading, parent, false)
                    EmptyViewHolder(view)
                }

                else -> {
                    val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.view_error_retry, parent, false)
                    ErrorViewHolder(view)
                }
            }
        }
    }

    open fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return BaseViewHolder(view)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        Log.d("TEST", "state: ${state}, super.getItemCount ${super.getItemCount()}")
        if (state == STATE_NONE && super.getItemCount() == 0)
            return

        if (state != STATE_NONE && position == itemCount - 1) {

            when (getItemViewType(position)) {
                VIEW_TYPE_RETRY -> {

                    val retryViewHolder = holder as ErrorViewHolder
                    retryViewHolder.button.setOnClickListener {
                        retry()
                    }
                }
            }

            return
        }

        val item = getItem(position)!!

        when (getItemViewType(position)) {
            VIEW_TYPE_USER -> {

                onBindViewHolder(holder, item, position)

                if (position == snapshot().size) {
                    holder.itemView.post {
                        notifyItemChanged(snapshot().size - 1, Object())
                    }
                }
            }

            VIEW_TYPE_SEPARATOR -> {

                holder as DateViewHolder
                item as String
                holder.text.text = item
            }
        }


        RecyclerViewHelper.roundRecyclerItemsVerticallyWithSeparator(
            holder.itemView,
            position,
            this
        )


    }

    abstract fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int)

    override fun getItemCount(): Int {

        return if (state != STATE_NONE || super.getItemCount() == 0) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }


    override fun getItemViewType(position: Int): Int {

        if (super.getItemCount() == 0 && state == STATE_NONE)
            return -1

        if (position == itemCount - 1) {
            if (state == STATE_LOADING) {
                return VIEW_TYPE_LOADING
            } else if (state == STATE_ERROR) {
                return VIEW_TYPE_RETRY
            }
        }

        return when (getItem(position)) {
            is String -> VIEW_TYPE_SEPARATOR
            else -> VIEW_TYPE_USER
        }


    }


    companion object {
        const val STATE_NONE = 0
        const val STATE_LOADING = 1
        const val STATE_ERROR = 2


        const val VIEW_TYPE_USER = 0
        const val VIEW_TYPE_SEPARATOR = 1
        const val VIEW_TYPE_LOADING = 2
        const val VIEW_TYPE_RETRY = 3
    }


    class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.text_text)
        val description: TextView = itemView.findViewById(R.id.text_description)
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text: TextView = itemView.findViewById(R.id.text_view)
    }

    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val button: Button = itemView.findViewById(R.id.button)
    }
}