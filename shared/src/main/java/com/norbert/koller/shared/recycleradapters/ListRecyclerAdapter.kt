package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewGroup.MarginLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.ListItem
import com.norbert.koller.shared.databinding.ItemCheckBoxBinding
import com.norbert.koller.shared.fragments.bottomsheet.list.ListBsdfFragment
import com.norbert.koller.shared.fragments.bottomsheet.list.ListCardBsdfFragment
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.viewmodels.ListBsdfFragmentCardViewModel

abstract class ListRecyclerAdapter(val bottomSheet : ListCardBsdfFragment) : RecyclerView.Adapter<ListRecyclerAdapter.ListViewHolder>() {

    var itemsCopy : ArrayList<ListItem>? = null

    fun filter(text: String) {
        if(itemsCopy == null){
            itemsCopy = ArrayList(bottomSheet.getCardViewModel().list.value!!)
        }
        bottomSheet.getCardViewModel().list.value!!.clear()
        if (text.isEmpty()) {
            bottomSheet.getCardViewModel().list.value!!.addAll(itemsCopy!!)
        } else {
            for (item in itemsCopy!!) {
                Log.d("ASDASD",text + " ::: ${item.title} ${item.description.toString()}")
                val regexSearch = Regex(ApplicationManager.searchWithRegex(text), RegexOption.IGNORE_CASE)
                if (regexSearch.containsMatchIn("${item.title} ${item.description.toString()}")) {
                    bottomSheet.getCardViewModel().list.value!!.add(item)
                }
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCheckBoxBinding.inflate(LayoutInflater.from(parent.context), null, false)
        return ListViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        val margin = holder.itemView.resources.getDimensionPixelSize(R.dimen.card_margin)
        val params = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        params.setMargins(0,margin,0,margin)
        holder.itemView.layoutParams = params

        val currentItem = bottomSheet.getCardViewModel().list.value!![position]

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

    override fun getItemCount(): Int {
        return bottomSheet.getCardViewModel().list.value!!.size
    }

    class ListViewHolder(var itemBinding: ItemCheckBoxBinding) : RecyclerView.ViewHolder(itemBinding.root)

}