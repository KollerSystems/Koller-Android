package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.view.View.VISIBLE
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.fragments.bottomsheet.list.ListToggleStaticBsdfFragment

class ListToggleStaticRecyclerAdapter (bottomSheet : ListToggleStaticBsdfFragment) : ListRecyclerAdapter(bottomSheet) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        super.onBindViewHolder(holder, position)

        bottomSheet as ListToggleStaticBsdfFragment

        val currentItem = bottomSheet.viewModel.list.value!![position] as ListToggleItem

        holder.itemBinding.checkBox.setOnCheckedChangeListener(null)
        holder.itemBinding.checkBox.isChecked = bottomSheet.getToggleViewModel().selectedItems.contains(currentItem.id)
        holder.itemBinding.checkBox.visibility = VISIBLE
        holder.itemBinding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
            if(isChecked){
                bottomSheet.getToggleViewModel().selectedItems.add(currentItem.id)
            }
            else{
                bottomSheet.getToggleViewModel().selectedItems.remove(currentItem.id)
            }
        }


        holder.itemView.setOnClickListener {

            holder.itemBinding.checkBox.toggle()
        }
    }
}