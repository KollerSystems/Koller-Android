package com.example.koller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EventsRecyclerAdapter (private val eventsList : ArrayList<EventsData>) : RecyclerView.Adapter<EventsRecyclerAdapter.EventsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_panel, parent, false)
        return EventsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
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
    }
}