package com.example.koller

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ListItem(val title: String, val description: String?, val icon: Drawable?)

private val mOnClickListener: View.OnClickListener? = null

class ListAdapter (private val listItem : ArrayList<ListItem>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false)
        view.setOnClickListener(mOnClickListener);
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = listItem[position]
        holder.title.text = currentItem.title
        if(!currentItem.description.isNullOrEmpty()) {
            holder.description.visibility = VISIBLE
            holder.description.text = currentItem.description
        }
        holder.icon.setImageDrawable(currentItem.icon)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.simple_list_text_title)
        val description : TextView = itemView.findViewById(R.id.simple_list_text_description)
        val icon : ImageView = itemView.findViewById(R.id.simple_list_image_icon)
    }

}