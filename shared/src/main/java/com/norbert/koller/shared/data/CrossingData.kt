package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
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
        return time.formatDate(DateTimeHelper.monthDay)
    }

    override fun getMainID(): Int {
        return id
    }
}
