package com.norbert.koller.teacher.recycleradapters

import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseProgramTypeData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.data.StudyGroupTypeData
import com.norbert.koller.shared.recycleradapters.BaseRecycleAdapter

class BaseProgramTypeRecyclerAdapter() : BaseRecycleAdapter() {



    override fun getViewType(): Int {
        return R.layout.view_user_item
    }

    override fun getDataTag(): String {
        return "base_program_type"
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context

        holder as BaseViewHolder

        item as BaseProgramTypeData

        holder.title.text = item.topic

        val description = "${item.teacher?.name} • ${item.rid}"

        holder.description.text = description



        holder.itemView.setOnClickListener {



        }
    }

}