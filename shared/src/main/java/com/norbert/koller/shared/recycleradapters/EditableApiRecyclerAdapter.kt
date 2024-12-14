package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.managers.CacheManager
import com.norbert.koller.shared.managers.getAttributeColor
import java.util.Locale

abstract class EditableApiRecyclerAdapter() : ApiRecyclerAdapter() {

    abstract fun onClick(holder: RecyclerView.ViewHolder, item: BaseData)

    open fun getTypeName(context: Context) : String{
        return context.getString(R.string.item)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        val mainActivity = recyclerView.context as MainActivity
        if(viewModel.selectedItems.size > 0){
            mainActivity.enableEditMode()
            mainActivity.onCancelEditMode = {
                deselectAll()
            }
            mainActivity.setToolbarTitle("${viewModel.selectedItems.size} ${getTypeName(mainActivity).toLowerCase(
                Locale.ROOT)}")
        }
    }

    fun deselectAll(){
        viewModel.selectedItems = mutableSetOf()
        notifyDataSetChanged()
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {
        checkItem(holder, item)
        holder.itemView.setOnClickListener {
            if(viewModel.selectedItems.isNotEmpty()){
                toggleItem(holder, item)
            }
            else{
                onClick(holder, item)
            }
        }

        if(CacheManager.getCurrentUserData()!!.role == 2){
            holder.itemView.setOnLongClickListener {
                toggleItem(holder, item)
                return@setOnLongClickListener true
            }
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
        val mainActivity = holder.itemView.context as MainActivity

        if(!viewModel.selectedItems.contains(item.getMainID())){
            toggleOnItem(holder, item)
            if(viewModel.selectedItems.size > 0 && mainActivity.onCancelEditMode == null){
                mainActivity.enableEditMode()
                mainActivity.onCancelEditMode = {
                    deselectAll()
                }
            }
        }
        else{
            toggleOffItem(holder, item)
            if(viewModel.selectedItems.size == 0){
                mainActivity.disableEditMode()
                return
            }
        }
        mainActivity.setToolbarTitle("${viewModel.selectedItems.size} ${getTypeName(mainActivity).toLowerCase(
            Locale.ROOT)}")
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