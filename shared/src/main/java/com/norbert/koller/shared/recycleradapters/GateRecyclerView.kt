package com.norbert.koller.shared.recycleradapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.ChipGroup
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.data.DefaultDayTimes
import com.google.android.material.imageview.ShapeableImageView
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class GateRecyclerAdapter(chipGroupSort: ChipGroup? = null, chipGroupFilter: ChipGroup? = null, ) :BaseRecycleAdapter(chipGroupSort, chipGroupFilter){

    override fun getViewType(): Int {
        return R.layout.view_descriptive_icon_item
    }

    override fun getDataTag(): String {
        return "exits_and_entrances"
    }

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



                val icon : Int
                val iconLocDesc : Int

                if(item.direction == 0.toByte()){
                    icon = R.drawable.in_
                    iconLocDesc = R.string.in_
                } else{
                    icon = R.drawable.out
                    iconLocDesc = R.string.out
                }
                holder.iconLeft.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, icon))
                holder.iconDesc.text = holder.itemView.context.getString(iconLocDesc)
                holder.title.text = SimpleDateFormat(DateTimeHelper.timeFormat).format(item.time)
                holder.description.text = late(item.time.time)

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
        val iconDesc : TextView = itemView.findViewById(R.id.text_iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }


}