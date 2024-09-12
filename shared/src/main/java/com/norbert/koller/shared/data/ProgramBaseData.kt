package com.norbert.koller.shared.data

import com.google.gson.annotations.SerializedName
import java.util.Date

abstract class ProgramBaseData(
    @SerializedName("ID") var id: Int,
    @SerializedName("Type") var type: Int,
    @SerializedName("Topic") var topic: String,
    @SerializedName("RID") var rid : Int,
    @SerializedName("Teacher") var teacher: UserData?,
) : BaseData() {
    override fun updateValues(baseData: BaseData) {
        baseData as ProgramBaseData
        type = baseData.type
        topic = baseData.topic
        rid = baseData.rid
        teacher = baseData.teacher
    }
}