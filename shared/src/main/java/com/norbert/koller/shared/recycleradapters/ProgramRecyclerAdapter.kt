package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper

abstract class ProgramRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {

    abstract fun onItemPress(holder: RecyclerView.ViewHolder, item : BaseProgramData)

    override fun getDataTag(): String {
        return "base_program"
    }

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        super.onBindItemViewHolder(holder, item, position)

        val context = holder.itemView.context
        holder as RecyclerViewHelper.UserViewHolder

        item as BaseProgramData

        holder.itemBinding.textTitle.text = item.topic

        var description ="${item.teacher!!.name} • ${ApplicationManager.createClassesText(context, item.lesson, item.length)}"

        holder.itemBinding.textDescription.text = description

        holder.itemView.setOnClickListener {

            onItemPress(holder, item)


        }
    }
}