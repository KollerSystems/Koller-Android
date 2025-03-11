package com.norbert.koller.shared.fragments

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.appcompat.content.res.AppCompatResources
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.norbert.koller.shared.R
import com.norbert.koller.shared.api.ApiDataObjectClass
import com.norbert.koller.shared.api.ApiDataObjectGroup
import com.norbert.koller.shared.data.ListToggleItem
import com.norbert.koller.shared.databinding.FragmentStatisticsBinding

class StatisticsFragment : SearchFragment() {

    lateinit var contentBinding : FragmentStatisticsBinding

    override fun getFragmentTitle(): String {
        return getString(R.string.statistics)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contentBinding = FragmentStatisticsBinding.inflate(layoutInflater)

        binding.root.addView(contentBinding.root)
        val params = CoordinatorLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        params.behavior = AppBarLayout.ScrollingViewBehavior()
        contentBinding.root.layoutParams = params

        setupSort(R.string.from_better, R.string.from_worst,"Name")

        addDateChipWithTemplates()

        addSortingChip("Gender", R.string.gender, arrayListOf(
            ListToggleItem(getString(R.string.woman), null, AppCompatResources.getDrawable(requireContext(), R.drawable.woman), 0),
            ListToggleItem(getString(R.string.man), null, AppCompatResources.getDrawable(requireContext(), R.drawable.man), 1)
        ))

        addSortingChip("Class.ID", R.string.class_, ApiDataObjectClass())

        addSortingChip("Group.ID", R.string.group, ApiDataObjectGroup(), true)
    }
}