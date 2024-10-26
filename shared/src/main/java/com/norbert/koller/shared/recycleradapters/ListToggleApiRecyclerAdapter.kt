package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.databinding.ItemCheckBoxBinding
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.databinding.ItemNothingToDisplayBinding
import com.norbert.koller.shared.databinding.ViewErrorRetryBinding
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListToggleApiBsdfFragment
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_EMPTY
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_ITEM
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_LOADING
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_RETRY
import com.norbert.koller.shared.viewmodels.ListViewModel

class ListToggleApiRecyclerAdapter(val bottomSheet : ListToggleApiBsdfFragment) :  PagingDataAdapter<Any, RecyclerView.ViewHolder>(Comparator) {

    fun deselectAll(){
        bottomSheet.pagingViewModel.selectedItems = mutableSetOf()
        notifyDataSetChanged()
    }

    fun seemlessRefresh() {
        bottomSheet.pagingViewModel.isRequestModeRefresh = true
        refresh()
    }

    fun fullRefresh() {
        if(bottomSheet.pagingViewModel.beingEmptied || bottomSheet.pagingViewModel.shouldBeEmpty) return
        bottomSheet.pagingViewModel.isRequestModeRefresh = false
        bottomSheet.pagingViewModel.beingEmptied = true
        refresh()
        bottomSheet.pagingViewModel.shouldBeEmpty = true
        refresh()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            VIEW_TYPE_EMPTY -> {
                val binding = ItemNothingToDisplayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ApiRecyclerAdapter.EmptyViewHolder(binding)
            }
            VIEW_TYPE_ITEM -> {
                setItemViewHolder(parent)
            }
            VIEW_TYPE_LOADING -> {
                val binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ApiRecyclerAdapter.LoadingViewHolder(binding)
            }
            else -> {
                val binding = ViewErrorRetryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ApiRecyclerAdapter.ErrorViewHolder(binding)
            }
        }
    }

    fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ListRecyclerAdapter.ListViewHolder(ItemCheckBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            VIEW_TYPE_ITEM -> {
                val item = getItem(position) as BaseData

                holder as ListRecyclerAdapter.ListViewHolder

                val margin = holder.itemView.resources.getDimensionPixelSize(R.dimen.card_margin)
                val params = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                params.setMargins(0,margin,0,margin)
                holder.itemView.layoutParams = params

                if(!bottomSheet.viewModel.collapseText) {
                    holder.itemBinding.textTitle.text = item.getTitle()
                    if (item.getDescription().isNotEmpty()) {
                        holder.itemBinding.textDescription.visibility = VISIBLE
                        holder.itemBinding.textDescription.text = item.getDescription()
                    }
                }
                else{
                    holder.itemBinding.textTitle.text = "${item.getTitle()} • ${item.getDescription()}"
                }

                holder.itemBinding.imgIcon.visibility = GONE


                holder.itemBinding.checkBox.setOnCheckedChangeListener(null)
                holder.itemBinding.checkBox.isChecked = bottomSheet.pagingViewModel.selectedItems.contains(item.getMainID())
                holder.itemBinding.checkBox.visibility = VISIBLE
                holder.itemBinding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                    if(isChecked){
                        bottomSheet.pagingViewModel.selectedItems.add(item.getMainID())
                    }
                    else{
                        bottomSheet.pagingViewModel.selectedItems.remove(item.getMainID())
                    }
                }

                holder.itemView.setOnClickListener {

                    holder.itemBinding.checkBox.toggle()
                }
            }
            VIEW_TYPE_RETRY -> {

                val retryViewHolder = holder as ApiRecyclerAdapter.ErrorViewHolder
                retryViewHolder.itemBinding.btn.setOnClickListener {
                    retry()
                }
            }
        }
    }



    override fun getItemCount(): Int {

        return if ((bottomSheet.pagingViewModel.state != ApiHelper.STATE_NONE || super.getItemCount() == 0) && !bottomSheet.pagingViewModel.isRequestModeRefresh) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }


    override fun getItemViewType(position: Int): Int {

        if (super.getItemCount() == 0 && bottomSheet.pagingViewModel.state == ApiHelper.STATE_NONE)
            return VIEW_TYPE_EMPTY

        if (position == itemCount - 1 && !bottomSheet.pagingViewModel.isRequestModeRefresh) {
            if (bottomSheet.pagingViewModel.state == ApiHelper.STATE_LOADING) {
                return VIEW_TYPE_LOADING
            } else if (bottomSheet.pagingViewModel.state == ApiHelper.STATE_ERROR) {
                return VIEW_TYPE_RETRY
            }
        }

        return VIEW_TYPE_ITEM
    }
}