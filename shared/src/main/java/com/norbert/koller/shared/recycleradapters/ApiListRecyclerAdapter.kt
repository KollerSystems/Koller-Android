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
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.databinding.ItemCheckBoxBinding
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.fragments.bottomsheet.ListBsdfFragment
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.recycleradapters.ListAdapter.ListViewHolder

class ApiListAdapter (val bottomSheet : ListBsdfFragment) : EditableApiRecyclerAdapter() {
    override fun onClick(holder: RecyclerView.ViewHolder, item: BaseData) {

    }

    override fun getIconHolder(holder: RecyclerView.ViewHolder): ViewGroup {
        return holder.itemView as ViewGroup
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

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val binding = ItemCheckBoxBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {

        holder as ListViewHolder

        val margin = holder.itemView.resources.getDimensionPixelSize(R.dimen.card_margin)
        (holder.itemView.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(0,margin,0,margin)

        val currentItem = bottomSheet.viewModel.list.value!![position]


        if (bottomSheet.toggleList()) {
            holder.itemBinding.checkBox.setOnCheckedChangeListener(null)
            holder.itemBinding.checkBox.isChecked = currentItem.isChecked
            holder.itemBinding.checkBox.visibility = VISIBLE
            holder.itemBinding.checkBox.setOnCheckedChangeListener{ _, isChecked ->
                currentItem.isChecked = isChecked
            }

        } else {
            holder.itemBinding.checkBox.visibility = GONE
        }


        holder.itemView.setOnClickListener {

            if(bottomSheet.toggleList()) {
                holder.itemBinding.checkBox.toggle()
            }
            else{
                bottomSheet.dismiss()
            }
            currentItem.function?.invoke(holder.itemBinding.checkBox.isChecked)
        }

        if(!bottomSheet.viewModel.collapseText) {
            holder.itemBinding.textTitle.text = currentItem.title
            if (!currentItem.description.isNullOrEmpty()) {
                holder.itemBinding.textDescription.visibility = VISIBLE
                holder.itemBinding.textDescription.text = currentItem.description
            }
        }
        else{
            holder.itemBinding.textTitle.text = "${currentItem.title} • ${currentItem.description}"
        }

        if(currentItem.icon != null) {
            holder.itemBinding.imgIcon.visibility = VISIBLE
            holder.itemBinding.imgIcon.setImageDrawable(currentItem.icon)
        }
        else{
            holder.itemBinding.imgIcon.visibility = GONE
        }
    }
}