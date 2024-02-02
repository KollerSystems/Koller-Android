package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.findFragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.transition.MaterialElevationScale
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.fragments.ListFragment

abstract class BaseRecyclerAdapterWithTransition() : BaseRecycleAdapter() {

    lateinit var fragment : ListFragment

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        fragment = recyclerView.findFragment<ListFragment>()

        fragment.exitTransition = null
        fragment.reenterTransition = MaterialElevationScale(/* growing= */ true)

        fragment.postponeEnterTransition()
        (fragment.requireView().parent as ViewGroup).viewTreeObserver
            .addOnPreDrawListener {
                fragment.startPostponedEnterTransition()
                true
            }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        item as BaseData
        val transitionName = getTransitionName(item.getMainID())
        ViewCompat.setTransitionName(holder.itemView as MaterialCardView, transitionName)

        afterBindViewHolder(holder, item, position)
    }

    abstract fun afterBindViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int)

    fun getTransitionName(id : Int) : String{
        return "cardTransition_${id}position"
    }

}