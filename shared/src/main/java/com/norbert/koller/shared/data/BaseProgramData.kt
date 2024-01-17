package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import java.text.SimpleDateFormat
import java.util.Date

class BaseProgramData(
    @SerializedName("ID") var id: Int,
    @SerializedName("TypeID") var typeId: Int,
    @SerializedName("Date") var date: Date,
    @SerializedName("Group") var group: String,
    @SerializedName("Lesson") var lesson: Int,
    @SerializedName("Length") var length: Int,
    @SerializedName("Topic") var topic: String,
    @SerializedName("RID") var rid : Int,
    @SerializedName("TUID") var tuid: Int
) : BaseData() {

    override fun diffrentDecider(context: Context): String {
        return RoomOrderData.instance[0].date.formatDate(DateTimeHelper.monthDay)
    }

    override fun getMainID(): Int {
        return id
    }
}