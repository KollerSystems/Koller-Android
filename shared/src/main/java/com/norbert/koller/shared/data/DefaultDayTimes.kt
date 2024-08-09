package com.norbert.koller.shared.data

data class DefaultDayTimes(
    var dayTimeStart : Int = 360, //6:00
    var dayTimeGoInside : Int = 945, //15:45

    var lessons: ArrayList<FromTo> = arrayListOf(
        FromTo(960, 1005), //16:00 - 16:45
        FromTo(1010, 1055), //16:50 - 17:35
        FromTo(1060, 1105), //17:40 - 18:25
        FromTo(1110, 1155) //17:30 - 19:15
    ),

    var nightTimeGoInsideRed : Int = 1260, //21:00
    var nightTimeGoInsideYellow : Int = 1260, //21:00
    var nightTimeEnd : Int = 1320, //22:00
){
    companion object {
        var instance: DefaultDayTimes = DefaultDayTimes()
    }

    init {
        instance = this
    }
}

data class FromTo(
    var from : Int = 0,
    var to : Int = 0
)
