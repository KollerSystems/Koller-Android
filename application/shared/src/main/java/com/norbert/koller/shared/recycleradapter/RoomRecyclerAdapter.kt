package com.norbert.koller.shared.recycleradapter

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomData
import com.norbert.koller.shared.fragments.RoomFragment
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.activities.MainActivity


class RoomRecyclerAdapter(chipGroup: ChipGroup?) : BaseRecycleAdapter(chipGroup){

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

        if(item.Residents !=null ) {

            var desc = "${item.Residents!![0].Group} • ${getShortName(item.Residents!![0].Name!!)}"
            for (i in 1 until item.Residents!!.size){


                desc += ", " + getShortName(item.Residents!![i].Name!!)
            }
            holder.description.text = desc
        }

        holder.itemView.setOnClickListener {

            (holder.itemView.context as MainActivity).changeFragment(MyApplication.roomFragment(item.RID))

        }
    }


    class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}