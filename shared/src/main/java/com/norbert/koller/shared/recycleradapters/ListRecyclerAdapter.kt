package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
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
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdFragment
import com.norbert.koller.shared.managers.ApplicationManager

data class ListItem(val title: String, val description: String? = null, val icon: Drawable? = null, val tag : String? = null, val function: ((isChecked : Boolean) -> Unit)? = null, var isChecked : Boolean = false){
}

class ListAdapter (val bottomSheet : ListBsdFragment) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)


    }

    var itemsCopy : ArrayList<ListItem>? = null

    fun filter(text: String) {
        if(itemsCopy == null){
            itemsCopy = ArrayList(bottomSheet.viewModel.list.value!!)
        }
        bottomSheet.viewModel.list.value!!.clear()
        if (text.isEmpty()) {
            bottomSheet.viewModel.list.value!!.addAll(itemsCopy!!)
        } else {
            for (item in itemsCopy!!) {
                Log.d("ASDASD",text + " ::: ${item.title} ${item.description.toString()}")
                val regexSearch = Regex(ApplicationManager.searchWithRegex(text), RegexOption.IGNORE_CASE)
                if (regexSearch.containsMatchIn("${item.title} ${item.description.toString()}")) {
                    bottomSheet.viewModel.list.value!!.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_check_box, parent, false)
        return ListViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val margin = holder.itemView.resources.getDimensionPixelSize(R.dimen.card_margin)
        (holder.itemView.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,margin,0,margin)

        val currentItem = bottomSheet.viewModel.list.value!![position]


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

        if(!bottomSheet.viewModel.collapseText) {
            holder.title.text = currentItem.title
            if (!currentItem.description.isNullOrEmpty()) {
                holder.description.visibility = VISIBLE
                holder.description.text = currentItem.description
            }
        }
        else{
            holder.title.text = "${currentItem.title} • ${currentItem.description}"
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
        return bottomSheet.viewModel.list.value!!.size
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val title : TextView = itemView.findViewById(R.id.simple_list_text_title)
        val description : TextView = itemView.findViewById(R.id.simple_list_text_description)
        val icon : ImageView = itemView.findViewById(R.id.simple_list_image_icon)
        val checkBox : CheckBox = itemView.findViewById(R.id.check_box)
    }

}