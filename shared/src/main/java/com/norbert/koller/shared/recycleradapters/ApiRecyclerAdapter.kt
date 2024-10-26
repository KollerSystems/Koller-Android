package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.iterator
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.databinding.ItemBinding
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.databinding.ItemNothingToDisplayBinding
import com.norbert.koller.shared.databinding.TextViewDateBinding
import com.norbert.koller.shared.databinding.ViewErrorRetryBinding
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.viewmodels.ListViewModel

abstract class ApiRecyclerAdapter() : PagingDataAdapter<Any, RecyclerView.ViewHolder>(Comparator) {

    lateinit var viewModel : ListViewModel

    fun seemlessRefresh() {
        viewModel.isRequestModeRefresh = true
        refresh()
    }

    fun fullRefresh() {
        if(viewModel.beingEmptied || viewModel.shouldBeEmpty) return
        viewModel.isRequestModeRefresh = false
        viewModel.beingEmptied = true
        refresh()
        viewModel.shouldBeEmpty = true
        refresh()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (super.getItemCount() == 0 && viewModel.state == ApiHelper.STATE_NONE) {
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

    fun getTransitionName(id : Int) : String{
        return "cardTransition_${id}position"
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {


        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                val item = getItem(position)!!
                item as BaseData
                val transitionName = getTransitionName(item.getMainID())
                ViewCompat.setTransitionName(holder.itemView as MaterialCardView, transitionName)
                onBindItemViewHolder(holder, item, position)
            }
            VIEW_TYPE_SEPARATOR -> {
                val item = getItem(position)!!
                holder as DateViewHolder
                item as String
                holder.itemBinding.text.text = item
            }
            VIEW_TYPE_RETRY -> {

                val retryViewHolder = holder as ErrorViewHolder
                retryViewHolder.itemBinding.btn.setOnClickListener {
                    retry()
                }
            }
        }


        RecyclerViewHelper.roundRecyclerItemsVerticallyWithSeparator(
            holder.itemView,
            position,
            this
        )


    }

    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int)

    override fun getItemCount(): Int {

        return if ((viewModel.state != ApiHelper.STATE_NONE || super.getItemCount() == 0) && !viewModel.isRequestModeRefresh) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }


    override fun getItemViewType(position: Int): Int {

        if (super.getItemCount() == 0 && viewModel.state == ApiHelper.STATE_NONE)
            return VIEW_TYPE_EMPTY

        if (position == itemCount - 1 && !viewModel.isRequestModeRefresh) {
            if (viewModel.state == ApiHelper.STATE_LOADING) {
                return VIEW_TYPE_LOADING
            } else if (viewModel.state == ApiHelper.STATE_ERROR) {
                return VIEW_TYPE_RETRY
            }
        }

        return when (getItem(position)) {
            is String -> VIEW_TYPE_SEPARATOR
            else -> VIEW_TYPE_ITEM
        }


    }


    companion object {
        const val VIEW_TYPE_EMPTY = -1
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