package com.norbert.koller.shared.recycleradapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.data.UserData
import com.norbert.koller.shared.databinding.ItemCanteenBinding
import com.norbert.koller.shared.databinding.ItemUserBinding
import com.norbert.koller.shared.fragments.bottomsheet.CanteenBsdfFragment
import com.norbert.koller.shared.helpers.RecyclerViewHelper
import java.util.Date

class CanteenRecyclerAdapter() : ApiRecyclerAdapter() {

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ViewHolder(ItemCanteenBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {


        holder as ViewHolder

        item as UserData

        holder.itemBinding.content.textTitle.text = item.name
        holder.itemBinding.content.textTime.text = item.createDescription()

        val resources = getCategoryResources(holder.itemBinding.root.context, 1)

        holder.itemBinding.content.textCategory.text = resources.first
        holder.itemBinding.content.imageIcon.setImageDrawable(AppCompatResources.getDrawable(holder.itemBinding.root.context, resources.second))

        holder.itemView.setOnClickListener{
            val dialog = CanteenBsdfFragment(CanteenData(1,0, "6:00 - 8:45", holder.itemView.context.getString(R.string.fake_breakfast_1), Date()))
            dialog.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, CanteenBsdfFragment.TAG)
        }
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