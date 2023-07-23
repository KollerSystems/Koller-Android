package com.example.shared

import androidx.recyclerview.widget.DiffUtil
import com.example.shared.data.UserData

object UserComparator : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is UserData && newItem is UserData) {
            return oldItem.ID == newItem.ID
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem is UserData && newItem is UserData) {
            return oldItem == newItem
        }
        return true
    }
}