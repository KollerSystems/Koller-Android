package com.example.koller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.koller.fragments.NewFragment

class StarsRecyclerAdapter (private val starList : ArrayList<StarData>) : RecyclerView.Adapter<StarsRecyclerAdapter.StarsViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view_rate_byte, parent, false)
        return StarsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StarsViewHolder, position: Int) {
        val currentItem = starList[position]
        holder.title.text = currentItem.title
    }

    override fun getItemCount(): Int {
        return starList.size
    }

    class StarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.view_rate_byte_text)
    }
}