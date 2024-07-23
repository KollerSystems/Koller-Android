package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomData
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.managers.ApplicationManager
import com.norbert.koller.shared.activities.MainActivity
import com.norbert.koller.shared.helpers.RecyclerViewHelper


class RoomRecyclerAdapter() : ApiRecyclerAdapterWithTransition(){

    override fun getDataTag(): String {
        return "room"
    }

    fun getShortName(name : String) : String{
        val nameParts : List<String> = name.split(" ")
        return nameParts[0][0] + ". " + nameParts[1]
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        super.onBindItemViewHolder(holder, item, position)

        holder as RecyclerViewHelper.ItemViewHolder

        item as RoomData

        holder.itemBinding.imageIcon.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bed))
        holder.itemBinding.textTitle.text = "${item.rid} • ${item.group!!.group}"

        holder.itemBinding.textDescription.isSingleLine = false

        if(item.residents !=null ) {

            var desc : String = ""
            for (i in 0 until item.residents!!.size){

                desc += getShortName(item.residents!![i].name!!) + ", "
            }
            desc.trimEnd()

            holder.itemBinding.textDescription.text = desc
        }

        holder.itemView.setOnClickListener {

            val fragment = ApplicationManager.roomFragment(item.rid)
            (holder.itemView.context as MainActivity).addFragmentWithTransition(fragment, holder.itemView, getTransitionName(item.getMainID()))

        }
    }
}