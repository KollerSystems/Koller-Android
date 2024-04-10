package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import java.util.Date

class StudyGroupTypeData(
    id: Int,
    type: Int,
    topic: String,
    rid : Int,
    teacher: UserData?,
    prgraomId: Int,
    date: Date,
    lesson: Int,
    length: Int
) : ProgramData(id, type, topic, rid, teacher, prgraomId, date, lesson, length){
    override fun diffrentDecider(context: Context): String {
        return topic[0].toString().capitalize()
    }
}