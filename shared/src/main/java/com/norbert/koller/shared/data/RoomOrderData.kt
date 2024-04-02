package com.norbert.koller.shared.data

import com.norbert.koller.shared.R
import java.util.*
import kotlin.collections.ArrayList

data class RoomOrderData(
    var FinalGrade : Byte,
    var TID : Long,
    var date : Date,
    var Conditions : ArrayList<RoomOrderConditionsBase>
){
    companion object {
        val conditionBoolean : RoomOrderConditionsBoolean = RoomOrderConditionsBoolean()
        init {

            conditionBoolean.icon = R.drawable.power
            conditionBoolean.title = "Áramtalanítás"
            conditionBoolean.value = false

        }
        val listOfConditions : ArrayList<RoomOrderConditionsBase> = arrayListOf(conditionBoolean,conditionBoolean,conditionBoolean,conditionBoolean,conditionBoolean,conditionBoolean,conditionBoolean,conditionBoolean,conditionBoolean)


        var instance: ArrayList<RoomOrderData> = arrayListOf(
            RoomOrderData(7, 54253252353, Date(2005, 0,7, 22, 15, 45), listOfConditions)
        )
    }
}

open class RoomOrderConditionsBase(
    var icon : Int = -1,
    var title : String = "Unknown"
)

class RoomOrderConditionsBoolean(
    var value : Boolean? = null
) : RoomOrderConditionsBase()

class RoomOrderConditionsInt(
    var value : Int? = null,
    var changed : Boolean = false
) : RoomOrderConditionsBase()