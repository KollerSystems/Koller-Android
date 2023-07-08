package com.example.shared.recycleradapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

data class ListItem(val function: () -> Unit, val title: String, val description: String?, val icon: Drawable?)

class ListAdapter (val dialog : BottomSheetDialogFragment, private val listItem : ArrayList<ListItem>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_item_list_dialog_list_dialog_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentItem = listItem[position]
        holder.title.text = currentItem.title
        holder.itemView.setOnClickListener {
            currentItem.function.invoke()
            dialog.dismiss()
        }
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