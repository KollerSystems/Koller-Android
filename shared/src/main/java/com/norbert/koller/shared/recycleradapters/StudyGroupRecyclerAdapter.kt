package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.search.SearchBar
import com.norbert.koller.shared.customviews.SearchView
import com.norbert.koller.shared.fragments.bottomsheet.BaseProgramDetailsFragment
import com.norbert.koller.shared.data.BaseProgramData
import com.norbert.koller.shared.data.StudyGroupData
import com.norbert.koller.shared.fragments.bottomsheet.RoomOrderBottomSheet

abstract class StudyGroupRecyclerAdapter() : BaseRecycleAdapter() {

    abstract fun onItemPress(fragmentActivity: FragmentActivity, item : StudyGroupData)

    override fun getViewType(): Int {
        return R.layout.view_user_item
    }

    override fun getDataTag(): String {
        return "study_group"
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {


        val context = holder.itemView.context
        holder as BaseViewHolder

        item as StudyGroupData

        holder.title.text = item.topic

        var description ="${item.teacher!!.name} • ${ApplicationManager.createClassesText(context, item.lesson, item.length)}"

        holder.description.text = description



        holder.itemView.setOnClickListener {

            onItemPress((holder.itemView.context as FragmentActivity), item)

        }
    }

}