package com.norbert.koller.shared.data

import android.content.Context
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CanteenData (var ID : Int, var Category : String, var Time : String, var FoodName : String, val Date: Date) : BaseData() {
    override fun diffrentDecider(context: Context): String {
        return SimpleDateFormat(DateTimeHelper.monthDay, Locale.getDefault()).format(Date)
    }

    override fun getMainID(): Int {
        return ID
    }
}