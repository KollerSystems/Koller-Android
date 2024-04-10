package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import java.util.Date

open class ProgramData(
    id: Int,
    type: Int,
    topic: String,
    rid : Int,
    teacher: UserData?,
    @SerializedName("ProgramID") var prgraomID: Int,
    @SerializedName("Date") var date: Date,
    @SerializedName("Lesson") var lesson: Int,
    @SerializedName("Length") var length: Int
) : ProgramBaseData(id, type, topic, rid, teacher) {

    override fun diffrentDecider(context: Context): String {
        return date.formatDate(DateTimeHelper.monthDay)
    }

    override fun getMainID(): Int {
        return id
    }
}