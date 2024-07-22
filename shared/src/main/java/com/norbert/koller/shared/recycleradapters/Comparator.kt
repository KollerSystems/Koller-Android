package com.norbert.koller.shared.recycleradapters

import androidx.recyclerview.widget.DiffUtil
import com.norbert.koller.shared.data.BaseData

object Comparator : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is BaseData && newItem is BaseData) {
            return oldItem.getMainID() == newItem.getMainID()
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is BaseData && newItem is BaseData) {
            return oldItem.getMainID() == newItem.getMainID()
        }
        return true
    }
}