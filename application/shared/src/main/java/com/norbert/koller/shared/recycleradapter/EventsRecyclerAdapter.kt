package com.norbert.koller.shared.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.EventsData
import com.norbert.koller.shared.fragments.bottomsheet.NewFragment
import com.google.android.material.chip.Chip

class EventsRecyclerAdapter (private val eventsList : ArrayList<EventsData>) : RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_panel, parent, false)
        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {

        MyApplication.roundRecyclerItemsVertically(holder.itemView.context, holder.itemView, position, eventsList.size)

        val currentItem = eventsList[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description

        MyApplication.visibilityOn(holder.chipPlace, currentItem.place)
        MyApplication.visibilityOn(holder.chipDateTime, currentItem.dateTime)
        MyApplication.visibilityOn(holder.chipBPR, currentItem.baseProgramReplacement)

        val isAnyChildVisible = holder.chipPlace.visibility == VISIBLE || holder.chipDateTime.visibility == VISIBLE || holder.chipBPR.visibility == VISIBLE

        (holder.chipPlace.parent as ViewGroup).visibility = MyApplication.visibilityOn(isAnyChildVisible)

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

        init {
            itemView.setOnClickListener {

                val dialog = NewFragment()
                dialog.show((itemView.context as FragmentActivity).supportFragmentManager, NewFragment.TAG)
            }
        }
    }
}