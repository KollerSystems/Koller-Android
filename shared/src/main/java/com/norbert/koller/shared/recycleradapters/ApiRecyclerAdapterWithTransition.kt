package com.norbert.koller.shared.recycleradapters

import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.fragments.ListFragment

abstract class ApiRecyclerAdapterWithTransition() : ApiRecyclerAdapter() {

    lateinit var fragment : ListFragment

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val fragment = (recyclerView.context as MainActivity).supportFragmentManager.fragments[0]

        fragment.postponeEnterTransition()
        if(fragment.view == null) return
        (fragment.view as ViewGroup).viewTreeObserver
            .addOnPreDrawListener {
                fragment.startPostponedEnterTransition()
                true
            }

    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {

        item as BaseData
        val transitionName = getTransitionName(item.getMainID())
        ViewCompat.setTransitionName(holder.itemView as MaterialCardView, transitionName)
    }

    fun getTransitionName(id : Int) : String{
        return "cardTransition_${id}position"
    }

}