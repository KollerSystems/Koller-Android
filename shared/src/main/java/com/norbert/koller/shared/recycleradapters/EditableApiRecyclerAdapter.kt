package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.view.iterator
import androidx.core.widget.doBeforeTextChanged
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
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
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.managers.getAttributeColor
import com.norbert.koller.shared.viewmodels.ListViewModel

abstract class EditableApiRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {

    abstract fun onClick(holder: RecyclerView.ViewHolder, item: BaseData)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {
        super.onBindItemViewHolder(holder, item, position)
        checkItem(holder, item)
        holder.itemView.setOnClickListener {
            if(viewModel.selectedItems.isNotEmpty()){
                toggleItem(holder, item)
            }
            else{
                onClick(holder, item)
            }
        }

        holder.itemView.setOnLongClickListener {
            toggleItem(holder, item)
            return@setOnLongClickListener true
        }
    }

    fun checkItem(holder: RecyclerView.ViewHolder, item: BaseData){
        if(viewModel.selectedItems.contains(item.getMainID())){
            toggleOnItem(holder, item)
        }
        else{
            toggleOffItem(holder, item)
        }
    }

    fun toggleItem(holder: RecyclerView.ViewHolder, item: BaseData){
        if(!viewModel.selectedItems.contains(item.getMainID())){
            toggleOnItem(holder, item)
        }
        else{
            toggleOffItem(holder, item)
            if(viewModel.selectedItems.size == 0){

            }
        }
        (holder.itemView.context as MainActivity).setToolbarTitle(viewModel.selectedItems.size.toString())
    }

    fun toggleOnItem(holder: RecyclerView.ViewHolder, item: BaseData){
        (holder.itemView as MaterialCardView).
        setCardBackgroundColor(
            holder.itemView.context.getAttributeColor(
                com.google.android.material.R.attr.colorSurfaceContainerHigh))

        viewModel.selectedItems.add(item.getMainID())
        getIconHolder(holder).getChildAt(0).isVisible = false
        getIconHolder(holder).getChildAt(1).isVisible = true
    }

    fun toggleOffItem(holder: RecyclerView.ViewHolder, item: BaseData){
        (holder.itemView as MaterialCardView).
        setCardBackgroundColor(
            holder.itemView.context.getAttributeColor(
                com.google.android.material.R.attr.colorSurfaceContainerLow))

        viewModel.selectedItems.remove(item.getMainID())
        getIconHolder(holder).getChildAt(0).isVisible = true
        getIconHolder(holder).getChildAt(1).isVisible = false
    }

    abstract fun getIconHolder(holder: RecyclerView.ViewHolder) : ViewGroup
}