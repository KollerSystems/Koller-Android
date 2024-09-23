package com.norbert.koller.shared.recycleradapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.helpers.RecyclerViewHelper

abstract class StudyGroupTypeRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {

    abstract fun getFragment(item: StudyGroupTypeData) : Fragment

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return RecyclerViewHelper.UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }


    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {

        super.onBindItemViewHolder(holder, item, position)

        val context = holder.itemView.context

        holder as RecyclerViewHelper.UserViewHolder

        item as StudyGroupTypeData

        holder.itemBinding.textTitle.text = item.topic

        val description = "${item.teacher?.name} • ${item.rid}"

        holder.itemBinding.textDescription.text = description

        holder.itemView.setOnClickListener {
            (context as MainActivity).addFragmentWithTransition(getFragment(item), holder.itemView as MaterialCardView, getTransitionName(item.getMainID()))
        }
    }

}