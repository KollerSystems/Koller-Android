package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View.GONE
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment

class ListCardItem(title: String, description: String? = null, icon: Drawable? = null, val function: (() -> Unit)? = null) : ListItem(title, description, icon)

class ListCardRecyclerAdapter(bottomSheet : ListBsdfFragment) : ListRecyclerAdapter(bottomSheet) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        val currentItem = bottomSheet.viewModel.list.value!![position] as ListCardItem

        holder.itemBinding.checkBox.visibility = GONE

        holder.itemView.setOnClickListener {
            bottomSheet.dismiss()
            currentItem.function?.invoke()
        }
    }
}