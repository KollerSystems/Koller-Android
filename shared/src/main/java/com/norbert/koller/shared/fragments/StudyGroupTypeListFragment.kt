package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.StudyGroupTypePagingSource
import com.norbert.koller.shared.recycleradapters.PagingSource

abstract class StudyGroupTypeListFragment() : ListFragment() {

    override fun getFragmentTitle(): String? {
        return getString(R.string.study_groups)
    }

    override fun getPagingSource(): PagingSource {
        return StudyGroupTypePagingSource(requireContext(), getBaseViewModel())
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.abc, R.string.zyx,"Topic")

        addSearchbar("Topic")
    }

}