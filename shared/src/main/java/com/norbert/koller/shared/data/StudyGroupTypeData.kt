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
) : ProgramBaseData(id, type, topic, rid, teacher){
    override fun diffrentDecider(context: Context): String {
        return topic[0].toString().capitalize()
    }

    override fun getMainID(): Int {
        return id
    }

    override fun updateValues(baseData: BaseData) {
        super.updateValues(baseData)

    }
}