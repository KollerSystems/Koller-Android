package com.norbert.koller.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.data.DefaultDayTimes
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class GateRecyclerAdapter(chipGroup: ChipGroup? = null, chips: List<Chip> = listOf()) :BaseRecycleAdapter(chipGroup, chips){

    override fun createViewHolder(view: View): RecyclerView.ViewHolder {
        return CrossingViewHolder(view)
    }

    fun late(timeInMillis : Long) : String{

        val calendar = Calendar.getInstance()
        calendar.time = Date(timeInMillis)
        val hourOfDay : Int = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes : Int = calendar.get(Calendar.MINUTE)

        val minutesOfArrive : Int = hourOfDay * 60 + minutes

        val delay : Int = minutesOfArrive - DefaultDayTimes.instance.dayTimeGoInside
        if(delay > 0){
            return "Késtél"
        }
        return "Időben"
    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: Any, position: Int) {

        when (holder.itemViewType) {
            0 -> {
                item as CrossingData
                holder as CrossingViewHolder



                val icon : Int = if(item.Direction == 0.toByte()){
                    R.drawable.in_
                } else{
                    R.drawable.out
                }
                holder.iconLeft.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, icon))

                holder.title.text = SimpleDateFormat(DateTimeHelper.timeFormat).format(item.Time)
                holder.description.text = late(item.Time.time)

                holder.itemView.setOnClickListener {

                }
            }
            else -> {
                item as String
                holder as DateViewHolder

                holder.text.text = item

            }
        }
    }

    class CrossingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}