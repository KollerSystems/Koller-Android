package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.RoomData
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.managers.MyApplication
import com.norbert.koller.shared.activities.MainActivity


class RoomRecyclerAdapter(chipGroup: ChipGroup? = null, chipGroupFilter: ChipGroup? = null) : BaseRecycleAdapter(chipGroup, chipGroupFilter){

    override fun getViewType(): Int {
        return R.layout.view_item
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return RoomsViewHolder(view)
    }

    fun getShortName(name : String) : String{
        val nameParts : List<String> = name.split(" ")
        return nameParts[0][0] + ". " + nameParts[1]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        holder as RoomsViewHolder

        item as RoomData

        holder.iconLeft.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bed))
        holder.title.text = "${item.RID} • ${item.Group}"

        holder.description.isSingleLine = false

        if(item.Residents !=null ) {

            var desc : String = ""
            for (i in 0 until item.Residents!!.size){

                desc += getShortName(item.Residents!![i].Name!!) + ", "
            }
            desc.trimEnd()

            holder.description.text = desc
        }

        holder.itemView.setOnClickListener {

            val fragment = MyApplication.roomFragment(item.RID)
            (holder.itemView.context as MainActivity).addFragment(fragment)

        }
    }


    class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}