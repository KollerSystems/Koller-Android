package com.example.teacher.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.shared.MyApplication
import com.example.teacher.R
import com.example.shared.data.RoomData
import com.example.shared.data.RoomOrderConditionsBase
import com.example.shared.data.RoomOrderConditionsBoolean
import com.example.shared.data.RoomOrderConditionsInt
import com.example.shared.data.RoomOrderData
import com.example.shared.data.UserData
import com.example.shared.fragments.UserFragment
import com.example.shared.recycleradapter.GateRecyclerAdapter
import com.example.shared.recycleradapter.UserPreviewRecyclerAdapter
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.Chip
import com.google.android.material.slider.Slider
import org.w3c.dom.Text

class RoomRateRecyclerAdapter (private var roomOrderConditionsData : ArrayList<RoomOrderConditionsBase>) :  RecyclerView.Adapter<ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        when (viewType){
            R.layout.view_rating_slider -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_rating_slider, parent, false)
                return RoomOrderConditionsIntViewHolder(view)
            }
            R.layout.view_rating_checkbox ->{
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_rating_checkbox, parent, false)
                return RoomOrderConditionsBooleanViewHolder(view)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        when (getItemViewType(position)){
            R.layout.view_rating_rv_footer -> {

            }
            else->{
                holder as RoomOrderConditionsBaseViewHolder

                val currentItem = roomOrderConditionsData[position]

                val context = holder.itemView.context
                if (roomOrderConditionsData.size == 1) {

                    MyApplication.roundCard(context, holder.itemView)

                }
                else if(position == 0){

                    MyApplication.roundCardTop(context, holder.itemView)
                }
                else{
                    MyApplication.deroundCardVertical(context, holder.itemView)
                }



                holder.title.text = currentItem.title
                holder.icon.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, currentItem.icon))
            }
        }


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
                when (roomOrderConditionsData[position]) {
                    is RoomOrderConditionsInt -> R.layout.view_rating_slider
                    is RoomOrderConditionsBoolean -> R.layout.view_rating_checkbox
                    else -> throw Exception("Idiot")
                }
            }
        }
    }

    class RoomOrderConditionsIntViewHolder(itemView: View) : RoomOrderConditionsBaseViewHolder(itemView)
    {
        val chipSlider : Chip = itemView.findViewById(R.id.chip_slider)
        val slider : Slider = itemView.findViewById(R.id.slider)
    }

    class RoomOrderConditionsBooleanViewHolder(itemView: View) : RoomOrderConditionsBaseViewHolder(itemView)
    {
        val chipYes : Chip = itemView.findViewById(R.id.chip_yes)
        val chipNo : Chip = itemView.findViewById(R.id.chip_no)
    }

    class RoomOrderConditionsFooter(itemView: View) : ViewHolder(itemView)
    {

    }

    abstract class RoomOrderConditionsBaseViewHolder(itemView: View) : ViewHolder(itemView)
    {
        val icon : ImageView = itemView.findViewById(R.id.image_icon)
        val title : TextView = itemView.findViewById(R.id.text_title)
        val chipNone : Chip = itemView.findViewById(R.id.chip_none)
    }

}