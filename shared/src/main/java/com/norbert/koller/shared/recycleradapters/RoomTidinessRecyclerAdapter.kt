package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.databinding.ItemTextBinding

abstract class RoomTidinessRecyclerAdapter() : EditableApiRecyclerAdapter() {

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ItemTextViewHolder(ItemTextBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getTypeName(context: Context): String {
        return context.getString(R.string.room_order)
    }

    override fun getIconHolder(holder: RecyclerView.ViewHolder): ViewGroup {
        holder as ItemTextViewHolder
        return holder.itemBinding.flIconHolder
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {
        super.onBindItemViewHolder(holder, item, position)

        holder as ItemTextViewHolder

        item as RoomData

        holder.itemBinding.textTitle.text = "Hibátlan"
        holder.itemBinding.textDescription.text = "Kis Gazsi"
        holder.itemBinding.textIcon.text = "5"
    }

    class ItemTextViewHolder(val itemBinding: ItemTextBinding) : RecyclerView.ViewHolder(itemBinding.root)


}