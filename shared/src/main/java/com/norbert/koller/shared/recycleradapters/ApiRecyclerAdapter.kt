package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
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
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.databinding.ItemBinding
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.databinding.ItemNothingToDisplayBinding
import com.norbert.koller.shared.databinding.TextViewDateBinding
import com.norbert.koller.shared.databinding.ViewErrorRetryBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper

abstract class ApiRecyclerAdapter() : PagingDataAdapter<Any, RecyclerView.ViewHolder>(Comparator) {

    lateinit var recyclerView: RecyclerView

    var state: Int = STATE_NONE
    var withLoadingAnim: Boolean = true

    var setOffsetToZero : Boolean = false

    var beforeRefresh: (() -> Unit)? = null

    var chipsSort: ChipGroup? = null
    var chipsFilter: ChipGroup? = null
    var searchBar: SearchView? = null

    abstract fun getDataTag(): String

    @SuppressLint("SetTextI18n")
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView

        for (chip in chipsFilter!!) {
            chip as Chip
            chip.doBeforeTextChanged { text, start, before, count ->
                fullRefresh()
            }
        }


        if (searchBar != null) {
            searchBar!!.tag = searchBar!!.getEditText().text.toString()
            searchBar!!.getEditText().setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (searchBar!!.tag != searchBar!!.getEditText().text.toString()) {
                        fullRefresh()
                    }

                    searchBar!!.tag = searchBar!!.getEditText().text.toString()
                }
                false
            }

            searchBar!!.getButton().setOnClickListener {
                searchBar!!.getEditText().setText("")
                if (searchBar!!.tag != searchBar!!.getEditText().text.toString()) {
                    fullRefresh()
                }


                searchBar!!.tag = ""
            }
        }

        recyclerView.post {
            chipsSort?.setOnCheckedStateChangeListener { chipGroup: ChipGroup, ints: MutableList<Int> ->
                fullRefresh()
            }
        }
    }

    var beingEmptied: Boolean = false

    fun seemlessRefresh() {
        beforeRefresh?.invoke()
        recyclerView.scrollToPosition(0)
        setOffsetToZero = true
        refresh()
        recyclerView.scrollToPosition(0)
    }

    fun fullRefresh() {

        recyclerView.scrollToPosition(0)

        beingEmptied = true
        Log.d("INFO", "START TO EMPTY")
        seemlessRefresh()

        recyclerView.scrollToPosition(0)


        beingEmptied = false
        Log.d("INFO", "START TO LOAD")
        seemlessRefresh()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (super.getItemCount() == 0 && state == STATE_NONE) {
            val binding = ItemNothingToDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return EmptyViewHolder(binding)
        } else {

            return when (viewType) {
                VIEW_TYPE_ITEM -> {
                    setItemViewHolder(parent)
                }

                VIEW_TYPE_SEPARATOR -> {
                    val binding = TextViewDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    DateViewHolder(binding)
                }

                VIEW_TYPE_LOADING -> {
                    val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    LoadingViewHolder(binding)
                }

                else -> {
                    val binding = ViewErrorRetryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                    ErrorViewHolder(binding)
                }
            }
        }
    }

    open fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.ItemViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (state == STATE_NONE && super.getItemCount() == 0)
            return

        if (state != STATE_NONE && position == itemCount - 1) {

            when (getItemViewType(position)) {
                VIEW_TYPE_RETRY -> {

                    val retryViewHolder = holder as ErrorViewHolder
                    retryViewHolder.itemBinding.btn.setOnClickListener {
                        retry()
                    }
                }
            }
            return
        }

        val item = getItem(position)!!

        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {

                onBindItemViewHolder(holder, item, position)

                if (position == snapshot().size) {
                    holder.itemView.post {
                        notifyItemChanged(snapshot().size - 1, Object())
                    }
                }
            }

            VIEW_TYPE_SEPARATOR -> {

                holder as DateViewHolder
                item as String
                holder.itemBinding.text.text = item
            }
        }


        RecyclerViewHelper.roundRecyclerItemsVerticallyWithSeparator(
            holder.itemView,
            position,
            this
        )


    }

    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int)

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
            else -> VIEW_TYPE_ITEM
        }


    }


    companion object {
        const val STATE_NONE = 0
        const val STATE_LOADING = 1
        const val STATE_ERROR = 2


        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_SEPARATOR = 1
        const val VIEW_TYPE_LOADING = 2
        const val VIEW_TYPE_RETRY = 3
    }

    class DateViewHolder(val itemBinding: TextViewDateBinding) : RecyclerView.ViewHolder(itemBinding.root)

    class LoadingViewHolder(val itemBinding: ItemLoadingBinding) : RecyclerView.ViewHolder(itemBinding.root){
        init{
            itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }

    class EmptyViewHolder(val itemBinding: ItemNothingToDisplayBinding) : RecyclerView.ViewHolder(itemBinding.root)

    class ErrorViewHolder(val itemBinding: ViewErrorRetryBinding) : RecyclerView.ViewHolder(itemBinding.root)
}