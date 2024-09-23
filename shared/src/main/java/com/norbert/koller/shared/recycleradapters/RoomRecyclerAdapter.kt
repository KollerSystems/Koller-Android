package com.norbert.koller.shared.recycleradapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomData
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class RoomRecyclerAdapter() : EditableApiRecyclerAdapter(){

    fun getShortName(name : String) : String{
        val nameParts : List<String> = name.split(" ")
        return nameParts[0][0] + ".\u00A0" + nameParts[1]
    }

    override fun onClick(holder: RecyclerView.ViewHolder, item: BaseData) {
        val fragment = ApplicationManager.roomFragment()
        val bundle = Bundle()
        bundle.putInt("id", item.getMainID())
        fragment.arguments = bundle
        (holder.itemView.context as MainActivity).addFragmentWithTransition(fragment, holder.itemView, getTransitionName(item.getMainID()))

    }

    @SuppressLint("SetTextI18n")
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {

        super.onBindItemViewHolder(holder, item, position)

        holder as RecyclerViewHelper.ItemViewHolder

        item as RoomData

        holder.itemBinding.imageIcon.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bed))
        holder.itemBinding.textTitle.text = "${item.rid} • ${item.group!!.group}"

        holder.itemBinding.textDescription.isSingleLine = false

        if(item.residents !=null ) {

            var desc = ""
            for (i in 0 until item.residents!!.size){

                desc += getShortName(item.residents!![i].name!!) + ", "
            }
            desc = desc.dropLast(2)

            holder.itemBinding.textDescription.text = desc
        }
        else{
            holder.itemBinding.textDescription.text = holder.itemView.context.getString(R.string.empty)
        }
    }

    override fun getIconHolder(holder: RecyclerView.ViewHolder): ViewGroup {
        holder as RecyclerViewHelper.ItemViewHolder
        return holder.itemBinding.flIconHolder
    }
}