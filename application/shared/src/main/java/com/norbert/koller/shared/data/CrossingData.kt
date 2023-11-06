package com.norbert.koller.shared.data

import android.content.Context
import com.norbert.koller.shared.MyApplication
import com.norbert.koller.shared.helpers.DateTimeHelper
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date


class CrossingData(
    var ID : Int = -1,
    var UID : Int = -1,
    var Time: Date,
    var Direction : Byte

) : BaseData() {
    override fun diffrentDecider(context: Context): String {
        return SimpleDateFormat(DateTimeHelper.monthDay).format(Time)
    }

    override fun getMainID(): Int {
        return ID
    }
}
