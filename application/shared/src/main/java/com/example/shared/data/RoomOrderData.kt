package com.example.shared.data

import com.example.shared.R
import java.util.*
import kotlin.collections.ArrayList

data class RoomOrderData(
    var FinalGrade : Byte,
    var TID : Long,
    var Date : Date,
    var Conditions : ArrayList<RoomOrderConditionsBase>
){
    companion object {
        val conditionInt : RoomOrderConditionsInt = RoomOrderConditionsInt()
        val conditionBoolean : RoomOrderConditionsBoolean = RoomOrderConditionsBoolean()
        init {

            conditionInt.icon = R.drawable.bed
            conditionInt.title = "Ágyak"
            conditionInt.value = 10


            conditionBoolean.icon = R.drawable.power
            conditionBoolean.title = "Áramtalanítás"
            conditionBoolean.value = false

        }
        val listOfConditions : ArrayList<RoomOrderConditionsBase> = arrayListOf(conditionInt,conditionInt,conditionBoolean,conditionInt,conditionBoolean,conditionInt,conditionInt,conditionInt,conditionBoolean)


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
    var value : Int? = null
) : RoomOrderConditionsBase()