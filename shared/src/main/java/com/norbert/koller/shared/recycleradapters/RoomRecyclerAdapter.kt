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


class RoomRecyclerAdapter() : ApiRecyclerAdapterWithTransition(){

    override fun getViewType(): Int {
        return R.layout.item_large
    }

    override fun getDataTag(): String {
        return "room"
    }

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return RoomsViewHolder(view)
    }

    fun getShortName(name : String) : String{
        val nameParts : List<String> = name.split(" ")
        return nameParts[0][0] + ". " + nameParts[1]
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        super.onBindViewHolder(holder, item, position)

        holder as RoomsViewHolder

        item as RoomData

        holder.iconLeft.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, R.drawable.bed))
        holder.title.text = "${item.rid} • ${item.group!!.group}"

        holder.description.isSingleLine = false

        if(item.residents !=null ) {

            var desc : String = ""
            for (i in 0 until item.residents!!.size){

                desc += getShortName(item.residents!![i].name!!) + ", "
            }
            desc.trimEnd()

            holder.description.text = desc
        }

        holder.itemView.setOnClickListener {

            val fragment = ApplicationManager.roomFragment(item.rid)
            (holder.itemView.context as MainActivity).addFragmentWithTransition(fragment, holder.itemView, getTransitionName(item.getMainID()))

        }
    }


    class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}