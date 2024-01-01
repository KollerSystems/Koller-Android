package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class CrossingData(
    @SerializedName("ID") var id : Int = -1,
    @SerializedName("UID") var uid : Int = -1,
    @SerializedName("Time") var time: Date,
    @SerializedName("Direction") var direction : Byte

) : BaseData() {
    override fun diffrentDecider(context: Context): String {
        return SimpleDateFormat(DateTimeHelper.monthDay, Locale.getDefault()).format(time)
    }

    override fun getMainID(): Int {
        return id
    }
}
