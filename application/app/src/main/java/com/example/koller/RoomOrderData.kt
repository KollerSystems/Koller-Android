package com.example.koller

import java.util.*
import kotlin.collections.ArrayList

data class RoomOrderData(
    var finalGrade : Byte,
    var teacherID : Long,
    var date : Date
){
    companion object {
        var instance: ArrayList<RoomOrderData> = arrayListOf(
            RoomOrderData(7, 54253252353, Date(2005, 0,7, 22, 15, 45))
        )
    }
}
