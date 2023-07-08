package com.example.shared.recycleradapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.marginTop
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shared.MyApplication
import com.example.shared.R
import com.example.shared.data.CrossingData
import com.example.shared.data.DefaultDayTimes
import com.google.android.material.imageview.ShapeableImageView
import java.util.Calendar
import java.util.Date


class GateRecyclerAdapter (private var crossingList : ArrayList<Any>, var context : Context, var date : TextView) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.setOnScrollChangeListener(){ view: View, i: Int, i1: Int, i2: Int, i3: Int ->

            val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
            val firstVisibleItemIndex : Int = layoutManager.findFirstVisibleItemPosition()
            val firstCompletelyVisibleIndex : Int = layoutManager.findFirstCompletelyVisibleItemPosition()

            if(firstVisibleItemIndex != -1) {
                val firstData = crossingList[firstVisibleItemIndex]
                if (firstData is String) {
                    date.text = firstData
                }
                else{
                    firstData as CrossingData
                    date.text = MyApplication.simpleLocalMonthDay.format(firstData.Time).capitalize()
                }
            }

            if(firstCompletelyVisibleIndex != -1){
                val firstData = crossingList[firstCompletelyVisibleIndex]
                if(firstData is String){
                    val firstCompletelyVisibleItem : View = layoutManager.getChildAt(1)!!
                    val top = firstCompletelyVisibleItem.top
                    val y = top - firstCompletelyVisibleItem.marginTop
                    val fullFirstViewHeight = firstCompletelyVisibleItem.height + firstCompletelyVisibleItem.marginTop * 2
                    if(y < fullFirstViewHeight){
                        date.translationY = (y - fullFirstViewHeight).toFloat()
                    }
                }
                else{
                    date.translationY = 0f
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            0 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_panel, parent, false)
                return CrossingViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.view_date, parent, false)
                return DateViewHolder(view)
            }
        }
    }

    fun late(timeInMillis : Long) : String{

        val calendar = Calendar.getInstance()
        calendar.time = Date(timeInMillis)
        var hourOfDay : Int = calendar.get(Calendar.HOUR_OF_DAY)
        var minutes : Int = calendar.get(Calendar.MINUTE)

        val minutesOfArrive : Int = hourOfDay * 60 + minutes

        val delay : Int = minutesOfArrive - DefaultDayTimes.instance.dayTimeGoInside
        if(delay > 0){
            return "Késtél"
        }
        return "Időben"
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = crossingList[position]

        when (holder.itemViewType) {
            0 -> {
                currentItem as CrossingData
                holder as CrossingViewHolder

                MyApplication.roundRecyclerItemsVerticallyWithSeparator(context, holder.itemView, position, crossingList)

                val icon : Int = if(currentItem.Direction == 0.toByte()){
                    R.drawable.in_
                } else{
                    R.drawable.out
                }
                holder.iconLeft.setImageDrawable(AppCompatResources.getDrawable(context, icon))

                holder.title.text = MyApplication.simpleTimeFormat.format(currentItem.Time)
                holder.description.text = late(currentItem.Time.time)

                holder.itemView.setOnClickListener {

                }
            }
            else -> {
                currentItem as String
                holder as DateViewHolder

                holder.text.text = currentItem

            }
        }


    }

    override fun getItemCount(): Int {
        return crossingList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (crossingList[position] is CrossingData){
            0
        }
        else{
            1
        }
    }

    class CrossingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val iconLeft : ShapeableImageView = itemView.findViewById(R.id.iv_icon)
        val title : TextView = itemView.findViewById(R.id.text_text)
        val description : TextView = itemView.findViewById(R.id.text_description)
    }

    class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val text : TextView = itemView.findViewById(R.id.text_view)
    }

}