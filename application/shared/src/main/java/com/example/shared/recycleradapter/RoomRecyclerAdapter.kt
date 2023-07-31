package com.example.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.R
import com.example.shared.data.RoomData
import com.example.shared.data.TodayData
import com.example.shared.data.UserData
import com.example.shared.fragments.RoomFragment
import com.example.shared.navigateWithDefaultAnimation
import com.google.android.material.imageview.ShapeableImageView


class RoomRecyclerAdapter() : BaseRecycleAdapter(){

    override fun CustomViewHolder(view: View): RecyclerView.ViewHolder {
        return RoomsViewHolder(view)
    }

    fun getShortName(name : String) : String{
        val nameParts : List<String> = name.split(" ")
        return nameParts[0][0] + ". " + nameParts[1]
    }

    override fun onBindCustomViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        holder as RoomsViewHolder

        item as RoomData

        holder.iconLeft.setImageDrawable(holder.itemView.context.getDrawable(R.drawable.bed))
        holder.title.text = item.RID.toString()

        if(item.residents !=null ) {

            var desc = getShortName(item.residents!![0].Name!!)
            for (i in 1 until item.residents!!.size){


                desc += ", " + getShortName(item.residents!![i].Name!!)
            }
            holder.description.text = desc
        }

        holder.itemView.setOnClickListener {

            RoomFragment.toGet = item.RID
            holder.itemView.findNavController().navigateWithDefaultAnimation(R.id.action_roomsFragment_to_roomFragment)

        }
    }


    class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

}