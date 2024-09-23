package com.norbert.koller.shared.recycleradapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.BaseData
import com.norbert.koller.shared.data.CrossingData
import com.norbert.koller.shared.data.DefaultDayTimes
import com.norbert.koller.shared.databinding.ItemDescriptiveIconBinding
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import java.util.Calendar
import java.util.Date


class GateRecyclerAdapter() :ApiRecyclerAdapter(){

    override fun setItemViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CrossingViewHolder(ItemDescriptiveIconBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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



    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder, item: BaseData, position: Int) {



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
        holder.itemBinding.imageIcon.setImageDrawable(AppCompatResources.getDrawable(holder.itemView.context, icon))
        holder.itemBinding.textIconDescription.text = holder.itemView.context.getString(iconLocDesc)
        holder.itemBinding.textTitle.text = item.time.formatDate(DateTimeHelper.timeFormat)
        holder.itemBinding.textDescription.text = late(item.time.time)
    }

    class CrossingViewHolder(val itemBinding: ItemDescriptiveIconBinding) : RecyclerView.ViewHolder(itemBinding.root)


}