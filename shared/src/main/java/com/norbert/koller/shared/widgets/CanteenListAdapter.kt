package com.norbert.koller.shared.widgets

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.norbert.koller.shared.R
import com.norbert.koller.shared.data.CanteenData
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import com.norbert.koller.shared.recycleradapters.CanteenRecyclerAdapter
import java.util.Calendar


class CanteenListRecyclerAdapter(private val context: Context, private val canteenDataList: java.util.ArrayList<CanteenData>) : RemoteViewsService.RemoteViewsFactory {

    override fun onCreate() {

    }

    var indexOfDayDifference = canteenDataList.size

    override fun onDataSetChanged() {
        for (i in 1 until canteenDataList.size){
            if(checkForDayDifference(i)){
                indexOfDayDifference = i
                break
            }

        }

        Log.d("TESTWIDGET", indexOfDayDifference.toString())
    }

    override fun onDestroy() {

    }

    fun checkForDayDifference(i : Int) : Boolean{

        val calendar = Calendar.getInstance()
        calendar.time = canteenDataList[i-1].date
        val dayBefore = calendar.get(Calendar.DAY_OF_YEAR)
        calendar.time = canteenDataList[i].date
        val dayCurrent = calendar.get(Calendar.DAY_OF_YEAR)
        return dayBefore != dayCurrent
    }

    override fun getCount(): Int {
        if(canteenDataList.isNotEmpty()) {
            var extraDays = 1
            if(indexOfDayDifference < canteenDataList.size){
                extraDays = 2
            }
            return canteenDataList.size + extraDays
        }
        else{
            return 0
        }
    }

    fun textView(i : Int) : RemoteViews {
        val dateViews = RemoteViews(context.packageName, R.layout.text_view_date)

        dateViews.setTextViewText(R.id.text, canteenDataList[i].date.formatDate(DateTimeHelper.monthDay).capitalize())

        return dateViews
    }

    fun cardView(i : Int, background : Int = R.drawable.square) : RemoteViews{
        val resources = CanteenRecyclerAdapter.getCategoryResources(context, canteenDataList[i].category)
        val canteenViews = RemoteViews(context.packageName, R.layout.widget_item_canteen)
        canteenViews.setTextViewText(R.id.text_category, resources.first)
        canteenViews.setImageViewResource(R.id.image_icon, resources.second)
        canteenViews.setTextViewText(R.id.text_time, canteenDataList[i].time)
        canteenViews.setTextViewText(R.id.text_title, canteenDataList[i].foodName)
        canteenViews.setInt(R.id.root, "setBackgroundResource", background);

        val extras = Bundle()
        extras.putInt("position", i)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)

        canteenViews.setOnClickFillInIntent(R.id.root, fillInIntent)

        return canteenViews
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = if(position == 0){
            textView(0)
        }
        else{
            if(position-1 == indexOfDayDifference){
                textView(position)
            }
            else{
                var innerPos = position - 1
                var innerLength = indexOfDayDifference
                var itemPosition = position - 1

                if(position-1 > indexOfDayDifference){
                    innerPos = position -2 - indexOfDayDifference
                    innerLength = canteenDataList.size - indexOfDayDifference
                    itemPosition--
                }



                if (innerLength == 1) {

                    cardView(itemPosition, R.drawable.card_middle)

                }
                else if(innerPos == 0){

                    cardView(itemPosition, R.drawable.card_top)
                }
                else if (innerPos == innerLength-1){

                    cardView(itemPosition, R.drawable.card_bottom)
                }
                else{
                    cardView(itemPosition, R.drawable.square)
                }



            }
        }

        return views
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }


}