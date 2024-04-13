package com.norbert.koller.shared.recycleradapters

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData

abstract class StudyGroupTypeRecyclerAdapter() : BaseRecycleAdapter() {

    abstract fun onItemPress(fragmentActivity: FragmentActivity, item: StudyGroupTypeData)


    override fun getViewType(): Int {
        return R.layout.view_user_item
    }

    override fun getDataTag(): String {
        return "study_group_type"
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context

        holder as BaseViewHolder

        item as StudyGroupTypeData

        holder.title.text = item.topic

        val description = "${item.teacher?.name} • ${item.rid}"

        holder.description.text = description



        holder.itemView.setOnClickListener {

            onItemPress((holder.itemView.context as FragmentActivity), item)

        }
    }

}