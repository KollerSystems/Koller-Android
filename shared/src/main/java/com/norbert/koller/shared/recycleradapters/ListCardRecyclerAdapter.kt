package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.view.View.GONE
import com.norbert.koller.shared.data.ListCardItem
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.ListCardStaticBsdfFragment

class ListCardRecyclerAdapter(bottomSheet : ListCardStaticBsdfFragment) : ListRecyclerAdapter(bottomSheet) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        bottomSheet as ListCardStaticBsdfFragment

        val currentItem = bottomSheet.getListCardStaticViewModel().list.value!![position] as ListCardItem

        holder.itemBinding.checkBox.visibility = GONE

        holder.itemView.setOnClickListener {
            bottomSheet.dismiss()
            currentItem.function?.invoke()
        }
    }
}