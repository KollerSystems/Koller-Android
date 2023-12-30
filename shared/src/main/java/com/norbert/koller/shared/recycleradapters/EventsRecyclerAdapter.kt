package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.EventsData
import com.norbert.koller.shared.fragments.bottomsheet.PostFragment
import com.google.android.material.chip.Chip
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.setVisibilityBy

class EventsRecyclerAdapter (private val eventsList : ArrayList<EventsData>) : RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_post, parent, false)
        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        val mainActivity : MainActivity = holder.itemView.context as MainActivity

        RecyclerViewHelper.roundRecyclerItemsVertically(holder.itemView, position, eventsList.size)

        val currentItem = eventsList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description

        holder.chipPlace.setVisibilityBy(currentItem.place)
        holder.chipDateTime.setVisibilityBy(currentItem.dateTime)
        holder.chipBPR.setVisibilityBy(currentItem.baseProgramReplacement)

        val isAnyChildVisible = holder.chipPlace.visibility == VISIBLE || holder.chipDateTime.visibility == VISIBLE || holder.chipBPR.visibility == VISIBLE

        (holder.chipPlace.parent as ViewGroup).setVisibilityBy(isAnyChildVisible)

        holder.posterUser.setOnClickListener{
            mainActivity.addFragment(MyApplication.userFragment(currentItem.UID))
        }

    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.events_title)
        val description : TextView = itemView.findViewById(R.id.events_description)
        val image : ImageView = itemView.findViewById(R.id.events_image)

        val chipPlace : Chip = itemView.findViewById(R.id.view_new_chip_place)
        val chipDateTime : Chip = itemView.findViewById(R.id.view_new_chip_date_time)
        val chipBPR : Chip = itemView.findViewById(R.id.view_new_chip_base_program_replacement)

        val posterUser : FrameLayout = itemView.findViewById(R.id.frame_layout_user)

        init {
            itemView.setOnClickListener {

                val dialog = PostFragment()
                dialog.show((itemView.context as FragmentActivity).supportFragmentManager, PostFragment.TAG)
            }
        }
    }
}