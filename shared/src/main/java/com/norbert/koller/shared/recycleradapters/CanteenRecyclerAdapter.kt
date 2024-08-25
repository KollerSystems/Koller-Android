package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.graphics.drawable.Drawable
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

        val resources = getCategoryResources(context, currentItem.category)

        holder.itemBinding.content.textCategory.text = resources.first
        holder.itemBinding.content.imageIcon.setImageDrawable(AppCompatResources.getDrawable(context, resources.second))

        holder.itemView.setOnClickListener{
            val dialog = CanteenBsdFragment(currentItem)
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, CanteenBsdFragment.TAG)
        }
    }

    override fun getItemCount(): Int {
        return canteenList.size
    }

    class ViewHolder(val itemBinding: ItemCanteenBinding) : RecyclerView.ViewHolder(itemBinding.root)

    companion object{
        fun getCategoryResources(context : Context, category : Int) : Pair<String, Int>{
            return when(category){
                0 ->{
                    Pair(context.getString(R.string.breakfast), R.drawable.breakfast)
                }
                1 ->{
                    Pair(context.getString(R.string.lunch), R.drawable.lunch)
                }
                else ->{
                    Pair(context.getString(R.string.dinner), R.drawable.dinner)
                }
            }
        }
    }
}