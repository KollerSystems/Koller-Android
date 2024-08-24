package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.databinding.ItemCanteenBinding
import com.norbert.koller.shared.fragments.bottomsheet.CanteenBsdFragment
import com.norbert.koller.shared.fragments.bottomsheet.LessonsBsdFragment
import com.norbert.koller.shared.helpers.RecyclerViewHelper

class CanteenRecyclerAdapter (private val canteenList : ArrayList<CanteenData>) : RecyclerView.Adapter<CanteenRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCanteenBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        RecyclerViewHelper.roundRecyclerItemsVertically(holder.itemView, position, canteenList.size)

        val currentItem = canteenList[position]
        holder.itemBinding.content.textTime.text = currentItem.time
        holder.itemBinding.content.textTitle.text = currentItem.foodName
        val context = holder.itemView.context
        when(currentItem.category){
            0 ->{
                holder.itemBinding.content.textCategory.text = context.getString(R.string.breakfast)
                holder.itemBinding.content.imageIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.breakfast))
            }
            1 ->{
                holder.itemBinding.content.textCategory.text = context.getString(R.string.lunch)
                holder.itemBinding.content.imageIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.lunch))
            }
            2 ->{
                holder.itemBinding.content.textCategory.text = context.getString(R.string.dinner)
                holder.itemBinding.content.imageIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.dinner))
            }
        }

        holder.itemView.setOnClickListener{
            val dialog = CanteenBsdFragment(currentItem)
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, CanteenBsdFragment.TAG)
        }
    }

    override fun getItemCount(): Int {
        return canteenList.size
    }

    class ViewHolder(val itemBinding: ItemCanteenBinding) : RecyclerView.ViewHolder(itemBinding.root)
}