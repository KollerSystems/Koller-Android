package com.norbert.koller.shared.recycleradapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragment
import com.norbert.koller.shared.helpers.RecyclerViewHelper

data class ListItem(val title: String, val description: String?, val icon: Drawable?, val tag : String? = null, val function: ((isChecked : Boolean) -> Unit)? = null, var isChecked : Boolean = false){
}

class ListAdapter (val bottomSheet : ItemListDialogFragment, private val listItem : ArrayList<ListItem>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        if(bottomSheet.alreadyChecked != null) {
            for (item in listItem) {
                item.isChecked = bottomSheet.alreadyChecked.contains(item.tag)
            }
        }
        else{
            for (item in listItem) {
                item.isChecked = false
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        RecyclerViewHelper.roundRecyclerItemsVertically(holder.itemView, position, itemCount)

        val currentItem = listItem[position]
        holder.title.text = currentItem.title



        if (bottomSheet.getValuesOnFinish != null) {
            holder.checkBox.isChecked = currentItem.isChecked
            holder.checkBox.visibility = VISIBLE
            holder.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                currentItem.isChecked = isChecked
            }

        } else {
            holder.checkBox.visibility = GONE
        }


        holder.itemView.setOnClickListener {

            if(bottomSheet.getValuesOnFinish != null) {
                holder.checkBox.toggle()
            }
            else{
                bottomSheet.dismiss()
            }
            currentItem.function?.invoke(holder.checkBox.isChecked)
        }

        if(!currentItem.description.isNullOrEmpty()) {
            holder.description.visibility = VISIBLE
            holder.description.text = currentItem.description
        }

        if(currentItem.icon != null) {
            holder.icon.visibility = VISIBLE
            holder.icon.setImageDrawable(currentItem.icon)
        }
        else{
            holder.icon.visibility = GONE
        }
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.simple_list_text_title)
        val description : TextView = itemView.findViewById(R.id.simple_list_text_description)
        val icon : ImageView = itemView.findViewById(R.id.simple_list_image_icon)
        val checkBox : CheckBox = itemView.findViewById(R.id.check_box)
    }

}