package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.api.StudyGroupPagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class StudyGroupListFragment : ListFragment() {

    override fun getFragmentTitle(): String? {
        return null
    }


    override fun getPagingSource(): PagingSource {
        return StudyGroupPagingSource(requireContext(), getBaseViewModel())
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(com.norbert.koller.shared.R.string.newest, com.norbert.koller.shared.R.string.oldest,"Date", "desc,Lesson:desc", "asc,Lesson:asc")
        addSearchbar("Topic")
    }
}