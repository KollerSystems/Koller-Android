package com.norbert.koller.shared.recycleradapters

import android.graphics.drawable.Drawable
import android.util.Log
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
import com.norbert.koller.shared.fragments.bottomsheet.ItemListDialogFragmentBase
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.ApplicationManager
import java.util.Locale

data class ListItem(val title: String, val description: String? = null, val icon: Drawable? = null, val tag : String? = null, val function: ((isChecked : Boolean) -> Unit)? = null, var isChecked : Boolean = false){
}

class ListAdapter (val bottomSheet : ItemListDialogFragmentBase, private val listItem : ArrayList<ListItem>) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {


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

    var itemsCopy : ArrayList<ListItem>? = null

    fun filter(text: String) {
        if(itemsCopy == null){
            itemsCopy = ArrayList(listItem)
        }
        listItem.clear()
        if (text.isEmpty()) {
            listItem.addAll(itemsCopy!!)
        } else {
            for (item in itemsCopy!!) {
                Log.d("ASDASD",text)
                if (Regex(ApplicationManager.searchWithRegex(text)).matches(item.title)) {
                    listItem.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        RecyclerViewHelper.roundRecyclerItemsVertically(holder.itemView, position, itemCount)

        val currentItem = listItem[position]
        holder.title.text = currentItem.title


        if (bottomSheet.toggleList()) {
            holder.checkBox.setOnCheckedChangeListener(null)
            holder.checkBox.isChecked = currentItem.isChecked
            holder.checkBox.visibility = VISIBLE
            holder.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                currentItem.isChecked = isChecked
            }

        } else {
            holder.checkBox.visibility = GONE
        }


        holder.itemView.setOnClickListener {

            if(bottomSheet.toggleList()) {
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