package com.norbert.koller.shared.recycleradapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.fragments.RoomFragment
import com.google.android.material.imageview.ShapeableImageView


class RoomRecyclerAdapter : BaseRecycleAdapter(){

    override fun serverErrorPopup(view: View): RecyclerView.ViewHolder {
        return RoomsViewHolder(view)
    }

    fun getShortName(name : String) : String{
        val nameParts : List<String> = name.split(" ")
        return nameParts[0][0] + ". " + nameParts[1]
    }

    override fun onBindserverErrorPopup(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        holder as RoomsViewHolder

        item as RoomData

        holder.iconLeft.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bed))
        holder.title.text = item.RID.toString()

        if(item.residents !=null ) {

            var desc = getShortName(item.residents!![0].Name!!)
            for (i in 1 until item.residents!!.size){


                desc += ", " + getShortName(item.residents!![i].Name!!)
            }
            holder.description.text = desc
        }

        holder.itemView.setOnClickListener {

            RoomFragment.open(holder.itemView.context, item.RID)

        }
    }


    class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}