package com.norbert.koller.teacher.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.BaseProgramTypeData
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.recycleradapters.ApiRecyclerAdapter

class BaseProgramTypeRecyclerAdapter() : ApiRecyclerAdapter() {


    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {

        holder as RecyclerViewHelper.UserViewHolder

        item as BaseProgramTypeData

        holder.itemBinding.textTitle.text = item.topic

        val description = "${item.teacher?.name} • ${item.rid}"

        holder.itemBinding.textDescription.text = description

        holder.itemView.setOnClickListener {



        }
    }

}