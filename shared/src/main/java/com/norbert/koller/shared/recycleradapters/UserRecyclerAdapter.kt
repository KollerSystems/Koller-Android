package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.customviews.UserView

class UserRecyclerAdapter() : ApiRecyclerAdapterWithTransition() {
    override fun getViewType(): Int {
        return R.layout.item_user
    }

    override fun getDataTag(): String {
        return "user"
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item : Any, position: Int) {

        super.onBindViewHolder(holder, item, position)

        val context = holder.itemView.context
        holder as UserViewHolder

        item as UserData
        holder.userBadge.getImage().setImageDrawable(
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
        val userBadge : UserView = itemView.findViewById(R.id.user_badge)
        val title : TextView = itemView.findViewById(R.id.text_title)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}