package com.norbert.koller.shared.recycleradapters

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.fragment.app.findFragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransform.FADE_MODE_CROSS
import com.google.android.material.transition.MaterialElevationScale
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.RoundedBadgeImageView
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.fragments.ListFragment
import com.norbert.koller.shared.fragments.UsersFragment

class UserRecyclerAdapter(chipGroupSort: ChipGroup? = null, chipGroupFilter: ChipGroup? = null) : BaseRecyclerAdapterWithTransition(chipGroupSort, chipGroupFilter) {
    override fun getViewType(): Int {
        return R.layout.view_user_item
    }

    override fun getDataTag(): String {
        return "user"
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return UserViewHolder(view)
    }

    override fun afterBindViewHolder(holder: RecyclerView.ViewHolder, item : BaseData, position: Int) {



        val context = holder.itemView.context
        holder as UserViewHolder

        item as UserData
        holder.userBadge.image.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                R.drawable.person
            )
        )
        holder.userBadge.setUser(item)
        holder.title.text = item.name
        holder.description.text = item.createDescription()

        holder.itemView.setOnClickListener {

            if (item.uid == UserData.instance.uid) {
                ApplicationManager.openProfile(context)
            } else {

                val fragment = ApplicationManager.userFragment(item.uid)
                (context as MainActivity).addFragmentWithTransition(fragment, holder.itemView as MaterialCardView, getTransitionName(item.getMainID()))
            }

        }
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val userBadge : RoundedBadgeImageView = itemView.findViewById(R.id.user_badge)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}