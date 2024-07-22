package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CanteenData

class CanteenRecyclerAdapter (private val canteenList : ArrayList<CanteenData>) : RecyclerView.Adapter<CanteenRecyclerAdapter.CanteenViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CanteenViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_canteen, parent, false)
        return CanteenViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CanteenViewHolder, position: Int) {



        val currentItem = canteenList[position]
        holder.category.text = currentItem.category
        holder.time.text = currentItem.time
        holder.foodName.text = currentItem.foodName
    }

    override fun getItemCount(): Int {
        return canteenList.size
    }

    class CanteenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val category : TextView = itemView.findViewById(R.id.canteen_text_category)
        val time : TextView = itemView.findViewById(R.id.canteen_text_time)
        val foodName : TextView = itemView.findViewById(R.id.canteen_text_food_name)
        val image : ImageView = itemView.findViewById(R.id.canteen_image)
    }

}