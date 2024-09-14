package com.norbert.koller.shared.recycleradapters

import androidx.recyclerview.widget.DiffUtil
import com.norbert.koller.shared.data.BaseData

object Comparator : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return false
    }
}