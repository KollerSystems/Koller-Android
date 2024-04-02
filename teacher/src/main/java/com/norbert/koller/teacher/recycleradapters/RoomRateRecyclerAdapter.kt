package com.norbert.koller.teacher.recycleradapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.norbert.koller.teacher.R
import com.norbert.koller.shared.data.RoomOrderConditionsBase
import com.norbert.koller.shared.data.RoomOrderConditionsBoolean
import com.norbert.koller.shared.data.RoomOrderConditionsInt
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.helpers.RecyclerViewHelper

class RoomRateRecyclerAdapter (private var roomOrderConditionsData : ArrayList<RoomOrderConditionsBase>) :  RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType){
            R.layout.view_rating ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_rating, parent, false)
                return RoomOrderConditionsViewHolder(view)
            }
            R.layout.view_rating_rv_footer ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_rating_rv_footer, parent, false)
                return RoomOrderConditionsFooter(view)
            }
            else ->{
                throw Exception("Idiot")
            }
        }



    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        when (getItemViewType(position)){
            R.layout.view_rating_rv_footer -> {

            }
            R.layout.view_rating -> {

                setupRatingView(holder, position)

            }
        }


    }


    fun setupRatingView(holder: ViewHolder, position: Int){
        holder as RoomOrderConditionsViewHolder

        val currentItem = roomOrderConditionsData[position]

        val context = holder.itemView.context
        if (roomOrderConditionsData.size == 1) {

            RecyclerViewHelper.roundCard(holder.itemView)

        }
        else if(position == 0){

            RecyclerViewHelper.roundCardTop(holder.itemView)
        }
        else{
            RecyclerViewHelper.deroundCardVertical(holder.itemView)
        }



        holder.title.text = currentItem.title
        holder.icon.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, currentItem.icon))
    }

    override fun getItemCount(): Int {
        return roomOrderConditionsData.size + 1
    }

    override fun getItemViewType(position: Int): Int {

        return when (position) {
            itemCount - 1 ->
            {
                R.layout.view_rating_rv_footer
            }
            else -> {

                   R.layout.view_rating

            }
        }
    }



    class RoomOrderConditionsViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val chipGroup : ChipGroup = itemView.findViewById(R.id.chip_group)
        val icon : ImageView = itemView.findViewById(R.id.image_icon)
        val title : TextView = itemView.findViewById(R.id.text_title)
        val chipYes : Chip = itemView.findViewById(R.id.chip_yes)
        val chipNo : Chip = itemView.findViewById(R.id.chip_no)
    }

    class RoomOrderConditionsFooter(itemView: View) : ViewHolder(itemView)


}