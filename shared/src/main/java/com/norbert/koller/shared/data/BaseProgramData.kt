package com.norbert.koller.shared.data

import android.content.Context
import com.google.gson.annotations.SerializedName
import com.norbert.koller.shared.helpers.DateTimeHelper
import com.norbert.koller.shared.managers.formatDate
import java.text.SimpleDateFormat
import java.util.Date

class BaseProgramData(
    id: Int,
    type: Int,
    topic: String,
    rid : Int,
    tuid: Int,
    prgraomId: Int,
    date: Date,
    lesson: Int,
    length: Int,
    @SerializedName("Class") var class_: ClassData,
) : ProgramData(id, type, topic, rid, tuid, prgraomId, date, lesson, length)