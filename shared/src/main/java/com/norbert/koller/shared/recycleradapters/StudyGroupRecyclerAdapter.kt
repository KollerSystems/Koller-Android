package com.norbert.koller.shared.recycleradapters

import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.StudyGroupData

abstract class StudyGroupRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {

    abstract fun onItemPress(holder: RecyclerView.ViewHolder, item: StudyGroupData)

    override fun getViewType(): Int {
        return R.layout.item_user
    }

    override fun getDataTag(): String {
        return "study_group"
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {

        super.onBindViewHolder(holder, item, position)
        val context = holder.itemView.context
        holder as BaseViewHolder

        item as StudyGroupData

        holder.title.text = item.topic

        var description ="${item.teacher!!.name} • ${ApplicationManager.createClassesText(context, item.lesson, item.length)}"

        holder.description.text = description



        holder.itemView.setOnClickListener {

            onItemPress(holder, item)

        }
    }

}