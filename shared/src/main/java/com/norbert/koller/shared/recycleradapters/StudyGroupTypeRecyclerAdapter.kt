package com.norbert.koller.shared.recycleradapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.StudyGroupTypeData

abstract class StudyGroupTypeRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {

    abstract fun getFragment(item: StudyGroupTypeData) : Fragment

    override fun getViewType(): Int {
        return R.layout.item_user
    }

    override fun getDataTag(): String {
        return "study_group_type"
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {

        super.onBindViewHolder(holder, item, position)

        val context = holder.itemView.context

        holder as BaseViewHolder

        item as StudyGroupTypeData

        holder.title.text = item.topic

        val description = "${item.teacher?.name} • ${item.rid}"

        holder.description.text = description

        holder.itemView.setOnClickListener {
            Log.d("HNDGFSAIJKUHUIVHJIOEWJOPIKPOLDPFS", item.getMainID().toString())
            (context as MainActivity).addFragmentWithTransition(getFragment(item), holder.itemView as MaterialCardView, getTransitionName(item.getMainID()))
        }
    }

}