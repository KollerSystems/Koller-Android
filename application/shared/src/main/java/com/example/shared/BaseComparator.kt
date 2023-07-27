package com.example.shared

import androidx.recyclerview.widget.DiffUtil
import com.example.shared.data.BaseData

object BaseComparator : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is BaseData && newItem is BaseData) {
            return oldItem.ID == newItem.ID
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is BaseData && newItem is BaseData) {
            return oldItem.ID == newItem.ID
        }
        return true
    }
}