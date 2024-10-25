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
import com.norbert.koller.shared.databinding.ItemCheckBoxBinding
import com.norbert.koller.shared.databinding.ItemLoadingBinding
import com.norbert.koller.shared.databinding.ItemNothingToDisplayBinding
import com.norbert.koller.shared.databinding.ViewErrorRetryBinding
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.helpers.ApiHelper
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_ITEM
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_LOADING
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter.Companion.VIEW_TYPE_RETRY
import com.norbert.koller.shared.viewmodels.ListViewModel


class ListToggleApiRecyclerAdapter(val bottomSheet : ListBsdfFragment) :  PagingDataAdapter<Any, RecyclerView.ViewHolder>(Comparator) {

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
            return ApiRecyclerAdapter.EmptyViewHolder(binding)
        } else {

            return when (viewType) {
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
    }

    open fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ListRecyclerAdapter.ListViewHolder(ItemCheckBoxBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (viewModel.state == ApiHelper.STATE_NONE && super.getItemCount() == 0)
            return

        if (viewModel.state != ApiHelper.STATE_NONE && position == itemCount - 1) {

            when (getItemViewType(position)) {
                VIEW_TYPE_RETRY -> {

                    val retryViewHolder = holder as ApiRecyclerAdapter.ErrorViewHolder
                    retryViewHolder.itemBinding.btn.setOnClickListener {
                        retry()
                    }
                }
            }
            return
        }

        val item = getItem(position)!! as ListToggleItem


        holder as ListRecyclerAdapter.ListViewHolder

        val margin = holder.itemView.resources.getDimensionPixelSize(R.dimen.card_margin)
        val params = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        params.setMargins(0,margin,0,margin)
        holder.itemView.layoutParams = params

        if(!bottomSheet.viewModel.collapseText) {
            holder.itemBinding.textTitle.text = item.title
            if (!item.description.isNullOrEmpty()) {
                holder.itemBinding.textDescription.visibility = VISIBLE
                holder.itemBinding.textDescription.text = item.description
            }
        }
        else{
            holder.itemBinding.textTitle.text = "${item.title} • ${item.description}"
        }

        if(item.icon != null) {
            holder.itemBinding.imgIcon.visibility = VISIBLE
            holder.itemBinding.imgIcon.setImageDrawable(item.icon)
        }
        else{
            holder.itemBinding.imgIcon.visibility = GONE
        }

        holder.itemBinding.checkBox.setOnCheckedChangeListener(null)
        holder.itemBinding.checkBox.isChecked = item.isChecked
        holder.itemBinding.checkBox.visibility = VISIBLE
        holder.itemBinding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
            item.isChecked = isChecked
        }


        holder.itemView.setOnClickListener {

            holder.itemBinding.checkBox.toggle()
        }

        if (position == snapshot().size) {
            holder.itemView.post {
                notifyItemChanged(snapshot().size - 1, Object())
            }
        }
    }



    override fun getItemCount(): Int {

        return if ((viewModel.state != ApiHelper.STATE_NONE || super.getItemCount() == 0) && !viewModel.isRequestModeRefresh) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }


    override fun getItemViewType(position: Int): Int {

        if (super.getItemCount() == 0 && viewModel.state == ApiHelper.STATE_NONE)
            return -1

        if (position == itemCount - 1 && !viewModel.isRequestModeRefresh) {
            if (viewModel.state == ApiHelper.STATE_LOADING) {
                return VIEW_TYPE_LOADING
            } else if (viewModel.state == ApiHelper.STATE_ERROR) {
                return VIEW_TYPE_RETRY
            }
        }

        return VIEW_TYPE_ITEM
    }
}