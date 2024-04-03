package com.norbert.koller.teacher.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import com.norbert.koller.shared.api.BaseProgramPagingSource
import com.norbert.koller.shared.fragments.CalendarBaseProgramsFragment
import com.norbert.koller.shared.recycleradapters.BasePagingSource
import com.norbert.koller.shared.recycleradapters.ListItem
import com.norbert.koller.teacher.R
import com.norbert.koller.teacher.recycleradapters.BaseProgramRecyclerAdapter

class CalendarBaseProgramsFragment : CalendarBaseProgramsFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        baseRecycleAdapter = BaseProgramRecyclerAdapter()


        addDateChip()

        val lessonLocalName : String = requireContext().getString(com.norbert.koller.shared.R.string.lesson).lowercase()

        addSortingChip("Length", com.norbert.koller.shared.R.string.length, arrayListOf(
            ListItem("1 ${lessonLocalName}", null, null, "1"),
            ListItem("2 ${lessonLocalName}", null, null, "2")
        ))



        super.onViewCreated(view, savedInstanceState)
    }
}