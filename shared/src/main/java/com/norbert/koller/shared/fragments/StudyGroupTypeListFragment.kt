package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectStudyGroupType
import com.norbert.koller.shared.recycleradapters.PagingSource
import com.norbert.koller.shared.recycleradapters.PagingSourceWithSeparator

abstract class StudyGroupTypeListFragment() : ListFragment() {

    override fun getFragmentTitle(): String? {
        return getString(R.string.study_groups)
    }

    override fun getPagingSource(): PagingSourceWithSeparator {
        getBaseViewModel().apiDataObject = ApiDataObjectStudyGroupType()
        return PagingSourceWithSeparator(requireContext(), getBaseViewModel())
    }

    override fun onSetUpSearching() {
        super.onSetUpSearching()

        setupSort(R.string.abc, R.string.zyx,"Topic")

        addSearchbar("Topic")
    }

}