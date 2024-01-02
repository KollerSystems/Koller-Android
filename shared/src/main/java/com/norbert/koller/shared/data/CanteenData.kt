package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CanteenData (
    @SerializedName("ID") var id : Int,
    @SerializedName("Category") var category : String,
    @SerializedName("Time") var time : String,
    @SerializedName("FoodName") var foodName : String,
    @SerializedName("Date") val date: Date
) : BaseData() {
    override fun diffrentDecider(context: Context): String {
        return SimpleDateFormat(DateTimeHelper.monthDay, Locale.getDefault()).format(date)
    }

    override fun getMainID(): Int {
        return id
    }
}