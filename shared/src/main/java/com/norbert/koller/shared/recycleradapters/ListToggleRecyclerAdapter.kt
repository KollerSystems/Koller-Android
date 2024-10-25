package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View.VISIBLE
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment

class ListToggleItem(title: String, description: String? = null, icon: Drawable? = null, val tag : String? = null, var isChecked : Boolean = false) : ListItem(title, description, icon)

class ListToggleRecyclerAdapter (bottomSheet : ListBsdfFragment) : ListRecyclerAdapter(bottomSheet) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        super.onBindViewHolder(holder, position)

        val currentItem = bottomSheet.viewModel.list.value!![position] as ListToggleItem

        holder.itemBinding.checkBox.setOnCheckedChangeListener(null)
        holder.itemBinding.checkBox.isChecked = currentItem.isChecked
        holder.itemBinding.checkBox.visibility = VISIBLE
        holder.itemBinding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
            currentItem.isChecked = isChecked
        }


        holder.itemView.setOnClickListener {

            holder.itemBinding.checkBox.toggle()
        }
    }
}