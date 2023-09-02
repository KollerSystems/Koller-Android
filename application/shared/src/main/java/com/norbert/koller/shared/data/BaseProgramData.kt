package com.norbert.koller.shared.data

import android.content.Context
import com.norbert.koller.shared.MyApplication
import java.util.Date

class BaseProgramData(

    var ID: Int,
    var TypeID: Int,
    var Date: Date,
    var Group: String,
    var Lesson: Int,
    var Length: Int,
    var Topic: String,
    var RID : Int,
    var TUID: Int

) : BaseData() {

    override fun diffrentDecider(context: Context): String {
        return MyApplication.simpleLocalMonthDay.format(Date)
    }

    override fun getMainID(): Int {
        return ID
    }
}