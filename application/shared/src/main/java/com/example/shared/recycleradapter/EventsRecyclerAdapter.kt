package com.example.shared.recycleradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.EventsData
import com.example.shared.fragments.bottomsheet.NewFragment

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
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.events_title)
        val description : TextView = itemView.findViewById(R.id.events_description)
        val image : ImageView = itemView.findViewById(R.id.events_image)

        init {
            itemView.setOnClickListener {

                val dialog = NewFragment()
                dialog.show((itemView.context as FragmentActivity).supportFragmentManager, NewFragment.TAG)
            }
        }
    }
}