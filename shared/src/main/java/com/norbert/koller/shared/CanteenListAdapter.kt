package com.norbert.koller.shared

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.norbert.koller.shared.data.CanteenData

class CanteenListAdapter(private val context: Context, private val canteenDataList: java.util.ArrayList<CanteenData>) : BaseAdapter() {

    private lateinit var category: TextView
    private lateinit var time: TextView
    private lateinit var title: TextView
    private lateinit var icon: ImageView

    override fun getCount(): Int {
        return 3
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        convertView = LayoutInflater.from(context).inflate(R.layout.widget_canteen, parent, false)
        category = convertView.findViewById(R.id.text_category)
        time = convertView.findViewById(R.id.text_time)
        title = convertView.findViewById(R.id.text_title)
        category.text = canteenDataList[position].category.toString()
        time.text = canteenDataList[position].time
        title.text = canteenDataList[position].foodName
        return convertView
    }

}